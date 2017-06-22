package com.github.jakz.healthkit.data;

public interface Unit
{
  Class<? extends Number> defaultValueType();
  Value parseValue(String value);
  String caption();
}
