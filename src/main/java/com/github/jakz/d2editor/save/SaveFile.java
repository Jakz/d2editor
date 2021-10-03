package com.github.jakz.d2editor.save;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
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
  
  
  BitArray d;
  
  public SaveFile(Path file) throws FileNotFoundException, IOException
  {
    d = new BitArray(Files.readAllBytes(file));
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
  
  private int seekHeader(String value)
  {
    if (value.length() != 2)
      throw new SaveFormatException("seekHeader requires a 2 character value");
    
    d.seek(SaveOffsets.CHUNK_STATS);
    
    int i = d.position();
    while (i + 1 < d.size())
    {
      d.seek(i);
      String s = d.readString(2);
            
      if (s.equals(value))
        return i;
      
      ++i;
    }
    
    throw new SaveFormatException("unable to find header "+value);
  }

  public void loadItems()
  {
    int o = seekHeader("JM");   
    
    
    int n = d.readU16(o + 2);
    
    System.out.println("Loading " + n + " items");
    
    for (int i = 0; i < 1; ++i)
    {
      
    }
    
  }
  
  public void loadSkills()
  {
    int o = seekHeader("if");
  }
  
  public void loadStats()
  {
    d.seek(SaveOffsets.CHUNK_STATS);
    
    require(d.readU8() == 'g' && d.readU8() == 'f', "invalid stats chunk header");
                     
    final int BIT_PER_STAT_INDEX = 9;
    final int ATTRIBUTE_END = 0x1ff;
    
    while (!d.didReachEnd())
    {
      int which = d.readBits(BIT_PER_STAT_INDEX);
      
      if (which == ATTRIBUTE_END)
        break;
      
      Optional<Attribute> attribute = Attribute.stream().filter(a -> a.indexInStatsSave == which).findFirst();
      
      if (attribute.isEmpty())
        throw new SaveFormatException("uknown stat index in stats chunk: "+ which);
      
      Attribute attr = attribute.get();
      
      int bitCount = attr.bitsInStatsSave;
      
      long value = d.readBits(bitCount) >> attr.bitShiftInStatsSave;
 
      System.out.println("Stat " + attr.name() + ": "+ value);
    }
  }
  
  public String name()
  {
    StringBuilder builder = new StringBuilder();
    
    d.seek(SaveOffsets.NAME);
    
    for (int i = 0; i < SaveOffsets.NAME_LENGTH; ++i)
    {
      int v = d.readU8();
      
      if (v == 0)
        break;
      
      builder.append((char)v);
    }

    return builder.toString();
  }

  public long computedChecksum()
  {
    int backup = d.position();
    long checksum = 0;
    int carry = 0;
    
    while (!d.didReachEnd())
    {
      int value = d.readU8(); 
      
      if (d.position() >= SaveOffsets.CHECKSUM && d.position() <= SaveOffsets.CHECKSUM + 4)
        value = 0;

      carry = (checksum & (1l << 31)) != 0 ? 1 : 0;
      checksum = ((checksum << 1) + value + carry) & 0x00000000FFFFFFFF;
      
    }
        
    d.seek(backup);
    
    return checksum;
  }
  
  public boolean hasValidSignature()
  {
    return signature() == SaveOffsets.VALID_SIGNATURE;
  }

  @Override
  public void close() throws Exception
  {

  }
}
