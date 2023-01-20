package it.unimi.di.sweng.slalom.presenter;

import it.unimi.di.sweng.slalom.model.Model;
import it.unimi.di.sweng.slalom.presenters.*;
import it.unimi.di.sweng.slalom.views.RankView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.mockito.Mockito.*;

public class SkierPresenterTest {

    @Test
    void updateTest(){
        RankView rankView =mock(RankView.class);
        Model model = mock(Model.class);
        Strategy strategy = mock(Strategy.class);
        SkierPresenter SUT = new SkierPresenter(model, rankView, strategy);

        Map<String, List<Double>> map = new HashMap<>();
        map.put("HECTOR Sara", new ArrayList<>(List.of(45.8)));
        map.put("VLHOVA Petra", new ArrayList<>(List.of(56.3)));
        map.put("BRIGNONE Federica", new ArrayList<>(List.of(41.9)));

        Map<String, List<Double>> map1 = new LinkedHashMap<>();
        map1.put("BRIGNONE Federica", new ArrayList<>(List.of(41.9)));
        map1.put("HECTOR Sara", new ArrayList<>(List.of(45.8)));
        map1.put("VLHOVA Petra", new ArrayList<>(List.of(56.3)));

        when(strategy.sort(map)).thenReturn(map1);
        SUT.update(model, map);
        verify(strategy).sort(map);
        verify(rankView).set(0,String.format("%-30s%-4s","BRIGNONE Federica",String.format(Locale.US,"%.2f",41.90)));
        verify(rankView).set(1,String.format("%-30s%-4s","HECTOR Sara",String.format(Locale.US,"%.2f",45.80)));
        verify(rankView).set(2,String.format("%-30s%-4s","VLHOVA Petra",String.format(Locale.US,"%.2f",56.30)));
    }

    @ParameterizedTest
    @CsvSource ({"BRIGNONE Federica, 59.40,6.60,1:06.00","GASIENICA-DANIEL Maryna, 59.24, 0.03, 59.27"})
    void sommmaTest(String name, Double first, Double second, String totalMin) {
        RankView rankView = mock(RankView.class);
        Model model = mock(Model.class);
        Strategy strategy = mock(Strategy.class);
        SkierPresenter SUT = new SkierPresenter(model, rankView, strategy);

        Map<String, List<Double>> map = new HashMap<>();
        map.put(name, new ArrayList<>(List.of(first, second,first+second)));
        when(strategy.sort(map)).thenReturn(map);
        SUT.update(model, map);
        verify(rankView).set(0, String.format("%-30s%-10s%-10s%-4s", name, String.format(Locale.US,"%.2f",first), String.format(Locale.US,"%.2f",second), totalMin));
    }
}
