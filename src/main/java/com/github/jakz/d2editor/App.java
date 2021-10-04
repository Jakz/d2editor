package com.github.jakz.d2editor;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.jakz.d2editor.casc.Casc;
import com.github.jakz.d2editor.casc.ImageCache;
import com.github.jakz.d2editor.data.DataTables;
import com.github.jakz.d2editor.save.BitArray;
import com.github.jakz.d2editor.save.SaveFile;
import com.github.jakz.d2editor.ui.UI;

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
      try
      {
        DataTables data = new DataTables();
        data.loadData();
        
        UI.init();
        UI.dataViewerPanel.setData(data);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      
      
      try (SaveFile save = new SaveFile(Paths.get("Slayer-21-22-23-25.d2s")))
      {
        System.out.println("Valid header: "+save.hasValidSignature());
        System.out.println("Version: "+save.version());
        System.out.println("Size in bytes: "+save.sizeInBytes());
        System.out.println("Checksum correct: "+(save.checksum() == save.computedChecksum()));
        System.out.println("Name: "+save.name());

        save.loadStats();
        //save.loadSkills();
        save.loadItems();
      }
      
      /*casc.open();
      
      //imageCache.get("panel", "inventory", "background");
      //casc.extractSubfolder(Casc.PATH_HD_INTERFACE);
      
      casc.close();
      
      UI.init();*/
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } 
  }
  
}
