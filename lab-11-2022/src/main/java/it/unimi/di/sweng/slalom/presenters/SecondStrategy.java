package it.unimi.di.sweng.slalom.presenters;

import org.jetbrains.annotations.NotNull;

import java.util.*;

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

        return SortByLastMapElement.sort(toSort);
    }
}
