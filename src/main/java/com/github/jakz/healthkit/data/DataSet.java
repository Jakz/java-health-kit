package com.github.jakz.healthkit.data;

import java.util.List;

import com.pixbits.lib.ui.table.FilterableDataSource;
import com.pixbits.lib.ui.table.FilterableListDataSource;

public class DataSet
{
  private List<Sample> samples;
  private List<Workout> workouts;
  
  public DataSet(List<Sample> samples, List<Workout> workouts)
  {
    this.samples = samples;
    this.workouts = workouts;
  }
  
  public int workoutCount() { return workouts.size(); }
  public int samplesCount() { return samples.size(); }
  
  public FilterableDataSource<Sample> createDataSourceForSamples()
  {
    return new FilterableListDataSource<Sample>(samples);
  }
  
  public FilterableDataSource<Sample> createDataSourceForWorkouts()
  {
    return new FilterableListDataSource<Sample>(samples);
  }
}
