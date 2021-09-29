package com.github.jakz.d2editor.casc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class ImageCache
{
  private final Casc casc;
  private final Path cacheFolder;
  
  public static final String CASC_PATH_PREFIX = "data\\data\\hd\\global\\ui\\";
  
  private Map<String, BufferedImage> cache;
  
  public ImageCache(Casc casc, Path cacheFolder)
  {
    this.casc = casc;
    this.cacheFolder = cacheFolder;
    
    cache = new HashMap<>();
  }
  
  public Image get(String... key)
  {
    String ckey = CASC_PATH_PREFIX + Arrays.stream(key).collect(Collectors.joining("\\"));
    
    BufferedImage image = cache.get(ckey);
    
    try
    {
      if (image == null)
      {
        Path path = cacheFolder.resolve(ckey + ".png");
        
        if (Files.exists(path))
        {
          image = ImageIO.read(path.toFile());
          cache.put(ckey, image);
        }
        else
        {
          ByteBuffer buffer = casc.get(ckey + ".sprite");
          image = ImageUtils.processNewSprite(buffer.array());
          
          Files.createDirectories(path.getParent());
          ImageIO.write(image, "PNG", path.toFile());
          
          cache.put(ckey, image);
        }
      }
      
      return image;
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return null;
    }

  }
}
