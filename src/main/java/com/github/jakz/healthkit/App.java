package com.github.jakz.healthkit;

import java.awt.Dimension;
import java.nio.file.Paths;

import com.github.jakz.healthkit.data.SampleSet;
import com.github.jakz.healthkit.ui.SampleTable;
import com.github.jakz.healthkit.ui.SampleTablePanel;
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
      
      XMLParser<SampleSet> parser = new XMLParser<>(new Parser());
      SampleSet set = parser.load(Paths.get("/Users/jack/Desktop/apple_health_export/export.xml"));
      
      SampleTable table = new SampleTable(set);
      SampleTablePanel panel = new SampleTablePanel(table, new Dimension(800,600));
      WrapperFrame<?> frame = UIUtils.buildFrame(panel, "Sample Table");
      frame.exitOnClose();
      frame.setVisible(true);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
