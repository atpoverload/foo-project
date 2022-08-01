package edu.binghamton.util;

public final class NativeUtils {
  private static String getLibrary(String library) {
    return String.format("lib%s.so", library);
  }

  public static void loadFromJavaLibs(String library) {
    System.loadLibrary(library);
  }

  public static void loadFromUserLibs(String library) {
    System.load(String.join("/", System.getProperty("user.dir"), "lib", getLibrary(library)));
  }

  public static void loadFromPath(String path, String library) {
    System.load(String.join("/", path, getLibrary(library)));
  }

  private NativeUtils() {}
}
