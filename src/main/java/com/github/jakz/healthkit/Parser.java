package com.github.jakz.healthkit;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.github.jakz.healthkit.data.BloodType;
import com.github.jakz.healthkit.data.Me;
import com.github.jakz.healthkit.data.Metadata;
import com.github.jakz.healthkit.data.Sample;
import com.github.jakz.healthkit.data.SampleSet;
import com.github.jakz.healthkit.data.SampleType;
import com.github.jakz.healthkit.data.Sex;
import com.github.jakz.healthkit.data.SkinType;
import com.github.jakz.healthkit.data.Source;
import com.github.jakz.healthkit.data.StandardUnit;
import com.github.jakz.healthkit.data.Timestamp;
import com.github.jakz.healthkit.data.Unit;
import com.pixbits.lib.io.xml.XMLHandler;
import com.pixbits.lib.io.xml.XMLParser;
import com.pixbits.lib.lang.Pair;

public class Parser extends XMLHandler<SampleSet>
{
  enum Status
  {
    ROOT,
    HEALTH_DATA,
    
    RECORD
  }
  
  boolean parseOptionalFields;
  
  Status status;
  
  ZonedDateTime exportDate;
  Locale locale;
  
  
  
  Set<String> keys = new HashSet<>();
  
  List<Sample> samples;
  Me me;
  Sample sample;
  Metadata metadata;
  
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
      assertTrue(sample == null);
      sample = new Sample();
      
      status = Status.RECORD;
      String stringType = attrString("type");
      
      SampleType type;
      Unit unit;
      
      /* parse sample type */
      type = SampleType.forKey(stringType);
      if (type == null)
        throw new InvalidDataException("Unknown record type: "+stringType);
      sample.type(type);


      /* parse optonal value */
      String stringUnit = attrStringOptional("unit");
      if (stringUnit != null)
        sample.value(StandardUnit.forKey(stringUnit).parseValue(attrString("value")));
      
      /* parse dates
         startDate, endDate required
         creationDate optional */
      String stringStartDate = attrString("startDate");
      String stringEndDate = attrString("endDate");
      String stringCreationDate = attrStringOptional("creationDate");
      
      ZonedDateTime startDate = parseDate(stringStartDate);
      ZonedDateTime endDate = parseDate(stringEndDate);
      ZonedDateTime creationDate = stringCreationDate != null ? parseDate(stringCreationDate) : null;
      
      sample.timestamp(new Timestamp(startDate, endDate, creationDate));
      
      /* parse source data fields */
      if (parseOptionalFields)
      { 
        sample.source(new Source(
          attrString("sourceName"),
          attrStringOptional("sourceVersion"),
          attrStringOptional("device")
        ));
      }
    }
    else if (name.equals("ExportDate"))
    {
      exportDate = parseDate(attrString("value"));
    }
    else if (name.equals("Me"))
    {
      assert(me == null);
      me = new Me();
      
      final String DATE_OF_BIRTH_KEY = "HKCharacteristicTypeIdentifierDateOfBirth";
      final String SEX_KEY = "HKCharacteristicTypeIdentifierBiologicalSex";
      final String BLOOD_TYPE_KEY = "HKCharacteristicTypeIdentifierBloodType";
      final String SKIN_TYPE_KEY = "HKCharacteristicTypeIdentifierFitzpatrickSkinType";
      
      String birthString = attrString(DATE_OF_BIRTH_KEY);
      Integer[] birthTokens = Arrays.stream(birthString.split("-"))
          .map(s -> Integer.parseInt(s))
          .toArray(i -> new Integer[i]);
      me.birth(LocalDate.of(birthTokens[0], birthTokens[1], birthTokens[2]));
      
      me.sex(Sex.forKey(attrString(SEX_KEY)));
      me.blood(BloodType.forKey(attrString(BLOOD_TYPE_KEY)));
      me.skin(SkinType.forKey(attrString(SKIN_TYPE_KEY)));
    }
    else if (name.equals("MetadataEntry"))
    {
      assertTrue(metadata == null);
      metadata = new Metadata(attrString("key"), attrString("value"));
    }
    else
    {
      Iterator<Pair<String,String>> it = attrIterator();
      while (it.hasNext())
      {
        Pair<String,String> attribute = it.next();
        System.err.println("Key: "+attribute.first+" Value: "+attribute.second);
      }

      throw new InvalidDataException("Unknown node name: "+name);
    }
  }
    
  @Override 
  protected void end(String ns, String name) throws SAXException
  {
    if (name.equals("Record"))
    {
      status = Status.HEALTH_DATA;
      assert(sample != null);
      
      samples.add(sample);
      sample = null;
    }
    else if (name.equals("MetadataEntry"))
    {
      if (status == Status.RECORD)
      {
        assertTrue(sample != null);
        sample.metadata(metadata);
        metadata = null;
      }
    }
  }
  
  @Override
  protected void init()
  {
    status = Status.ROOT;
    
    sample = null;
    samples = new ArrayList<>();
  }

  @Override
  public SampleSet get()
  {
    for (String key : keys)
      System.out.println("Key: "+key);
    
    return new SampleSet(samples);
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
