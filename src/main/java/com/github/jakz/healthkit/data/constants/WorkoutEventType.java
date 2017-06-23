package com.github.jakz.healthkit.data.constants;

import java.util.HashMap;
import java.util.Map;

public enum WorkoutEventType
{
  PAUSE("HKWorkoutEventTypePause"),
  RESUME("HKWorkoutEventTypeResume"),
  LAP("HKWorkoutEventTypeLap"),
  MARKER("HKWorkoutEventTypeMarker"),
  MOTION_PAUSED("HKWorkoutEventTypeMotionPaused"),
  MOTION_RESUMED("HKWorkoutEventTypeMotionResumed")
 
  ;
  
  private WorkoutEventType(String key)
  {
    this.key = key;
  }
  
  public final String key;
  
  private static final Map<String, WorkoutEventType> mapping = new HashMap<>();
  
  static
  {
    for (WorkoutEventType type : values())
      mapping.put(type.key, type);
  }
  
  public static WorkoutEventType forKey(String key)
  { 
    return mapping.get(key);
  }
}
