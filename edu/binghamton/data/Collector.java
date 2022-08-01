package edu.binghamton.data;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

public final class Collector<T> {
  private final ScheduledExecutorService executor =
      newSingleThreadScheduledExecutor(
          r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
          });
  private final Supplier<T> source;
  private final long periodMillis;

  private ArrayList<T> data = new ArrayList<>();
  private boolean isRunning = false;
  private Future<?> collectionFuture;

  public Collector(Supplier<T> source, long periodMillis) {
    this.source = source;
    this.periodMillis = periodMillis;
  }

  public void start() {
    if (!isRunning) {
      collectionFuture =
          executor.scheduleAtFixedRate(() -> data.add(source.get()), 0, periodMillis, MILLISECONDS);
      isRunning = true;
    }
  }

  public ArrayList<T> stop() {
    ArrayList<T> data = new ArrayList<>();
    if (isRunning) {
      collectionFuture.cancel(true);
      data = this.data;
      this.data = new ArrayList<>();
      isRunning = false;
    }
    return data;
  }
}
