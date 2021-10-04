package com.github.jakz.d2editor.data;

import java.util.Arrays;
import java.util.stream.Stream;

public enum EquipSlot
{
  Helmet(1, "head"),
  Amulet(2, "neck"),
  Armor(3, "torso"),
  WeaponRight(4, "rarm"),
  WeaponLeft(5, "larm"),
  RingRight(6, "rrin"),
  RingLeft(7, "lrin"),
  Belt(8, "belt"),
  Boots(9, "feet"),
  Gloves(10, "glov"),
  WeaponAlternateRight(11, "rarmalternate"),
  WeaponAlternateLeft(12, "larmalternate"),
  
  None(-1, "")
  
  ;
  
  public final int value;
  public final String code;
  
  private EquipSlot(int value, String code)
  {
    this.value = value;
    this.code = code;
  }
 
  public static Stream<EquipSlot> stream() { return Arrays.stream(EquipSlot.values()); }
}
