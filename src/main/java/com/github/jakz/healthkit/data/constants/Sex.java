package com.github.jakz.healthkit.data.constants;

import java.util.HashMap;
import java.util.Map;

public enum Sex
{
  UNSPECIFIED("HKBiologicalSexNotSet"),
  MALE("HKBiologicalSexMale"),
  FEMALE("HKBiologicalSexFemale"),
  OTHER("HKBiologicalSexOther")

  ;
  
  private Sex(String key)
  {
    this.key = key;
  }
  
  public final String key;
  
  private static final Map<String, Sex> mapping = new HashMap<>();
  
  static
  {
    for (Sex type : values())
      mapping.put(type.key, type);
  }
  
  public static Sex forKey(String key)
  { 
    return mapping.get(key);
  }
}
