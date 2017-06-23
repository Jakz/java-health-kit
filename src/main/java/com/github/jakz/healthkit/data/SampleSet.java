package com.github.jakz.healthkit.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pixbits.lib.ui.table.DataSource;

public class SampleSet implements DataSource<Sample>
{
  private List<Sample> allSamples;
  private List<Sample> samples;
    
  public SampleSet(List<Sample> samples)
  {
    allSamples = samples;
    this.samples = samples;
  }
  
  public SampleSet()
  {
    samples = new ArrayList<>();
    allSamples = new ArrayList<>();
  }
  
  @Override
  public Iterator<Sample> iterator()
  {
    return samples.iterator();
  }

  @Override
  public Sample get(int index)
  {
    return samples.get(index);
  }

  @Override
  public int size()
  {
    return samples.size();
  }

  @Override
  public int indexOf(Sample object)
  {
    return samples.indexOf(object);
  }

}
