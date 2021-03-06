package com.github.jakz.healthkit.data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.jakz.healthkit.data.constants.SampleType;
import com.pixbits.lib.ui.charts.Measurable;

public class Sample implements Timed, Measurable
{
  private SampleType type;
  private Value value;
  private Timestamp timestamp;
  
  private Source source;
  
  private List<Metadata> metadata;
  
  public Sample()
  {
    
  }
  
  public SampleType type() { return type; }
  public void type(SampleType type) { this.type = type; }
  
  public Value value() { return value; }
  public void value(Value value) { this.value = value; }
  
  public Timestamp timestamp() { return timestamp; }
  public void timestamp(Timestamp timestamp) { this.timestamp = timestamp; }
  
  public Source source() { return source; }
  public void source(Source source) { this.source = source; }

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

  @Override public float chartValue() { return value.value.floatValue(); }
}
