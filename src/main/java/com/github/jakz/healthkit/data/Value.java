package com.github.jakz.healthkit.data;

public class Value
{
  private Unit unit;
  private Number value;
  
  public Value(Unit unit, float value)
  {
    this.unit = unit;
    assert(unit.defaultValueType() == Float.class);
    this.value = new Float(value);
  }
  
  public Value(Unit unit, int value)
  {
    this.unit = unit;
    assert(unit.defaultValueType() == Integer.class);
    this.value = new Integer(value);
  }
  
  public Unit unit() { return unit; }
 
  public Number value() { return value; }
  
  public String toString()
  {
    return value.toString() + " " + unit.caption();
  }
}
