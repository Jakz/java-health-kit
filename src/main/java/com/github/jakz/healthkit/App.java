package com.github.jakz.healthkit;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.event.ChangeListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.jakz.healthkit.data.DataSet;
import com.github.jakz.healthkit.data.Filters;
import com.github.jakz.healthkit.data.Sample;
import com.github.jakz.healthkit.data.Value;
import com.github.jakz.healthkit.data.constants.SampleType;
import com.github.jakz.healthkit.data.constants.StandardUnit;
import com.github.jakz.healthkit.ui.DataSetPanel;
import com.pixbits.lib.io.stream.MonitoredInputStream;
import com.pixbits.lib.io.xml.XMLParser;
import com.pixbits.lib.io.xml.gpx.Gpx;
import com.pixbits.lib.io.xml.gpx.GpxExtension;
import com.pixbits.lib.io.xml.gpx.GpxParser;
import com.pixbits.lib.io.xml.gpx.GpxTrackSegment;
import com.pixbits.lib.io.xml.gpx.GpxWaypoint;
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
      /*if (true)
      {
        List<Sample> samples = generateSampleSet(200, 100.0f, 170.0f);
        testChart(samples);
        return;
      }*/
      
      UIUtils.setNimbusLNF();

      Path xmlPath = Paths.get("/Users/jack/Desktop/apple_health_export/export.xml"); 
      
      BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(xmlPath));
      MonitoredInputStream mis = new MonitoredInputStream(bis);
      final long fileSize = Files.size(xmlPath);
      /*ChangeListener listener = e -> System.out.println(((MonitoredInputStream)e.getSource()).location() / (float)fileSize);
      mis.addChangeListener(listener);*/
            
      XMLParser<DataSet> parser = new XMLParser<>(new Parser());
      DataSet set = parser.load(mis);
      
      Predicate<Sample> filter =           
        Filters.and(
            Filters.ofType(SampleType.QUANTITY_HEART_RATE),
            Filters.onDay(LocalDate.of(2017, 10, 18))

            /*Filters.and(
                Filters.isAfter(LocalDateTime.of(2017, 11, 04, 15, 0, 0)),
                Filters.isBefore(LocalDateTime.of(2017, 11, 18, 23, 59))
            )*/
        );
      
      DataSetPanel dataSetPanel = new DataSetPanel(set);
      dataSetPanel.filterSamples(filter);
      
      WrapperFrame<?> frame = UIUtils.buildFrame(dataSetPanel, "Sample Table");
      frame.exitOnClose();
      frame.setVisible(true);
      
      //if (true)
      //  return;
      
      /*List<Sample> filtered = set.sampleStream()
          .filter(
              Filters.and(
                Filters.ofType(SampleType.QUANTITY_HEART_RATE),
                Filters.onDay(LocalDate.of(2017, 6, 5))
              )
          )
          .collect(Collectors.toList());
      */
      //testChart(filtered);
      
      List<Sample> fset = set.sampleStream().filter(filter).collect(Collectors.toList());
      
      /*long adjust = 0;//- 20;
      int duration = (60+38)*60 + 03;
      LocalTime start = LocalTime.of(19, 27, 24);
                
      for (int i = 0; i < duration; i += 1)
      {
        LocalTime instant = start.plusSeconds(i);
        int current = Integer.MAX_VALUE;
        long min = Long.MAX_VALUE;
        for (Sample s : fset)
        {
          long delta = Math.abs(instant.get(ChronoField.SECOND_OF_DAY) - s.timestamp().start().getLong(ChronoField.SECOND_OF_DAY) - adjust);
          if (delta < min || current == Integer.MAX_VALUE)
          {
            current = s.value().ivalue();
            min = delta;
          }
        }
        
        System.out.println(current);
      }*/
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      Document document = null;
      try {
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();
      }catch (ParserConfigurationException parserException) {
        parserException.printStackTrace();
      }

      long adjust = 60*60;//- 20;
      Gpx gpx = GpxParser.parse(Paths.get("/Users/jack/Desktop/Fix.gpx"));
      GpxTrackSegment segment = gpx.tracks().get(0).segments().get(0);
      for (GpxWaypoint pt : segment)
      {
        LocalDateTime time = pt.time().toLocalDateTime();
        
        int current = Integer.MAX_VALUE;
        long min = Long.MAX_VALUE;
        for (Sample s : fset)
        {
          long delta = Math.abs(time.get(ChronoField.SECOND_OF_DAY) - s.timestamp().start().getLong(ChronoField.SECOND_OF_DAY) + adjust);
          if (delta < min || current == Integer.MAX_VALUE)
          {
            current = s.value().ivalue();
            min = delta;
          }
        }
        
        if (pt.extensions() != null)
          pt.extensions().clear();
        else
          pt.setExtensions(new GpxExtension());
        
        
        CDATASection value = document.createCDATASection(Integer.toString(current));
        Element valueNode = document.createElement("antanihr");
        valueNode.appendChild(value);
        
        Element element = document.createElement("antaniTrackPointExtension");
        element.appendChild(valueNode);
        
        pt.extensions().add(element);
        
        System.out.println(time + " -> " + current);
      }

      GpxParser.save(gpx, Paths.get("/Users/jack/Desktop/test2.gpx"));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
