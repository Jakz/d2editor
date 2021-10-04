package com.github.jakz.d2editor.data;

import java.util.Arrays;
import java.util.stream.Stream;

public enum CharacterClass
{
  Amazon("ama"),
  Barbarian("bar"),
  Paladin("pal"),
  Necromancer("nec"),
  Sorceress("sor"),
  Druid("dru"),
  Assassin("ass"),
  
  Any(""),
  None("")
  ;
  
  public final String code;
  
  private CharacterClass(String code)
  {
    this.code = code;
  }
  
  public static Stream<CharacterClass> stream() { return Arrays.stream(CharacterClass.values()); }

  
  public static CharacterClass findByCodeOrDefault(String code, CharacterClass def)
  {
    return CharacterClass.stream().filter(c -> c.code.equals(code)).findFirst().orElse(def);
  }
}
