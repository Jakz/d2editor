package com.github.jakz.d2editor.casc;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.hiveworkshop.blizzard.casc.ConfigurationFile;
import com.hiveworkshop.blizzard.casc.info.Info;
import com.hiveworkshop.blizzard.casc.storage.Storage;
import com.hiveworkshop.blizzard.casc.vfs.VirtualFileSystem;
import com.hiveworkshop.blizzard.casc.vfs.VirtualFileSystem.PathResult;

public class Casc implements AutoCloseable
{
  private final Path dataFolder;
  private final Path cacheFolder;
  
  public final static String PATH_HD_ITEM_SPRITES = "data\\data\\hd\\global\\ui\\items";
  public final static String PATH_HD_INTERFACE = "data\\data\\hd\\global\\ui\\panel";

  
  private VirtualFileSystem vfs;
  private TreeMap<String, PathResult> mapping;
  private Storage storage;
  
  public Casc(Path dataFolder, Path cacheFolder)
  {
    this.dataFolder = dataFolder;
    this.cacheFolder = cacheFolder;
  }
  
  public void open() throws IOException
  {
    System.out.println("opening storage");
    
    final Path infoFile = dataFolder.resolveSibling(Info.BUILD_INFO_FILE_NAME);

    Info info = new Info(ByteBuffer.wrap(Files.readAllBytes(infoFile)));
    
    if (info.getRecordCount() < 1)
      throw new IllegalArgumentException("No records in info");
    
    {
      int fieldIndex = info.getFieldIndex("Build Key");
      
      if (fieldIndex < 1)
        throw new IllegalArgumentException("Missing 'Build Key' field");
      
      String buildKey = info.getField(0, fieldIndex);
      
      ConfigurationFile buildConfiguration = ConfigurationFile.lookupConfigurationFile(dataFolder, buildKey);
      
      storage = new Storage(dataFolder, false, true);
          
      vfs = new VirtualFileSystem(storage, buildConfiguration.getConfiguration());
      List<PathResult> allFilePaths = vfs.getAllFiles();
      
      mapping = new TreeMap<>();
      for (PathResult pr : allFilePaths)
        mapping.put(pr.getPath(), pr);
    }
  }
  
  public void close() throws IOException
  {
    if (storage != null)
      storage.close();
  }
  
  public void extractSubfolder(String path) throws IOException
  {
    Map<String, PathResult> submap = mapping.subMap(path, path + Character.MAX_VALUE);


    for (Map.Entry<String, PathResult> e : submap.entrySet())
    {
      PathResult pr = e.getValue();
      
      long size = pr.getFileSize();
      ByteBuffer buffer = ByteBuffer.allocate((int)size);
      pr.readFile(buffer);
      
      BufferedImage image = processSprite(buffer.array());
      
      Path output = cacheFolder.resolve(e.getKey().replaceAll("\\.sprite", ".png"));
      Files.createDirectories(output.getParent());
      
      ImageIO.write(image, "PNG", output.toFile());
    }          
  }
  

  static int uint32(byte[] a, int o)
  {
    return a[o+0] & 0xff | ((a[o+1] & 0xff) << 8) | ((a[o+2] & 0xff) << 16) | ((a[o+3] & 0xff) << 24);
  }
  
  public static BufferedImage processSprite(byte[] b)
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
