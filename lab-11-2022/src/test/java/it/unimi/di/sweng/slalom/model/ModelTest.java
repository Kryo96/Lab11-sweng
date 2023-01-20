package it.unimi.di.sweng.slalom.model;

import it.unimi.di.sweng.slalom.Main;
import it.unimi.di.sweng.slalom.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ModelTest {

    Model SUT;
    Scanner s;

    @BeforeEach
    void setUp(){
        SUT = new Model();
        InputStream is = Main.class.getResourceAsStream("/first");
        assert is != null;
        s = new Scanner(is);
    }
    @Test
    void readFilePrimaManche() {
        SUT.readFilePrimaManche(s);
        assertThat(SUT.getState().keySet().size()).isEqualTo(Main.SKIER_NUM);
        assertThat(SUT.getState().get("BRIGNONE Federica")).isEqualTo(List.of(57.98));
    }

    @Test
    void notifyObservers(){
        Observer<Map<String,List<Double>>> obs = mock(Observer.class);
        SUT.addObserver(obs);
        SUT.readFilePrimaManche(s);
        verify(obs).update(SUT, SUT.getState());
    }
    @Test
    void readRecordTest(){
        Observer<Map<String,List<Double>>> obs = mock(Observer.class);
        SUT.addObserver(obs);
        SUT.readFilePrimaManche(s);
        SUT.readRecord("BRIGNONE Federica","12.5");
        assertThat(SUT.getState().get("BRIGNONE Federica")).contains(12.5);
        verify(obs).update(SUT,SUT.getState());
    }

}