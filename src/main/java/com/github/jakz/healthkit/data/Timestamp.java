package com.github.jakz.healthkit.data;

import java.time.ZonedDateTime;

public class Timestamp
{
  ZonedDateTime start;
  ZonedDateTime end;
  ZonedDateTime creation;
  
  public Timestamp(ZonedDateTime start, ZonedDateTime end, ZonedDateTime creation)
  {
    this.start = start;
    this.end = end;
    this.creation = creation;
  }
  
  public Timestamp(ZonedDateTime start, ZonedDateTime end)
  {
    this(start, end, null);
  }
  
  public ZonedDateTime start() { return start; }
  public ZonedDateTime end() { return end; }
  public ZonedDateTime creation() { return creation; }
}
