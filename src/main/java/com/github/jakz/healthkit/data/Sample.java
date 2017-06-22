package com.github.jakz.healthkit.data;

import java.time.ZonedDateTime;

public class Sample
{
  private ZonedDateTime start;
  private ZonedDateTime end;
  private ZonedDateTime creation;
  
  private SampleType type;
  private Unit unit;
  
  public Sample()
  {
    
  }
  
  public SampleType type() { return type; }
  public void type(SampleType type) { this.type = type; }
  
  public Unit unit() { return unit; }
  public void unit(Unit unit) { this.unit = unit; }
  
  public ZonedDateTime start() { return start; }
  public void start(ZonedDateTime start) { this.start = start; }
  
  public ZonedDateTime end() { return end; }
  public void end(ZonedDateTime end) { this.end = end; }
  
  public ZonedDateTime creation() { return creation; }
  public void creation(ZonedDateTime creation) { this.creation = creation; }
}
