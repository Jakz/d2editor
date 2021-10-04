package com.github.jakz.d2editor.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class DataTables
{
  public List<MagicSuffix> magicSuffixes;
  public List<MagicSuffix> magicPrefixes;
  public LinkedHashMap<String, ItemProperty> properties;
  public LinkedHashMap<String, ItemType> itemTypes;

  private int parseIntOrDefault(String code, int def)
  {
    if (code.isEmpty()) return def;
    else return Integer.parseInt(code);
  }
  
  public boolean parseBoolean(String tks)
  {
    return tks.equals("1") ? true : false;
  }
  
  private List<String[]> parseFile(Path path) throws IOException
  {
    List<String> lines = Files.readAllLines(path);
    
    List<String[]> data = lines.stream()
        .skip(1)
        .map(l -> l.split("\t", -1))
        .filter(t -> t.length > 0 && !t[0].equals("Expansion"))
        .collect(Collectors.toList());
    
    return data;
  }
  
  public void loadData() throws IOException
  {
    magicSuffixes = loadMagicSuffix(Paths.get("cache\\data\\data\\global\\excel\\magicsuffix.txt"));
    magicPrefixes = loadMagicPrefix(Paths.get("cache\\data\\data\\global\\excel\\magicprefix.txt"));
    properties = loadProperties(Paths.get("cache\\data\\data\\global\\excel\\properties.txt"));
    itemTypes = loadItemTypes(Paths.get("cache\\data\\data\\global\\excel\\itemtypes.txt"));
  }
  
  private List<MagicSuffix> loadMagicPrefix(Path path) throws IOException
  {
    return loadMagicSuffix(path);
  }
  
  private List<MagicSuffix> loadMagicSuffix(Path path) throws IOException
  {    
    List<String[]> data = parseFile(path);
    
    return data.stream().filter(t -> !t[0].isEmpty()).map(tokens -> {
      MagicSuffix suffix = new MagicSuffix();
      
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
      suffix.group = parseIntOrDefault(tokens[11], -1);
      
      return suffix;
    }).collect(Collectors.toList());
  }
  
  private LinkedHashMap<String, ItemProperty> loadProperties(Path path) throws IOException
  {
    List<String[]> data = parseFile(path);
    LinkedHashMap<String, ItemProperty> table = new LinkedHashMap<>();
    
    for (String[] tks : data)
    {
      ItemProperty property = new ItemProperty();
      property.code = tks[0];
      
      property.enabled = tks[1].equals("1") ? true : false;
      
      table.put(property.code, property);
    }
    
    return table;
  }
  
  private LinkedHashMap<String, ItemType> loadItemTypes(Path path) throws IOException
  {
    List<String[]> data = parseFile(path);
    LinkedHashMap<String, ItemType> table = new LinkedHashMap<>();
    
    for (String[] tks : data)
    {
      ItemType property = new ItemType();
      property.name = tks[0];
      property.code = tks[1];
      property.equiv1 = tks[2];
      property.equiv2 = tks[3];
      
      property.canBeRepaired = parseBoolean(tks[4]);
      
      property.body = parseBoolean(tks[5]);
      property.bodyLoc1 = EquipSlot.stream().filter(e -> e.code.equals(tks[6])).findFirst().orElse(EquipSlot.None);
      property.bodyLoc2 = EquipSlot.stream().filter(e -> e.code.equals(tks[7])).findFirst().orElse(EquipSlot.None);

      if (property.code.isEmpty())
        continue;
      
      table.put(property.code, property);
    }
    
    return table;
  }
}
