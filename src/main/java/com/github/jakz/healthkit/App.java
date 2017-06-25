package com.github.jakz.healthkit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import com.github.jakz.healthkit.data.DataSet;
import com.github.jakz.healthkit.data.SampleSet;
import com.github.jakz.healthkit.ui.DataSetStatsPanel;
import com.github.jakz.healthkit.ui.SampleTable;
import com.github.jakz.healthkit.ui.SampleTablePanel;
import com.pixbits.lib.io.MonitoredInputStream;
import com.pixbits.lib.io.xml.XMLParser;
import com.pixbits.lib.ui.UIUtils;
import com.pixbits.lib.ui.WrapperFrame;

/**
 * Hello world!
 *
 */
public class App 
{
  public static void main( String[] args )
  {
    try
    {
      UIUtils.setNimbusLNF();
      
      Path xmlPath = Paths.get("/Users/jack/Desktop/apple_health_export/export.xml"); 
      
      BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(xmlPath));
      MonitoredInputStream mis = new MonitoredInputStream(bis);
      final long fileSize = Files.size(xmlPath);
      ChangeListener listener = e -> System.out.println(((MonitoredInputStream)e.getSource()).location() / (float)fileSize);
      mis.addChangeListener(listener);
            
      XMLParser<DataSet> parser = new XMLParser<>(new Parser());
      DataSet set = parser.load(mis);
      
      SampleTable table = new SampleTable(set.createDataSourceForSamples());
      SampleTablePanel panel = new SampleTablePanel(table, new Dimension(800,600));
      DataSetStatsPanel statsPanel = new DataSetStatsPanel(set);
      
      JPanel container = new JPanel();
      container.setLayout(new BorderLayout());
      container.add(panel, BorderLayout.CENTER);
      container.add(statsPanel, BorderLayout.SOUTH);
      
      
      WrapperFrame<?> frame = UIUtils.buildFrame(container, "Sample Table");
      frame.exitOnClose();
      frame.setVisible(true);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
