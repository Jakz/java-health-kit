package com.github.jakz.healthkit.data.constants;

import java.util.HashMap;
import java.util.Map;

public enum SampleType
{
  QUANTITY_ACTIVE_ENERGY_BURNED("HKQuantityTypeIdentifierActiveEnergyBurned"),
  QUANTITY_BASAL_ENERGY_BURNED("HKQuantityTypeIdentifierBasalEnergyBurned"),
  QUANTITY_BODY_MASS("HKQuantityTypeIdentifierBodyMass"),
  QUANTITY_FLIGHTS_CLIMBED("HKQuantityTypeIdentifierFlightsClimbed"),
  QUANTITY_HEART_RATE("HKQuantityTypeIdentifierHeartRate"),
  QUANTITY_STEP_COUNT("HKQuantityTypeIdentifierStepCount"),
  
  QUANTITY_APPLE_EXERCISE_TIME("HKQuantityTypeIdentifierAppleExerciseTime"),
  
  QUANTITY_DISTANCE_CYCLING("HKQuantityTypeIdentifierDistanceCycling"),
  QUANTITY_DISTANCE_WALKING_RUNNING("HKQuantityTypeIdentifierDistanceWalkingRunning"),
  
  QUANTITY_HEIGHT("HKQuantityTypeIdentifierHeight"),
  
  
  CATEGORY_APPLE_STAND_HOUR("HKCategoryTypeIdentifierAppleStandHour"),
  CATEGORY_SLEEP_ANALYSIS("HKCategoryTypeIdentifierSleepAnalysis")
 
  ;
  
  private SampleType(String key) { this(key, key); }
  
  private SampleType(String key, String description)
  {
    this.key = key;
    this.description = description;
  }
  
  public final String key;
  public final String description;
  
  private static final Map<String, SampleType> mapping = new HashMap<>();
  
  static
  {
    for (SampleType type : values())
      mapping.put(type.key, type);
  }
  
  public static SampleType forKey(String key)
  { 
    return mapping.get(key);
  }
}
