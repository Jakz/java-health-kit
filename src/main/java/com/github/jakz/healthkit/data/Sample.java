package com.github.jakz.healthkit.data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sample
{
  private ZonedDateTime start;
  private ZonedDateTime end;
  private ZonedDateTime creation;
  
  private SampleType type;
  private Value value;
  
  private String sourceName;
  private String sourceVersion;
  private String device;
  
  private List<Metadata> metadata;
  
  public Sample()
  {
    
  }
  
  public SampleType type() { return type; }
  public void type(SampleType type) { this.type = type; }
  
  public Value value() { return value; }
  public void value(Value value) { this.value = value; }
  
  public ZonedDateTime start() { return start; }
  public void start(ZonedDateTime start) { this.start = start; }
  
  public ZonedDateTime end() { return end; }
  public void end(ZonedDateTime end) { this.end = end; }
  
  public ZonedDateTime creation() { return creation; }
  public void creation(ZonedDateTime creation) { this.creation = creation; }
  
  public String sourceName() { return sourceName; }
  public void sourceName(String sourceName) { this.sourceName = sourceName; }
  
  public String sourceVersion() { return sourceVersion; }
  public void sourceVersion(String sourceVersion) { this.sourceVersion = sourceVersion; }
  
  public String device() { return device; }
  public void device(String device) { this.device = device; }
  
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

}
