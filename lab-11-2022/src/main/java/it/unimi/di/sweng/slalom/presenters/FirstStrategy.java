package it.unimi.di.sweng.slalom.presenters;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public enum FirstStrategy implements Strategy{

    INSTANCE;
    @Override
    public @NotNull Map<String, List<Double>> sort(@NotNull Map<String, List<Double>> toSort){
        return SortByLastMapElement.sort(toSort);
    }
}