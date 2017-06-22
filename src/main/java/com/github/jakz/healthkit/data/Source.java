package com.github.jakz.healthkit.data;

public class Source
{
  private String name;
  private String version;
  private String device;
  
  public Source(String name, String version)
  {
    this(name, version, null);
  }
  
  public Source(String name, String version, String device)
  {
    this.name = name;
    this.version = version;
    this.device = device;
  }
  
  public String name() { return name; }
  public String version() { return version; }
  public String device() { return device; }
}
