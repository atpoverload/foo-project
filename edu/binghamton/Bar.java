package edu.binghamton;

import edu.binghamton.util.NativeUtils;

public final class Bar {
  public static native long call();

  private Bar() {}

  public static void main(String[] args) {
    NativeUtils.loadFromUserLibs("Bar");
    System.out.println(String.format("bar returned %d", call()));
  }
}
