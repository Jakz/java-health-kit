package com.github.jakz.healthkit.data.constants;

import java.util.HashMap;
import java.util.Map;

public enum SampleType
{
  QUANTITY_ACTIVE_ENERGY_BURNED("HKQuantityTypeIdentifierActiveEnergyBurned", "Energy Burned (Active)"),
  QUANTITY_BASAL_ENERGY_BURNED("HKQuantityTypeIdentifierBasalEnergyBurned", "Energy Burned (Basal)"),
  QUANTITY_BODY_MASS("HKQuantityTypeIdentifierBodyMass", "Body Mass"),
  QUANTITY_FLIGHTS_CLIMBED("HKQuantityTypeIdentifierFlightsClimbed", "Flights Climbed"),
  QUANTITY_HEART_RATE("HKQuantityTypeIdentifierHeartRate", "Heart Rate"),
  QUANTITY_STEP_COUNT("HKQuantityTypeIdentifierStepCount", "Steps Count"),
  QUANTITY_VO2_MAX("HKQuantityTypeIdentifierVO2Max", "VO2 MAX"),
  
  QUANTITY_APPLE_EXERCISE_TIME("HKQuantityTypeIdentifierAppleExerciseTime", "Apple Exercise Time"),
  
  QUANTITY_DISTANCE_CYCLING("HKQuantityTypeIdentifierDistanceCycling", "Distance (Cycling)"),
  QUANTITY_DISTANCE_WALKING_RUNNING("HKQuantityTypeIdentifierDistanceWalkingRunning", "Distance (Walking/Running)"),
  
  QUANTITY_HEIGHT("HKQuantityTypeIdentifierHeight", "Height"),
  
  
  CATEGORY_APPLE_STAND_HOUR("HKCategoryTypeIdentifierAppleStandHour", "Apple Stand Hour"),
  CATEGORY_SLEEP_ANALYSIS("HKCategoryTypeIdentifierSleepAnalysis", "Sleep Analysis")
 
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
