package com.github.jakz.healthkit;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.event.ChangeListener;

import com.github.jakz.healthkit.data.DataSet;
import com.github.jakz.healthkit.data.Filters;
import com.github.jakz.healthkit.data.Sample;
import com.github.jakz.healthkit.data.Value;
import com.github.jakz.healthkit.data.constants.SampleType;
import com.github.jakz.healthkit.data.constants.StandardUnit;
import com.github.jakz.healthkit.ui.DataSetPanel;
import com.pixbits.lib.io.MonitoredInputStream;
import com.pixbits.lib.io.xml.XMLParser;
import com.pixbits.lib.ui.UIUtils;
import com.pixbits.lib.ui.WrapperFrame;
import com.pixbits.lib.ui.canvas.CanvasPanel;
import com.pixbits.lib.ui.canvas.Rectangle;
import com.pixbits.lib.ui.charts.BarChartPanel;
import com.pixbits.lib.ui.charts.Measurable;

/**
 * Hello world!
 *
 */
public class App 
{
  public static void testChart(List<Sample> values)
  {
    BarChartPanel<Sample> canvas = new BarChartPanel<Sample>(new Dimension(800,600));
    canvas.setAutoRebuild(true);
    canvas.add(values);
    
    WrapperFrame<?> frame = UIUtils.buildFrame(canvas, "Chart");
    frame.exitOnClose();
    frame.setVisible(true);
  }
  
  public static List<Sample> generateSampleSet(int count, float min, float max)
  {
    final Random r = new Random();
    Stream<Sample> samples = Stream.generate(() -> {
      Sample sample = new Sample();
      sample.value(new Value(StandardUnit.KM, r.nextFloat()*(max-min) + min));
      return sample;
    });
    return samples.limit(count).collect(Collectors.toList());
  }
  
  public static void main( String[] args )
  {
    try
    {
      if (true)
      {
        List<Sample> samples = generateSampleSet(200, 100.0f, 170.0f);
        testChart(samples);
        return;
      }
      
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
      
      List<Sample> filtered = set.sampleStream()
          .filter(
              Filters.and(
                Filters.ofType(SampleType.QUANTITY_HEART_RATE),
                Filters.onDay(LocalDate.of(2017, 6, 21))
              )
          )
          .collect(Collectors.toList());
      
      testChart(filtered);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
