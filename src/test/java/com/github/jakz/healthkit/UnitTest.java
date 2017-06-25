package com.github.jakz.healthkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.jakz.healthkit.data.Value;
import com.github.jakz.healthkit.data.constants.StandardUnit;

public class UnitTest
{  
  @Test
  public void testUpLengthConversion1()
  {
    Value value = new Value(StandardUnit.CM, 1000.0f);
    Value cvalue = value.convert(StandardUnit.M);
    
    assertEquals(StandardUnit.M, cvalue.unit());
    assertEquals(10, cvalue.ivalue());
  }
  
  @Test
  public void testDownLengthConversion1()
  {
    Value value = new Value(StandardUnit.KM, 1.0f);
    Value cvalue = value.convert(StandardUnit.M);
    
    assertEquals(StandardUnit.M, cvalue.unit());
    assertEquals(1000, cvalue.ivalue());
  }
  
  @Test
  public void testTimeConversion()
  {
    Value value = new Value(StandardUnit.HOURS, 3.2f);
    Value cvalue = value.convert(StandardUnit.MINUTES);
    
    assertEquals(StandardUnit.MINUTES, cvalue.unit());
    assertEquals((int)(60*3.2f), cvalue.ivalue());
  }
}
