package edu.binghamton.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;

public final class Record<T> {
  private static final String csvHeader = String.join(",", "timestamp", "unit", "value");

  public static final <T extends Object> void writeToCsv(Iterable<Record<T>> records, String path) {
    try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
      writer.println(csvHeader);
      for (Record<T> record : records) {
        writer.println(record.toCsv());
      }
    } catch (Exception e) {
      System.out.println(String.format("couldn't write {}", path));
      e.printStackTrace();
    }
  }

  public final Instant timestamp;
  public final String unit;
  public final T value;

  public Record(Instant timestamp, String unit, T value) {
    this.timestamp = timestamp;
    this.unit = unit;
    this.value = value;
  }

  private final String toCsv() {
    return String.join(",", Long.toString(timestamp.toEpochMilli()), unit, value.toString());
  }
}
