package com.github.jakz.healthkit.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Predicate;

import com.github.jakz.healthkit.data.constants.SampleType;

public class Filters
{
  private static Predicate<Timestamp> onDayTimestamp(LocalDate date)
  {
    return ts -> ts.start.getDayOfYear() == date.getDayOfYear() && ts.start.getYear() == date.getYear();
  }
  
  public static <T extends Timed> Predicate<T> onDay(LocalDate date)
  {
    return t -> onDayTimestamp(date).test(t.timestamp());
  }
  
  public static <T extends Timed> Predicate<T> isAfter(LocalDateTime timestamp)
  {
    return t -> t.timestamp().start.isAfter(timestamp.atZone(t.timestamp().start.getZone()));
  }
  
  public static <T extends Timed> Predicate<T> isAtLeast(LocalDate timestamp)
  {
    return t -> t.timestamp().start.isAfter(timestamp.atStartOfDay(t.timestamp().start.getZone()));
  }
  
  public static <T extends Timed> Predicate<T> isAfter(LocalDate timestamp)
  {
    return t -> t.timestamp().start.isAfter(timestamp.plusDays(1).atStartOfDay(t.timestamp().start.getZone()));
  }
  
  public static <T extends Timed> Predicate<T> isBefore(LocalDate timestamp)
  {
    return t -> t.timestamp().start.isBefore(timestamp.atStartOfDay(t.timestamp().start.getZone()));
  }
  
  public static <T extends Timed> Predicate<T> isBefore(LocalDateTime timestamp)
  {
    return t -> t.timestamp().start.isBefore(timestamp.atZone(t.timestamp().start.getZone()));
  }
 
  public static <T extends Timed> Predicate<T> inDayRange(LocalDate first, LocalDate last)
  {
    return t -> {
      Timestamp ts = t.timestamp();
      ZoneId zone = ts.start.getZone();
      boolean isAfterStart = ts.start.isAfter(first.atStartOfDay().atZone(zone));
      boolean isBeforeEnd = ts.end.isBefore(last.plusDays(1).atStartOfDay().atZone(zone));
      return isAfterStart && isBeforeEnd;
    };
  }
  
  public static Predicate<Sample> ofType(SampleType type)
  {
    return t -> t.type().equals(type);
  }
  
  public static <T> Predicate<T> and(Predicate<T> p1, Predicate<T> p2) { return p1.and(p2); }
}
