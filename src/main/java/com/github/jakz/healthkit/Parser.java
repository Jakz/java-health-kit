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
import java.util.function.Predicate;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.github.jakz.healthkit.data.ActivitySummary;
import com.github.jakz.healthkit.data.DataSet;
import com.github.jakz.healthkit.data.Filters;
import com.github.jakz.healthkit.data.Me;
import com.github.jakz.healthkit.data.Metadata;
import com.github.jakz.healthkit.data.Sample;
import com.github.jakz.healthkit.data.SampleSet;
import com.github.jakz.healthkit.data.Source;
import com.github.jakz.healthkit.data.Timestamp;
import com.github.jakz.healthkit.data.Unit;
import com.github.jakz.healthkit.data.Value;
import com.github.jakz.healthkit.data.Workout;
import com.github.jakz.healthkit.data.WorkoutEvent;
import com.github.jakz.healthkit.data.WorkoutType;
import com.github.jakz.healthkit.data.constants.BloodType;
import com.github.jakz.healthkit.data.constants.SampleType;
import com.github.jakz.healthkit.data.constants.Sex;
import com.github.jakz.healthkit.data.constants.SkinType;
import com.github.jakz.healthkit.data.constants.StandardUnit;
import com.github.jakz.healthkit.data.constants.WorkoutEventType;
import com.github.jakz.healthkit.exceptions.InvalidDataException;
import com.pixbits.lib.io.xml.XMLHandler;
import com.pixbits.lib.io.xml.XMLParser;
import com.pixbits.lib.lang.Pair;

public class Parser extends XMLHandler<DataSet>
{
  enum Status
  {
    ROOT,
    HEALTH_DATA,
    
    RECORD,
    WORKOUT
  }
  
  boolean parseOptionalFields;
  
  Status status;
  
  ZonedDateTime exportDate;
  Locale locale;
  
  
  
  Set<String> keys = new HashSet<>();
  
  List<Sample> samples;
  List<Workout> workouts;
  
  Me me;
  
  Sample sample;
  Workout workout;
  
  Metadata metadata;
  
  private boolean shouldSkip(boolean start)
  {
    return false && samples.size() > (start ? 100 : 101);
  }
  
