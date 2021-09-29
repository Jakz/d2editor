package com.github.jakz.d2editor.casc;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageUtils
{
  static int uint32(byte[] a, int o)
  {
    return a[o+0] & 0xff | ((a[o+1] & 0xff) << 8) | ((a[o+2] & 0xff) << 16) | ((a[o+3] & 0xff) << 24);
  }
  
  public static BufferedImage processNewSprite(byte[] b)
  {
    int bb = 40;
    int c = (b.length - 40) / 4;
    
    int width = uint32(b, 8);
    int height = uint32(b, 12);
    
    System.out.println("Size: "+width+"x"+height);
    System.out.println("Pixels count: "+c);
    
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    int pixels = image.getWidth() * image.getHeight();
    
    for (int i = 0; i < pixels; ++i)
    {
      int k = bb + i * 4;
      
      image.setRGB(i % image.getWidth(), i / image.getWidth(), new Color(b[k] & 0xff, b[k+1] & 0xff, b[k+2] & 0xff, b[k+3] & 0xff).getRGB());
    }
    
    return image;
  }
}
