package com.github.jakz.healthkit.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.jakz.healthkit.data.DataSet;
import com.github.jakz.healthkit.data.SampleSet;

public class DataSetStatsPanel extends JPanel
{
  private DataSet data;
  private final JLabel label;
  
  public DataSetStatsPanel(DataSet data)
  {
    this.data = data;
    this.label = new JLabel();
    
    this.add(label);
    refresh();
  }
  
  public void refresh()
  {
    label.setText(String.format("Samples: %d, workouts: %d", data.samplesCount(), data.workoutCount()));
  }
}