  @Override 
  protected void start(String ns, String name, Attributes attr) throws SAXException
  {
    if (shouldSkip(true)) return;
    
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
      sample.value(parseValue("unit", "value"));

      /* parse dates
         startDate, endDate required
         creationDate optional */
      sample.timestamp(parseTimestamp());
      
      /* parse source data fields */
      if (parseOptionalFields)
        workout.source(parseSource());
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
      
      me.birth(parseSimpleDate(attrString(DATE_OF_BIRTH_KEY)));
      me.sex(Sex.forKey(attrString(SEX_KEY)));
      me.blood(BloodType.forKey(attrString(BLOOD_TYPE_KEY)));
      me.skin(SkinType.forKey(attrString(SKIN_TYPE_KEY)));
    }
    else if (name.equals("MetadataEntry"))
    {
      assertTrue(metadata == null);
      metadata = new Metadata(attrString("key"), attrString("value"));
    }
    else if (name.equals("Workout"))
    {
      assertTrue(workout == null);
      status = Status.WORKOUT;
      workout = new Workout();

      workout.type(WorkoutType.forKey(attrString("workoutActivityType")));
      workout.duration(parseValue("durationUnit", "duration"));
      workout.distance(parseValue("totalDistanceUnit", "totalDistance"));
      workout.energyBurned(parseValue("totalEnergyBurnedUnit", "totalEnergyBurned"));
      workout.timestamp(parseTimestamp());
      
      if (parseOptionalFields)
        workout.source(parseSource());
    }
    else if (name.equals("WorkoutEvent"))
    {
      assertTrue(workout != null);
      workout.event(new WorkoutEvent(
        WorkoutEventType.forKey(attrString("type")),
        parseDate(attrString("date"))
      ));
    }
    else if (name.equals("WorkoutRoute") || name.equals("Location"))
    {
      //TODO: ignored for now
    }
    else if (name.equals("ActivitySummary"))
    {
      String date = attrStringOptional("dateComponents");
      
      /*
      ActivitySummary summary = new ActivitySummary(
        date != null ? parseSimpleDate(date) : null,
        
      );
      
          <!ATTLIST ActivitySummary
          dateComponents           CDATA #IMPLIED
          activeEnergyBurned       CDATA #IMPLIED
          activeEnergyBurnedGoal   CDATA #IMPLIED
          activeEnergyBurnedUnit   CDATA #IMPLIED
          appleExerciseTime        CDATA #IMPLIED
          appleExerciseTimeGoal    CDATA #IMPLIED
          appleStandHours          CDATA #IMPLIED
          appleStandHoursGoal      CDATA #IMPLIED
        >

      */
      
      //TODO: add it somewhere
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
    if (shouldSkip(true)) return;

    
    if (name.equals("Record"))
    {
      assert(sample != null);   

      /*LocalDate first = LocalDate.of(2017, 6, 1);
      LocalDate last = LocalDate.of(2017, 6, 21);
      Predicate<Sample> predicate = Filters.ofType(SampleType.QUANTITY_HEART_RATE).and(Filters.inDayRange(first, last));
      
      if (predicate.test(sample))
        samples.add(sample);*/
        
      samples.add(sample);
      sample = null;
      
      status = Status.HEALTH_DATA;

    }
    else if (name.equals("MetadataEntry"))
    {
      if (status == Status.RECORD)
      {
        assertTrue(sample != null);
        sample.metadata(metadata);
        metadata = null;
      }
      else if (status == Status.WORKOUT)
      {
        assertTrue(workout != null);
        workout.metadata(metadata);
        metadata = null;
      }
      else
        throw new InvalidDataException("Unknown parent node for MetadataEntry (status: "+status+")");
    }
    else if (name.equals("Workout"))
    {
      assertTrue(workout != null);
      workouts.add(workout);
      workout = null;
      
      status = Status.HEALTH_DATA;
    }
  }
  
  @Override
  protected void init()
  {
    status = Status.ROOT;
    
    locale = null;
    exportDate = null;
    me = null;
    
    sample = null;
    samples = new ArrayList<>();
    
    workout = null;
    workouts = new ArrayList<>();
  }

  @Override
  public DataSet get()
  {
    for (String key : keys)
      System.out.println("Key: "+key);
    
    return new DataSet(samples, workouts);
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

  protected Value parseValue(String unitKey, String valueKey) throws SAXException
  {
    String stringUnit = attrStringOptional(unitKey);
    if (stringUnit != null)
    {
      StandardUnit unit = StandardUnit.forKey(stringUnit);
      
      if (unit == null)
        throw new InvalidDataException("Unknown unit '"+stringUnit+"'");

      return unit.parseValue(attrString(valueKey));
    } 
    else
      return null;
  }
  
  protected Source parseSource() throws SAXException
  {
    return new Source(
        attrString("sourceName"),
        attrStringOptional("sourceVersion"),
        attrStringOptional("device")
    );
  }
  
  protected LocalDate parseSimpleDate(String value)
  {
    Integer[] birthTokens = Arrays.stream(value.split("-"))
        .map(s -> Integer.parseInt(s))
        .toArray(i -> new Integer[i]);
    return LocalDate.of(birthTokens[0], birthTokens[1], birthTokens[2]);
  }
  
  protected Timestamp parseTimestamp() throws SAXException
  {
    /* parse dates
    startDate, endDate required
    creationDate optional */
    String stringStartDate = attrString("startDate");
    String stringEndDate = attrString("endDate");
    String stringCreationDate = attrStringOptional("creationDate");
    
    ZonedDateTime startDate = parseDate(stringStartDate);
    ZonedDateTime endDate = parseDate(stringEndDate);
    ZonedDateTime creationDate = stringCreationDate != null ? parseDate(stringCreationDate) : null;
    
    return new Timestamp(startDate, endDate, creationDate);
  }


}
