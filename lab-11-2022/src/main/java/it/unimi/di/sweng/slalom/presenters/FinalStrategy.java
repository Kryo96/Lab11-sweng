package it.unimi.di.sweng.slalom.presenters;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public enum FinalStrategy implements Strategy{

    INSTANCE;
    @Override
    public @NotNull Map<String, List<Double>> sort(@NotNull Map<String, List<Double>> toSort) {
        List<String> daRimuovere = new ArrayList<>();
        for (String s : toSort.keySet())
            if(toSort.get(s).size() != 2)
                daRimuovere.add(s);
            else
                toSort.get(s).add(toSort.get(s).get(0) + toSort.get(s).get(1));
        for (String s:daRimuovere)
            toSort.remove(s);


        return SortByLastMapElement.sort(toSort);
    }
}
