package it.unimi.di.sweng.slalom.presenter;

import it.unimi.di.sweng.slalom.model.Model;
import it.unimi.di.sweng.slalom.presenters.FirstStrategy;
import it.unimi.di.sweng.slalom.presenters.NextSkierPresenter;
import it.unimi.di.sweng.slalom.presenters.Presenter;
import it.unimi.di.sweng.slalom.presenters.Strategy;
import it.unimi.di.sweng.slalom.views.NextSkierView;
import it.unimi.di.sweng.slalom.views.RankView;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class NextSkierPresenterTest {

    @Test
    public void testNextSkierName(){
        RankView firstManche = mock(RankView.class);
        NextSkierView nextSkierView = mock(NextSkierView.class);
        Model model = mock(Model.class);
        when(firstManche.get(14)).thenReturn("BRIGNONE Federica 41.90");
        Presenter SUT = new NextSkierPresenter(model, nextSkierView, firstManche);
        verify(nextSkierView).setName("BRIGNONE Federica");
    }

    @Test
    public void testAction(){
        RankView firstManche = mock(RankView.class);
        NextSkierView nextSkierView = mock(NextSkierView.class);
        Model model = mock(Model.class);
        when(firstManche.get(14)).thenReturn("HECTOR Sara", "45.8");
        when(firstManche.get(13)).thenReturn("BRIGNONE Federica 41.90");
        Presenter SUT = new NextSkierPresenter(model, nextSkierView, firstManche);
        SUT.action("VLHOVA Petra","59.34");
        verify(model).readRecord("VLHOVA Petra","59.34");
        verify(nextSkierView).setName("BRIGNONE Federica");
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
}
