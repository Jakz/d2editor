package com.github.jakz.d2editor.save;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;

import com.pixbits.lib.io.BinaryBuffer;

public class SaveFile implements AutoCloseable
{
  public final int VALID_SIGNATURE = 0xaa55aa55; 
  
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
    return d.readU32(4);
  }
  
  public int sizeInBytes()
  {
    return d.readU32(8);
  }
  
  public long checksum()
  {
    return d.readU32(12);
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
    return signature() == VALID_SIGNATURE;
  }

  @Override
  public void close() throws Exception
  {
    d.close();
  }
}
