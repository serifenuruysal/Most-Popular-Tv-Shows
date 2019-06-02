package com.soulkitchen.themovieapp;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLDecoder;

public class ReadFileUtils {

  private static final String UTF_8 = "UTF-8";

  @SuppressWarnings("TypeParameterUnusedInFormals")
  public static <T> T readResponse(
      ClassLoader testClassLoader, Type responseType, String fileName, Gson gson
  ) throws Exception {
    File file = new File(URLDecoder.decode(testClassLoader.getResource(fileName).getPath(), UTF_8));
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8));
    return gson.fromJson(reader, responseType);
  }

  public static String readAsString(ClassLoader testClassLoader, String fileName)
      throws FileNotFoundException, UnsupportedEncodingException {
    File file = new File(URLDecoder.decode(testClassLoader.getResource(fileName).getPath(), UTF_8));
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8));
    StringBuilder stringBuilder = new StringBuilder();
    reader.lines().forEach(stringBuilder::append);
    return stringBuilder.toString();
  }

  private ReadFileUtils() {

  }

}
