package com.github.jakz.healthkit.data;

public class WorkoutType
{
  private final String description;
  
  public WorkoutType(String description)
  {
    this.description = description;
  }
  
  public String description() { return description; }
  
  public static WorkoutType forKey(String key)
  {
    //TODO: make enum
    return new WorkoutType(key);
  }
}
