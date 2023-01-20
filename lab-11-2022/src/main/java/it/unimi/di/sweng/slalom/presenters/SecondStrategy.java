package it.unimi.di.sweng.slalom.presenters;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public enum SecondStrategy implements Strategy{

    INSTANCE;

    @Override
    public @NotNull Map<String, List<Double>> sort(@NotNull Map<String, List<Double>> toSort){
        List<String> daRimuovere = new ArrayList<>();
        for (String s : toSort.keySet())
            if(toSort.get(s).size() != 2)
                daRimuovere.add(s);
        for (String s:daRimuovere)
            toSort.remove(s);

        Map<String, List<Double>> sortedMap = new TreeMap<>(
                (o1, o2) -> {
                    int comparison = toSort.get(o1).get(1).compareTo(toSort.get(o2).get(1));
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
