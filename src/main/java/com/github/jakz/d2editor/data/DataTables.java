package com.github.jakz.d2editor.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataTables
{
  public List<MagicSuffix> magicSuffixes;
  public List<MagicSuffix> magicPrefixes;
  
  private int parseIntOrDefault(String code, int def)
  {
    if (code.isEmpty()) return def;
    else return Integer.parseInt(code);
  }
  
  public void loadData() throws IOException
  {
    magicSuffixes = loadMagicSuffix(Paths.get("cache\\data\\data\\global\\excel\\magicsuffix.txt"));
    magicPrefixes = loadMagicPrefix(Paths.get("cache\\data\\data\\global\\excel\\magicprefix.txt"));
  }
  
  private List<MagicSuffix> loadMagicPrefix(Path path) throws IOException
  {
    return loadMagicSuffix(path);
  }
  
  private List<MagicSuffix> loadMagicSuffix(Path path) throws IOException
  {
    List<String> lines = Files.readAllLines(path);
    List<MagicSuffix> list = new ArrayList<>();
    
    for (int i = 1; i < lines.size(); ++i)
    {
      String[] tokens = lines.get(i).split("\t", -1);
      
      MagicSuffix suffix = new MagicSuffix();
      
      if (tokens[0].isEmpty())
        continue;
      
      suffix.name = tokens[0];
      suffix.version = parseIntOrDefault(tokens[1], -1);
      
      suffix.spawnable = tokens[2].equals("1") ? true : false;
      suffix.rare = tokens[3].equals("1") ? true : false;
      
      suffix.level = parseIntOrDefault(tokens[4], -1);
      suffix.maxLevel = parseIntOrDefault(tokens[5], -1);
      suffix.levelReq = parseIntOrDefault(tokens[6], -1);
      
      suffix.clazzSpecific =  CharacterClass.findByCodeOrDefault(tokens[7], CharacterClass.Any);
      suffix.clazz =  CharacterClass.findByCodeOrDefault(tokens[8], CharacterClass.None);
      suffix.clazzLevelReq = parseIntOrDefault(tokens[9], -1);
      
      suffix.frequency = parseIntOrDefault(tokens[10], -1);
      
      list.add(suffix);
    }
    
    return list;
  }
}
