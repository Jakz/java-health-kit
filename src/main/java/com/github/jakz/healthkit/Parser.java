package com.github.jakz.healthkit;

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



}
