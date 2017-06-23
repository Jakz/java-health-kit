package com.github.jakz.healthkit.data;

import java.time.LocalDate;

public class ActivitySummary
{
  LocalDate date;
  GoaledValue activeEnergy;
  GoaledValue exerciseTime;
  GoaledValue standHours;
  
  public ActivitySummary(LocalDate date, GoaledValue activeEnergy, GoaledValue exerciseTime, GoaledValue standHours)
  {
    this.date = date;
    this.activeEnergy = activeEnergy;
    this.exerciseTime = exerciseTime;
    this.standHours = standHours;
  }
  
  public LocalDate date() { return date; }
  
  public GoaledValue activeEnergy() { return activeEnergy; }
  public GoaledValue exerciseTime() { return exerciseTime; }
  public GoaledValue standHours() { return standHours; }
}
