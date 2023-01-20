package it.unimi.di.sweng.slalom.presenter;

import it.unimi.di.sweng.slalom.model.Model;
import it.unimi.di.sweng.slalom.presenters.FirstStrategy;
import it.unimi.di.sweng.slalom.presenters.Presenter;
import it.unimi.di.sweng.slalom.presenters.SkierPresenter;
import it.unimi.di.sweng.slalom.presenters.Strategy;
import it.unimi.di.sweng.slalom.views.NextSkierView;
import it.unimi.di.sweng.slalom.views.RankView;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FirstManchePresenterTest {

    @Test
    public void testUpdate(){
        RankView firstManche = mock(RankView.class);
        NextSkierView nextSkierView = mock(NextSkierView.class);
        Model model = mock(Model.class);

        Strategy strategy = mock(Strategy.class);
        Map<String, List<Double>> map = new LinkedHashMap<>();
        map.put("BRIGNONE Federica", new ArrayList<>(List.of(41.9)));
        map.put("HECTOR Sara", new ArrayList<>(List.of(45.8)));
        map.put("VLHOVA Petra", new ArrayList<>(List.of(56.3)));

        when(strategy.sort(anyMap())).thenReturn(map);
        Presenter SUT = new SkierPresenter(model, nextSkierView, firstManche, strategy);

        Map<String, List<Double>> map1 = new HashMap<>();
        map1.put("HECTOR Sara", new ArrayList<>(List.of(45.8)));
        map1.put("VLHOVA Petra", new ArrayList<>(List.of(56.3)));
        map1.put("BRIGNONE Federica", new ArrayList<>(List.of(41.9)));

        SUT.update(model, map1);
        verify(firstManche).set(0,String.format("%-30s%-4s","BRIGNONE Federica","41.90"));
    }

    @Test
    void firstStrategyTest(){
        Map<String, List<Double>> map = new HashMap<>();
        map.put("HECTOR Sara", new ArrayList<>(List.of(45.8)));
        map.put("VLHOVA Petra", new ArrayList<>(List.of(56.3)));
        map.put("BRIGNONE Federica", new ArrayList<>(List.of(41.9)));
        Strategy strategy = FirstStrategy.INSTANCE;
        Map<String, List<Double>> map1 = new LinkedHashMap<>();
        map1.put("BRIGNONE Federica", new ArrayList<>(List.of(41.9)));
        map1.put("HECTOR Sara", new ArrayList<>(List.of(45.8)));
        map1.put("VLHOVA Petra", new ArrayList<>(List.of(56.3)));
        assertThat(strategy.sort(map).entrySet()).containsExactlyElementsOf(map1.entrySet());
    }

    @Test
    void setNameTest(){
        Strategy strategy = mock(Strategy.class);
        NextSkierView nextSkierView = mock(NextSkierView.class);
        RankView rankView = mock(RankView.class);
        Model model = mock(Model.class);

        Map<String, List<Double>> map = new HashMap<>();
        map.put("BRIGNONE Federica", new ArrayList<>(List.of(41.9)));
        map.put("HECTOR Sara", new ArrayList<>(List.of(45.8)));
        map.put("VLHOVA Petra", new ArrayList<>(List.of(56.3)));

        when(strategy.sort(anyMap())).thenReturn(map);

        Presenter SUT = new SkierPresenter(model, nextSkierView, rankView, strategy);
        SUT.update(model,map);
        verify(nextSkierView).setName("VLHOVA Petra");
    }

    @Test
    void setNextNameTest(){
        Strategy strategy = mock(Strategy.class);
        NextSkierView nextSkierView = mock(NextSkierView.class);
        RankView rankView = mock(RankView.class);
        Model model = mock(Model.class);

        Map<String, List<Double>> map = new TreeMap<>();
        map.put("BRIGNONE Federica", new ArrayList<>(List.of(41.9)));
        map.put("HECTOR Sara", new ArrayList<>(List.of(45.8)));
        map.put("VLHOVA Petra", new ArrayList<>(List.of(56.3)));

        when(strategy.sort(anyMap())).thenReturn(map);

        Presenter SUT = new SkierPresenter(model, nextSkierView, rankView, strategy);
        SUT.update(model,map);
        SUT.action("BRIGNONE Federica","45.0");
        verify(nextSkierView).setName("HECTOR Sara");
    }
}
