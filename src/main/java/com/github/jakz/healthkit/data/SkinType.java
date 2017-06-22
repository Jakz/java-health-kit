package com.github.jakz.healthkit.data;

import java.util.HashMap;
import java.util.Map;

public enum SkinType
{
  UNSPECIFIED("HKFitzpatrickSkinTypeNotSet"),
  TYPE_I("HKFitzpatrickSkinTypeTypeI"),
  TYPE_II("HKFitzpatrickSkinTypeTypeII"),
  TYPE_III("HKFitzpatrickSkinTypeTypeIII"),
  TYPE_IV("HKFitzpatrickSkinTypeIV"),
  TYPE_V("HKFitzpatrickSkinTypeV"),
  TYPE_VI("HKFitzpatrickSkinTypeVI"),
  ;
  
  private SkinType(String key)
  {
    this.key = key;
  }
  
  public final String key;
  
  private static final Map<String, SkinType> mapping = new HashMap<>();
  
  static
  {
    for (SkinType type : values())
      mapping.put(type.key, type);
  }
  
  public static SkinType forKey(String key)
  { 
    return mapping.get(key);
  }
}
