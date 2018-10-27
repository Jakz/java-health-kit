package com.github.jakz.healthkit.data;

public interface Unit
{
  public static enum Type
  {
    LENGTH,
    TIME,
    WEIGHT,
    ENERGY,
    AMOUNT,
    OXYGEN,
    TIMED_AMOUNT,
  }
  
  Unit.Type measureType();
  Class<? extends Number> defaultValueType();
  Value parseValue(String value);
  String caption();
  
  Value convert(Value value, Unit other);
}
