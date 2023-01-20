package it.unimi.di.sweng.slalom.presenters;

import it.unimi.di.sweng.slalom.Observer;

import java.util.List;
import java.util.Map;

public interface Presenter extends Observer<Map<String, List<Double>>> {
  void action(String text1, String text2);
}
