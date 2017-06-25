package com.github.jakz.healthkit.ui;

import java.time.format.DateTimeFormatter;

public class Formatters
{
  public static DateTimeFormatter startDate = DateTimeFormatter.ofPattern("dd/MM/yyyy - kk:mm:ss");
  public static DateTimeFormatter endDate = DateTimeFormatter.ofPattern("kk:mm:ss");
  //public static DateTimeFormatter startDate = DateTimeFormatter.ofPattern("dd/MM/yyyy - kk:mm:ss Z");
}
