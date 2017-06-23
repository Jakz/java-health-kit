package com.github.jakz.healthkit.data.constants;

import java.util.HashMap;
import java.util.Map;

import com.github.jakz.healthkit.InvalidDataException;
import com.github.jakz.healthkit.data.Unit;
import com.github.jakz.healthkit.data.Value;

public enum StandardUnit implements Unit
{
  CM("cm", Integer.class),
  KCAL("kcal", Float.class),
  KG("kg", Float.class),
  KM("km", Float.class),
  COUNT_MINUTES("count/min", Integer.class),
  MINUTES("min", Float.class),
  HOURS("hour", Integer.class),
  COUNT("count", Integer.class)  
  ;
  
  public final Class<? extends Number> defaultType;
  public final String key;
  
  private StandardUnit(String key, Class<? extends Number> defaultType)
  {
    this.key = key;
    this.defaultType = defaultType;
  }
  
  @Override
  public Class<? extends Number> defaultValueType()
  {
    return defaultType;
  }
  
  @Override
  public Value parseValue(String value)
  {
    try
    {
      if (defaultType == Integer.class)
        return new Value(this, Integer.parseInt(value));
      else if (defaultType == Float.class)
        return new Value(this, Float.parseFloat(value));
      else
        throw new IllegalArgumentException();
    }
    catch (NumberFormatException e)
    {
      throw new InvalidDataException("Error parsing value for unit "+key+" ("+defaultType.getSimpleName()+") for value "+value);
    }

  }
  
  @Override
  public String caption() { return key; }

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