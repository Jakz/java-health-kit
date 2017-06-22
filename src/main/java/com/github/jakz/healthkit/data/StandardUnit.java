package com.github.jakz.healthkit.data;

import java.util.HashMap;
import java.util.Map;

public enum StandardUnit implements Unit
{
  CM("cm"),
  KCAL("kcal"),
  KG("kg"),
  KM("km"),
  COUNT_MINUTES("count/min"),
  MINUTES("min"),
  COUNT("count")
   
  ;
  
  private StandardUnit(String key)
  {
    this.key = key;
  }
  
  public final String key;
  
  private static final Map<String, StandardUnit> mapping = new HashMap<>();
  
  static
  {
    for (StandardUnit type : values())
      mapping.put(type.key, type);
  }
  
  public static StandardUnit forKey(String key)
  { 
    return mapping.get(key);
  }
}