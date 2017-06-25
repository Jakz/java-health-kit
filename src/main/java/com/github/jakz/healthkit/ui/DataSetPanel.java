package com.github.jakz.healthkit.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.github.jakz.healthkit.data.DataSet;

public class DataSetPanel extends JPanel
{
  private DataSet set;
  
  private SampleTable sampleTable;
  private WorkoutTable workoutTable;
  
  private JTabbedPane tabs;
  private DataSetStatsPanel statsPanel;
  
  public DataSetPanel(DataSet set)
  {
    this.set = set;
    
    sampleTable = new SampleTable(set.createDataSourceForSamples());
    SampleTablePanel samplePanel = new SampleTablePanel(sampleTable, new Dimension(800,600));
    
    workoutTable = new WorkoutTable(set.createDataSourceForWorkouts());
    SampleTablePanel workoutPanel = new SampleTablePanel(workoutTable, new Dimension(800,600));
    
    statsPanel = new DataSetStatsPanel(set);
    
    tabs = new JTabbedPane();
    tabs.add("Samples", samplePanel);
    tabs.add("Workouts", workoutPanel);
    
    setLayout(new BorderLayout());
    add(tabs, BorderLayout.CENTER);
    add(statsPanel, BorderLayout.SOUTH);
  }
}
