package edu.binghamton;

import edu.binghamton.data.Collector;
import edu.binghamton.data.Record;
import edu.binghamton.util.NativeUtils;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public final class FooExperiment {
  private static Record<Long> fooRecord() {
    return new Record<Long>(Instant.now(), "foo", Foo.call());
  }

  public static void main(String[] args) throws Exception {
    NativeUtils.loadFromUserLibs("Foo");

    Collector<Record<Long>> collector =
        new Collector<>(FooExperiment::fooRecord, 10 /* periodMillis */);
    collector.start();
    Thread.sleep(1000);
    ArrayList<Record<Long>> data = collector.stop();
    System.out.println(
        String.format(
            "%d records collected over %s",
            data.size(),
            Duration.between(data.get(0).timestamp, data.get(data.size() - 1).timestamp)));
    Record.writeToCsv(data, "data.csv");
  }
}
