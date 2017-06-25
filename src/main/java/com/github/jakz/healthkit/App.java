package com.github.jakz.healthkit;

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.event.ChangeListener;

import com.github.jakz.healthkit.data.DataSet;
import com.github.jakz.healthkit.ui.DataSetPanel;
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
      
      WrapperFrame<?> frame = UIUtils.buildFrame(new DataSetPanel(set), "Sample Table");
      frame.exitOnClose();
      frame.setVisible(true);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
