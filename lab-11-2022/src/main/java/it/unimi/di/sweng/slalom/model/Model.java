package it.unimi.di.sweng.slalom.model;

import it.unimi.di.sweng.slalom.Observable;
import it.unimi.di.sweng.slalom.Observer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Model implements Observable<Map<String,List<Double>>> {

  private final Map<String,List<Double>> name_times = new HashMap<>();
  private final List<Observer<Map<String,List<Double>>>> observers = new ArrayList<>();

  public void readFilePrimaManche(@NotNull Scanner s) {
    while (s.hasNextLine()) {
      String linea = s.nextLine();
      String[] el = linea.split(";");
      String name = el[0];
      double time = Double.parseDouble(el[1]);
      System.out.printf("time: [%g] name: [%s]\n", time, name);
      name_times.put(name,new ArrayList<>(List.of(time)));
    }
    notifyObservers();
  }

  public void readRecord(@NotNull String name, @NotNull String time){
    if(name_times.containsKey(name)) {
      name_times.get(name).add(Double.parseDouble(time));
      notifyObservers();
    }
  }

  @Override
  public void notifyObservers() {
    for(int i=0;i<observers.size();i++) observers.get(i).update(this, getState());
  }

  @Override
  public void addObserver(@NotNull Observer<Map<String, List<Double>>> obs) {
    observers.add(obs);
  }

  @Override
  public void removeObserver(@NotNull Observer<Map<String, List<Double>>> obs) {
    observers.remove(obs);
  }

  @Override
  public @NotNull Map<String, List<Double>> getState() {
    HashMap<String, List<Double>> values = new HashMap<>();
    for (Map.Entry<String, List<Double>> stringListEntry : name_times.entrySet())
      values.put(stringListEntry.getKey(),new ArrayList<>(stringListEntry.getValue()));
    return values;
  }
}
