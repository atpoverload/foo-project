package edu.binghamton;

import edu.binghamton.util.NativeUtils;

public final class Foo {
  public static native long call();

  private Foo() {}

  public static void main(String[] args) {
    NativeUtils.loadFromUserLibs("Foo");
    System.out.println(String.format("foo returned %d", call()));
  }
}
