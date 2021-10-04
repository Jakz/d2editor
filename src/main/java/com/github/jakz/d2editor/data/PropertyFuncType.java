package com.github.jakz.d2editor.data;

public enum PropertyFuncType
{
  ItemModsSetValueRegular(1, true, true),
  ItemModsSetValueBaseToMax(2, true, true),
  ItemModsSetValueRegular2(3, true, true),
  ItemModsSetValueBaseToMax2(4, true, true),
  ItemModsSetMinDamage(5, false, true),
  ItemModsSetMaxDamage(6, false, true),
  ItemModsSetDamagePct(7, false, true),
  ItemModsSetSpeed(8, true, true),
  ItemModsSetSingleSkill(9, true, true),
  ItemModsSetTabSkills(10, true, true),
  ItemModsSetSkillOnAttack(11, true, true),
  ItemModsSetRandomParam(12, true, true),
  ItemModsSetMaxDurability(13, true, true),
  ItemModsSetSockets(14, true, false),
  ItemModsSetMin(15, true, true),
  ItemModsSetMax(16, true, true),
  ItemModsSetParam(17, true, true),
  ItemModsSetByTime(18, true, false),
  ItemModsSetChargedSkill(19, true, false),
  ItemModsSetIndestructible(20, false, false),
  ItemModsSetValueRegPropValParam(21, true, true, true),
  ItemModsSetValueRegParam(22, true, true),
  ItemModsSetEthereal(23, false, false),
  ItemModsSetParamAndValue(24, true, true),
  
  ItemModsSetValueRegPropValParamSwapped(36, true, true, true)
  
  ;
  
  public final int id;
  public final boolean stat, set, val;
  
  private PropertyFuncType(int id, boolean stat, boolean set)
  {
    this(id, stat, set, false);
  }
  
  private PropertyFuncType(int id, boolean stat, boolean set, boolean val)
  {
    this.id = id;
    this.stat = stat;
    this.set = set;
    this.val = val;
  }
}
