package it.unimi.di.sweng.slalom.presenters;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SortByLastMapElement {

    public static @NotNull Map<String, List<Double>> sort(Map<String, List<Double>> toSort) {
        Map<String, List<Double>> sortedMap = new TreeMap<>(
                (o1, o2) -> {
                    List<Double> val1 = toSort.get(o1);
                    List<Double> val2 = toSort.get(o2);
                    int comparison = val1.get(val1.size() - 1).compareTo(val2.get(val2.size() - 1));
                    if (comparison == 0)
                        return o2.compareTo(o1);
                    return comparison;
                }
        );
         sortedMap.putAll(toSort);
         return sortedMap;
    }
}