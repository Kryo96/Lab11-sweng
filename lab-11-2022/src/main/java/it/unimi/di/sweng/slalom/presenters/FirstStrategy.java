package it.unimi.di.sweng.slalom.presenters;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public enum FirstStrategy implements Strategy{

    INSTANCE;

    @Override
    public @NotNull Map<String, List<Double>> sort(@NotNull Map<String, List<Double>> toSort){
        Map<String, List<Double>> sortedMap = new TreeMap<>(
                (o1, o2) -> {
                    int comparison = toSort.get(o1).get(0).compareTo(toSort.get(o2).get(0));
                    if (comparison == 0) {
                        return o2.compareTo(o1); //a parimerito conta il "pettorale"
                    }
                    return comparison;
                }
        );
        sortedMap.putAll(toSort);
        return sortedMap;
    }
}
