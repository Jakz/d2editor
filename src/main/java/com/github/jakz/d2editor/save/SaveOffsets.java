package com.github.jakz.d2editor.save;

public class SaveOffsets
{
  public static final int MAGIC_NUMBER = 0;
  public static final int VERSION = 4;
  public static final int FILE_SIZE = 8;
  public static final int CHECKSUM = 12;
  public static final int UKNOWN16 = 16;
  public static final int NAME = 20;
  public static final int STATUS = 36;
  public static final int PROGRESSION = 37;
  public static final int UKNOWN38 = 38;
  public static final int CLASS = 40;
  public static final int UNKNOWN = 41;
  public static final int LEVEL = 43;
  public static final int UKNOWN44 = 44;
  public static final int TIMESTAMP = 48;
  public static final int UNKNOWN52 = 52;
  public static final int SKILL_KEY_BINDS = 56;
  public static final int SKILL_MOUSE_BINDS = 120; // l1 r1 l2 r2
  public static final int UNKNOWN136 = 136;
  public static final int CURRENT_ACT = 168;
  public static final int MAP_ID = 171;
  public static final int UNKNOWN175 = 175;
  public static final int UNKNOWN177 = 177;
  public static final int MERCENARY_ID = 179;
  public static final int MERCENARY_NAME_ID = 183;
  public static final int MERCENARY_STATS = 185;
  public static final int MERCENARY_XP = 187;
  public static final int UKNOWN191 = 191;
  
  
  public static int CHUNK_STATS = 765;

  
  public static final int NAME_LENGTH = 16;
  public static final int VALID_SIGNATURE = 0xaa55aa55; 
}
