package com.github.jakz.healthkit.data;

public class Value
{
  protected Unit unit;
  protected Number value;
  
  public Value(Unit unit, float value)
  {
    if (unit.defaultValueType() != Float.class)
      throw new IllegalArgumentException();
    
    this.unit = unit;
    this.value = new Float(value);
  }
  
  public Value(Unit unit, int value)
  {
    if (unit.defaultValueType() != Integer.class)
      throw new IllegalArgumentException();
    
    this.unit = unit;
    this.value = new Integer(value);
  }
  
  public Value convert(Unit unit) { return this.unit.convert(this, unit); }
  
  public Unit unit() { return unit; }
 
  public Number value() { return value; }
  public int ivalue() { return Math.round(value.floatValue()); }
  
  public String toString()
  {
    return value.toString() + " " + unit.caption();
  }
}
