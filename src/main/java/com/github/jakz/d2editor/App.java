package com.github.jakz.d2editor;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.jakz.d2editor.casc.Casc;
import com.github.jakz.d2editor.casc.ImageCache;

public class App
{
  final static Path folder = Paths.get("T:\\Games\\Diablo II Resurrected\\Data");
  final static Path cache = Paths.get("cache");
  
  public final static Casc casc = new Casc(folder, cache);
  public final static ImageCache imageCache = new ImageCache(casc, cache);
  
  
  public static void main(String[] args)
  {            
    try
    {
      casc.open();
      
      imageCache.get("panel", "inventory", "background");
      
      //casc.extractSubfolder(Casc.PATH_HD_INTERFACE);
      
      casc.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } 
  }
  
}
