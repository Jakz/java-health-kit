package com.github.jakz.healthkit;

import java.nio.file.Paths;

import com.pixbits.lib.io.xml.XMLParser;

/**
 * Hello world!
 *
 */
public class App 
{
  public static void main( String[] args )
  {
    XMLParser<Void> parser = new XMLParser<>(new Parser());
    try
    {
      parser.load(Paths.get("/Users/jack/Desktop/apple_health_export/export.xml"));
      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
