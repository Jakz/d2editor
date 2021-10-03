package com.github.jakz.d2editor.save;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.Optional;

import com.pixbits.lib.io.BinaryBuffer;

public class SaveFile implements AutoCloseable
{
  private static void require(boolean condition, String message)
  {
    if (!condition)
      throw new SaveFormatException(message);
  }
  
  
  BinaryBuffer d;
  
  public SaveFile(Path file) throws FileNotFoundException, IOException
  {
    d = new BinaryBuffer(file, BinaryBuffer.Mode.READ, ByteOrder.LITTLE_ENDIAN);
  }
  
  public int signature()
  { 
    return d.readU32(0); 
  }
  
  public int version()
  {
    return d.readU32(SaveOffsets.VERSION);
  }
  
  public int sizeInBytes()
  {
    return d.readU32(SaveOffsets.FILE_SIZE);
  }
  
  public long checksum()
  {
    return d.readU32(SaveOffsets.CHECKSUM);
  }
  
  public int offsetItemsChunk()
  {
    return offsetSkillsChunk() + 32;
  }
  
  public int offsetSkillsChunk()
  {
    return SaveOffsets.CHUNK_STATS + savedStatsCount() * 4 + 2 + 2;
  }  
  
  public void loadItems()
  {
    int offset = offsetItemsChunk();
    
    d.position(offset);
    
    require(d.readU8() == 'J' && d.readU8() == 'M', "invalid item chunk header");
  }
  
  public void loadSkills()
  {
    int offset = offsetSkillsChunk();
    d.position(offset);
    require(d.readU8() == 'i' && d.readU8() == 'f', "invalid skills chunk header");

  }
  
  public void loadStats()
  {
    d.position(SaveOffsets.CHUNK_STATS);
    
    require(d.readU8() == 'g' && d.readU8() == 'f', "invalid stats chunk header");
    
    int i = d.position();
    while (i + 1 < d.length())
    {
      if (d.read(i) == 'i' && d.read(i+1) == 'f')
        break;
      ++i;
    }
    
    byte[] data = new byte[i - SaveOffsets.CHUNK_STATS + 2];
    d.read(data, SaveOffsets.CHUNK_STATS + 2);
    
    System.out.println("Test");
   
    BitArray array = new BitArray(data);
      
    System.out.println(array);
    
    final int BIT_PER_STAT_INDEX = 9;
    final int ATTRIBUTE_END = 0x1ff;
    
    for (int k = 0; k + BIT_PER_STAT_INDEX < array.size(); /**/)
    {
      int which = array.read(k, BIT_PER_STAT_INDEX);
      k += BIT_PER_STAT_INDEX;
      
      if (which == ATTRIBUTE_END)
        break;
      
      Optional<Attribute> attribute = Attribute.stream().filter(a -> a.indexInStatsSave == which).findFirst();
      
      if (attribute.isEmpty())
        throw new SaveFormatException("uknown stat index in stats chunk: "+ which);
      
      Attribute attr = attribute.get();
      
      int bitCount = attr.bitsInStatsSave;
      
      long value = array.read(k, bitCount) >> attr.bitShiftInStatsSave;
 
      System.out.println("Stat " + attr.name() + ": "+ value);
      k += bitCount; 
    }
  }
  
  public String name()
  {
    byte[] data = new byte[16];
    d.read(data, 20);
    
    StringBuilder builder = new StringBuilder();
    
    int c = 0;
    while (data[c] != 0)
      builder.append((char)data[c++]);
    
    return builder.toString();
  }
  
  public int savedStatsCount()
  {
    int mask = d.readU16(SaveOffsets.CHUNK_STATS + 2);
    int count = 0;
    
    for (int i = 0; i < 16; ++i)
      if ((mask & (1 << i)) == 1 << i)
        ++count;
    
    System.out.println("Saved stats count: "+count);
    return count;
  }
  
  
  public long computedChecksum()
  {
    int backup = d.position();
    int checksum = 0;
    
    while (!d.didReachEnd())
      checksum = (checksum << 1) + d.readU8();
        
    d.position(backup);
    
    return checksum;
  }
  
  public boolean hasValidSignature()
  {
    return signature() == SaveOffsets.VALID_SIGNATURE;
  }

  @Override
  public void close() throws Exception
  {
    d.close();
  }
}
