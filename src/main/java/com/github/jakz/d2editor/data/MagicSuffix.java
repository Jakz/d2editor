package com.github.jakz.d2editor.data;

public class MagicSuffix
{
  public String name;
  
  public int version;
  
  public boolean spawnable;
  public boolean rare;
  
  public int level;
  public int maxLevel;
  public int levelReq;
  
  public CharacterClass clazzSpecific;
  public CharacterClass clazz;
  public int clazzLevelReq;
  
  public int frequency;
  
  public boolean isExpansion() { return version >= 100; }
}
