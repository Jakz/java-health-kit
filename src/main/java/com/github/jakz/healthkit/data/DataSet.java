package com.github.jakz.healthkit.data;

import java.util.List;
import java.util.stream.Stream;

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
  public Stream<Workout> workoutStream() { return workouts.stream(); }
  
  public int sampleCount() { return samples.size(); }
  public Stream<Sample> sampleStream() { return samples.stream(); }
  
  public FilterableDataSource<Sample> createDataSourceForSamples()
  {
    return new FilterableListDataSource<Sample>(samples);
  }
  
  public FilterableDataSource<Workout> createDataSourceForWorkouts()
  {
    return new FilterableListDataSource<Workout>(workouts);
  }
}
