package com.github.jakz.healthkit.data;

import java.util.ArrayList;
import java.util.List;

public class Workout
{
  private WorkoutType type;
  
  private Value duration;
  private Value distance;
  private Value energyBurned;
  
  private Source source;
  private Timestamp timestamp;
  
  private List<WorkoutEvent> events;
  private List<Metadata> metadata;
  
  public void type(WorkoutType type) { this.type = type; }
  public WorkoutType type() { return type; }

  public Value duration() { return duration; }
  public void duration(Value duration) { this.duration = duration; }
  
  public Value distance() { return distance; }
  public void distance(Value distance) { this.distance = distance; }
  
  public Value energyBurned() { return energyBurned; }
  public void energyBurned(Value energyBurned) { this.energyBurned = energyBurned; }
  
  public Source source() { return source; }
  public void source(Source source) { this.source = source; }
  
  public Timestamp timestamp() { return timestamp; }
  public void timestamp(Timestamp timestamp) { this.timestamp = timestamp; }
  
  
  public int metadataCount() { return metadata == null ? 0 : metadata.size(); }
  
  public void metadata(Metadata metadata)
  {
    if (this.metadata == null) this.metadata = new ArrayList<>();
    this.metadata.add(metadata);
  }
  
  public Metadata metadata(int index)
  {
    return metadata == null ? null : metadata.get(index);
  }
  
  public int eventCount() { return events == null ? 0 : events.size(); }
  
  public void event(WorkoutEvent event)
  {
    if (this.events == null) this.events = new ArrayList<>();
    this.events.add(event);
  }
  
  public WorkoutEvent event(int index)
  {
    return events == null ? null : events.get(index);
  }
}
