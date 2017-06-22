package com.github.jakz.healthkit.data;

public class Metadata
{
  String key;
  String value;
  
  public Metadata()
  {
    
  }
  
  public Metadata(String key, String value)
  {
    this.key = key;
    this.value = value;
  }
  
  public String key() { return key; }
  public void key(String key) { this.key = key; }
  
  public String value() { return value; }
  public void value(String key) { this.value = key; }
}
