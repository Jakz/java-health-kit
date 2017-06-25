package com.github.jakz.healthkit;

import java.awt.Color;
import java.awt.Dimension;
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
import com.pixbits.lib.ui.canvas.CanvasPanel;
import com.pixbits.lib.ui.canvas.Rectangle;

/**
 * Hello world!
 *
 */
public class App 
{
  public static void testChart()
  {
    CanvasPanel canvas = new CanvasPanel(new Dimension(800,600));
    Rectangle r1 = new Rectangle(30,30,50,50, Color.RED);
    r1.strokeColor(Color.BLACK);
    r1.strokeWidth(2.0f);
    
    canvas.add(r1);
        
    WrapperFrame<?> frame = UIUtils.buildFrame(canvas, "Chart");
    frame.exitOnClose();
    frame.setVisible(true);
  }
  
  public static void main( String[] args )
  {
    try
    {
      UIUtils.setNimbusLNF();
      
      /*if (true)
      {
        testChart();
        return;
      }*/
      
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
