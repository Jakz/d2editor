package com.github.jakz.d2editor.data;

public enum EquipSlot
{
  Helmet(1),
  Amulet(2),
  Armor(3),
  WeaponRight(4),
  WeaponLeft(5),
  RingRight(6),
  RingLeft(7),
  Belt(8),
  Boots(9),
  Gloves(10),
  WeaponAlternateRight(11),
  WeaponAlternateLeft(12)
  
  ;
  
  public final int value;
  
  private EquipSlot(int value)
  {
    this.value = value;
  }
 
}
