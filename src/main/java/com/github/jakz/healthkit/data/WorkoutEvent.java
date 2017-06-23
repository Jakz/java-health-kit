package com.github.jakz.healthkit.data;

import java.time.ZonedDateTime;

import com.github.jakz.healthkit.data.constants.WorkoutEventType;

public class WorkoutEvent
{
  private WorkoutEventType type;
  private ZonedDateTime date;
  
  public WorkoutEvent(WorkoutEventType type, ZonedDateTime date)
  {
    this.type = type;
    this.date = date;
  }
  
  public WorkoutEventType type() { return type; }
  
  public ZonedDateTime date() { return date; }
}
