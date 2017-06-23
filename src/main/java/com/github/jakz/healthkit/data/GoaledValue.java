package com.github.jakz.healthkit.data;

public class GoaledValue extends Value
{
  protected Number goal;
  
  public GoaledValue(Unit unit, int value, int goal)
  {
    super(unit, value);
    this.goal = new Integer(value);

  }
  
  public GoaledValue(Unit unit, float value, float goal)
  {
    super(unit, value);
    this.goal = new Float(value);
  }
  
  public Number goal() { return goal; }
}
