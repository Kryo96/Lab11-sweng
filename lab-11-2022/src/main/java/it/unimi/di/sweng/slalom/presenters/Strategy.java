package it.unimi.di.sweng.slalom.presenters;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface Strategy {

    @NotNull
    Map<String, List<Double>> sort(@NotNull Map<String, List<Double>> toSort);
}
