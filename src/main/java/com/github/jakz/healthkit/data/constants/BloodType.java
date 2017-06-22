package com.github.jakz.healthkit.data.constants;

import java.util.HashMap;
import java.util.Map;

public enum BloodType
{
  UNSPECIFIED("HKBloodTypeNotSet"),
  A_POSITIVE("HKBloodTypeAPositive"),
  A_NEGATIVE("HKBloodTypeANegative"),
  B_POSITIVE("HKBloodTypeBPositive"),
  B_NEGATIVE("HKBloodTypeBNegative"),
  AB_POSITIVE("HKBloodTypeABPositive"),
  AB_NEGATIVE("HKBloodTypeABNegative"),
  O_POSITIVE("HKBloodTypeOPositive"),
  O_NEGATIVE("HKBloodTypeONegative")
  ;
  
  private BloodType(String key)
  {
    this.key = key;
  }
  
  public final String key;
  
  private static final Map<String, BloodType> mapping = new HashMap<>();
  
  static
  {
    for (BloodType type : values())
      mapping.put(type.key, type);
  }
  
  public static BloodType forKey(String key)
  { 
    return mapping.get(key);
  }
}
