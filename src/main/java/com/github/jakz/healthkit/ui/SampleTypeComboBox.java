package com.github.jakz.healthkit.ui;

import java.util.function.Predicate;

import javax.swing.JComboBox;

import com.github.jakz.healthkit.data.constants.SampleType;

public class SampleTypeComboBox extends JComboBox<SampleType>
{
  public SampleTypeComboBox(boolean includeEmpty)
  {
    super(SampleType.values());
  }
  
  public Predicate<SampleType> predicate()
  {
    final SampleType type = this.getItemAt(this.getSelectedIndex());
    return st -> st.equals(type);
  }
}
