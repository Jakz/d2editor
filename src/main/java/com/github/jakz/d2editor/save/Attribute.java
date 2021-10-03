package com.github.jakz.d2editor.save;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Attribute
{
  Strength              (  0, 10, 0),
  Energy                (  1, 10, 0),
  Dexterity             (  2, 10, 0),
  Vitality              (  3, 10, 0),
  AvailableStatPoints   (  4, 10, 0),
  AvailableSkillPoints  (  5,  8, 0),
  HitPointsCurrent      (  6, 21, 8),
  HitPointsMax          (  7, 21, 8),         
  ManaCurrent           (  8, 21, 8),
  ManaMax               (  9, 21, 8),
  StaminaCurrent        ( 10, 21, 8),
  StaminaMax            ( 11, 21, 8),
  Level                 ( 12,  7, 0),
  Experience            ( 13, 32, 0),
  Gold                  ( 14, 25, 0),
  GoldStash             ( 15, 25, 0),
  ;
  
  private Attribute(int indexInStatsSave, int bitsInStatsSave, int bitShiftInStatsSave)
  {
    this.indexInStatsSave = indexInStatsSave;
    this.bitsInStatsSave = bitsInStatsSave;
    this.bitShiftInStatsSave = bitShiftInStatsSave;
  }
  
  public final int indexInStatsSave;
  public final int bitsInStatsSave;
  public final int bitShiftInStatsSave;
  
  public static Stream<Attribute> stream() { return Arrays.stream(Attribute.values()); }
}
