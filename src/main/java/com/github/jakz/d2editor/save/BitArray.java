package com.github.jakz.d2editor.save;

import java.util.Arrays;

public class BitArray
{  
  byte[] bytes;
  int b, B;
  
  public BitArray(byte[] bytes)
  {
    this.bytes = bytes;
    b = 0;
    B = 0;
  }
  
  public String readString(int length)
  {
    String value = new String(Arrays.copyOfRange(bytes, B, B+2));
    B += length;
    return value;
  }
  
  public int readU8()
  {
    if (b != 0)
      throw new IllegalArgumentException("Can't read aligned data if offset is not aligned");
    
    int v = bytes[B] & 0xFF;
    ++B;
    
    return v;
  }
  
  public int readU16()
  {
    if (b != 0)
      throw new IllegalArgumentException("Can't read aligned data if offset is not aligned");
    
    int v1 = bytes[B] & 0xFF, v2 = bytes[B+1] & 0xFF;
    B += 2;
    
    return v2 << 8 | v1;
  }
  
  public int readU32()
  {
    if (b != 0)
      throw new IllegalArgumentException("Can't read aligned data if offset is not aligned");
    
    int v1 = bytes[B] & 0xFF, v2 = bytes[B+1] & 0xFF;
    int v3 = bytes[B+2] & 0xFF, v4 = bytes[B+3] & 0xFF;
    B += 4;
    
    return v4 << 24 | v3 << 16 | v2 << 8 | v1;
  }
  
  public boolean readBool()
  {
    return readBits(1) == 1 ? true : false;
  }
   
  public int readBits(int bits)
  {
    int r = 0;
    
    for (int i = 0; i < bits; ++i)
    {
      byte bb = bytes[B];
      
      if ((bb & (1 << b)) != 0)
        r |= 1 << i;
      
      ++b;
      
      if (b == 8)
      {
        ++B;
        b = 0;
      }
    }
    
    return r;
  }
  
  public int readBits(int offset, int nbits)
  {   
    seek(offset);
    return readBits(nbits);
  }
  
  public int readU8(int offset)
  {
    seek(offset);
    return readU8();
  }

  public int readU16(int offset)
  {
    seek(offset);
    return readU16();
  }
  
  public int readU32(int offset)
  {
    seek(offset);
    return readU32();
  }
    
  /*public String toString()
  {
    StringBuilder b = new StringBuilder();
    
    int c = 0;
    for (byte v : bits)
    {
      b.append(v == 1 ? '1' : '0');
      if (c % 8 == 7)
        b.append(" ");
      ++c;
    }
    
    return b.toString();
  }*/
  
  public void seek(int offset)
  {
    /*B = offset / 8;
    b = offset % 8;*/
    
    B = offset;
    b = 0;
  }
  
  public int position()
  {
    return B;
  }
  
  public int finePosition()
  {
    return B * 8 + b;
  }
  
  public int size()
  {
    return bytes.length;
  }
  
  public boolean didReachEnd()
  {
    return B >= bytes.length;
  }
}
