package com.github.jakz.healthkit;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.github.jakz.healthkit.data.SampleType;
import com.github.jakz.healthkit.data.StandardUnit;
import com.github.jakz.healthkit.data.Unit;
import com.pixbits.lib.io.xml.XMLHandler;
import com.pixbits.lib.io.xml.XMLParser;

public class Parser extends XMLHandler<Void>
{
  enum Status
  {
    ROOT,
    HEALTH_DATA,
    
    RECORD
  }
  
  Status status;
  
  Locale locale;
  
  Set<String> keys = new HashSet<>();
  
  @Override 
  protected void start(String ns, String name, Attributes attr) throws SAXException
  {
    if (name.equals("HealthData"))
    {
      if (status != Status.ROOT)
        throw new InvalidDataException("Found <HealthData> node not at top level.");
      else
      {
        String stringLocale = attrString("locale");
        locale = Locale.forLanguageTag(stringLocale);
        System.out.println(locale);
      }       
    }
    else if (name.equals("Record"))
    {
      status = Status.RECORD;
      String stringType = attrString("type");
      
      SampleType type;
      Unit unit;
      
      /* parse sample type */
      type = SampleType.forKey(stringType);
      if (type == null)
        throw new InvalidDataException("Unknown record type: "+stringType);

      /* parse optonal unit */
      String stringUnit = attrStringOptional("unit");
      if (stringUnit != null)
        unit = StandardUnit.forKey(stringUnit);
      
      /* parse dates
         startDate, endDate required
         creationDate optional */
      String stringStartDate = attrString("startDate");
      String stringEndDate = attrString("endDate");
      String stringCreationDate = attrStringOptional("creationDate");
      
      ZonedDateTime startDate = parseDate(stringStartDate);
      ZonedDateTime endDate = parseDate(stringEndDate);
      ZonedDateTime creationDate = stringCreationDate != null ? parseDate(stringCreationDate) : null;

    }
  }
    
  @Override 
  protected void end(String ns, String name) throws SAXException
  {
    if (name.equals("Record"))
    {
      status = Status.HEALTH_DATA;
    }
  }
  
  @Override
  protected void init()
  {
    status = Status.ROOT;
  }

  @Override
  public Void get()
  {
    for (String key : keys)
      System.out.println("Key: "+key);
    
    return null;
  }

  protected ZonedDateTime parseDate(String value)
  {
    String[] tokens = value.split(" ");
    
    try
    {
      assertTrue(tokens.length == 3);
      Integer[] day = Arrays.stream(tokens[0].split("-"))
        .map(s -> Integer.parseInt(s))
        .toArray(i -> new Integer[3]);
      
      assertTrue(day.length == 3);
      
      Integer[] hour = Arrays.stream(tokens[1].split(":"))
        .map(s -> Integer.parseInt(s))
        .toArray(i -> new Integer[3]);
      
      int zone = Integer.parseInt(tokens[2]);
      int zoneHours = zone / 100;
      int zoneMinutes = zone % 100;
      
      return ZonedDateTime.of(day[0], day[1], day[2], hour[0], hour[1], hour[2], 0, ZoneOffset.ofHoursMinutes(zoneHours, zoneMinutes));
    }
    catch (NumberFormatException|SAXException e)
    {
      throw new InvalidDataException("Error while parsing date '"+value+"'");
    }
  }


}
