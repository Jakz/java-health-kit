package com.github.jakz.healthkit.data.constants;

import java.util.HashMap;
import java.util.Map;

import com.github.jakz.healthkit.data.Unit;
import com.github.jakz.healthkit.data.Value;
import com.github.jakz.healthkit.exceptions.InconvertibleUnitException;
import com.github.jakz.healthkit.exceptions.InvalidDataException;

public enum StandardUnit implements Unit
{
  CM("cm", Unit.Type.LENGTH, Float.class, 100.0f),
  M("m", Unit.Type.LENGTH, Float.class, 1.0f),
  KM("km", Unit.Type.LENGTH, Float.class, 0.001f),

  KCAL("kcal", Unit.Type.ENERGY, Float.class, 1.0f),
  
  KG("kg", Unit.Type.WEIGHT, Float.class, 1.0f),
  
  COUNT_MINUTES("count/min", Unit.Type.TIMED_AMOUNT, Integer.class, 1.0f),
  
  MINUTES("min", Unit.Type.TIME, Float.class, 1.0f),
  HOURS("hour", Unit.Type.TIME, Float.class, 1/60.0f),
  
  ML_MIN_KG("mL/min·kg", Unit.Type.OXYGEN, Float.class, 1.0f),
  
  COUNT("count", Unit.Type.AMOUNT, Integer.class, 1.0f)  
  ;
  
  public final Class<? extends Number> defaultType;
  public final String key;
  public final Unit.Type type;
  private final float normalizer;
  
  private StandardUnit(String key, Unit.Type type, Class<? extends Number> defaultType, float normalizer)
  {
    this.key = key;
    this.type = type;
    this.defaultType = defaultType;
    this.normalizer = normalizer;
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
  public Unit.Type measureType() { return type; }
  
  @Override
  public String caption() { return key; }
  
  @Override
  public Value convert(Value value, Unit other)
  {
    Unit base = value.unit();
    
    if (!(base instanceof StandardUnit) || !(other instanceof StandardUnit))
      throw new InconvertibleUnitException("Unit must be of type StandardUnit to be converted");
    else if (base.measureType() != other.measureType())
      throw new InconvertibleUnitException("Units must be of same measure to be converted");
    else
    {      
      StandardUnit sbase = (StandardUnit)base;
      StandardUnit sother = (StandardUnit)other;

      if (other.defaultValueType() == Integer.class)
      {
        float n = value.value().intValue();
        n /= sbase.normalizer;
        n *= sother.normalizer;
        return new Value(other, n);
      }
      else if (other.defaultValueType() == Float.class)
      {
        float n = value.value().floatValue();
        n /= sbase.normalizer;
        n *= sother.normalizer;
        return new Value(other, n);
      }
      else
        throw new InconvertibleUnitException("Unknown default type for unit");
    }
  }

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