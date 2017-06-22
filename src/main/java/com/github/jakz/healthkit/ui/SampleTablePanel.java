package com.github.jakz.healthkit.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SampleTablePanel extends JPanel
{
  private SampleTable table;
  private JScrollPane scrollPane;
  
  public SampleTablePanel(SampleTable table, Dimension dimension)
  {
    this.table = table;
    this.scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(dimension);
    this.setLayout(new BorderLayout());
    this.add(scrollPane, BorderLayout.CENTER);
  }
  
}
