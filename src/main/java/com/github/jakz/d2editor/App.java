package com.github.jakz.d2editor;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.jakz.d2editor.casc.Casc;

public class App
{
  public static void main(String[] args)
  {
    final Path folder = Paths.get("T:\\Games\\Diablo II Resurrected\\Data");
    final Path cache = Paths.get("cache");
            
    try (Casc casc = new Casc(folder, cache))
    {
      casc.open();
      casc.extractSubfolder(Casc.PATH_HD_INTERFACE);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } 
  }
  
}
