package com.github.jakz.d2editor.save;

public class BitArray
{
  private final static int BPB = 8;
  
  byte[] bits;
  
  public BitArray(byte[] bytes)
  {
    bits = new byte[bytes.length * BPB];
    
    for (int j = 0; j < bytes.length; ++j)
    {
      byte b = bytes[j];
      for (int i = 0; i < BPB; ++i)
      {
        //byte v = (byte) ((b & (1 << (BPB - 1 - i))) != 0 ? 1 : 0);
        byte v = (byte) ((b & (1 << i)) != 0 ? 1 : 0);
        bits[j * BPB + i] = v;
      }
    }
  }
  
  public int read(int offset, int nbits)
  {   
    int r = 0;
    
    for (int i = 0; i < nbits; ++i)
    {
      byte v = bits[offset + i];
      //byte v = bits[offset + nbits - 1 - i];
      
      if (v == 1)
        r |= 1 << i;
    }
    
    //System.out.println("read("+offset+", "+nbits+") : "+r);
    
    return r;
  }
  
  public int size() { return bits.length; }
  
  public String toString()
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
  }
}
