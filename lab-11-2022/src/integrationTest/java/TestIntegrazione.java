import static it.unimi.di.sweng.slalom.Main.SKIER_NUM;
import static it.unimi.di.sweng.slalom.Main.SKIER_NUM_TOTAL_RANK;
import static org.assertj.core.api.Assertions.assertThat;

import it.unimi.di.sweng.slalom.Main;
import it.unimi.di.sweng.slalom.model.Model;
import it.unimi.di.sweng.slalom.presenters.*;
import it.unimi.di.sweng.slalom.views.NextSkierView;
import it.unimi.di.sweng.slalom.views.RankView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.InputStream;
import java.util.Scanner;

public class TestIntegrazione extends ApplicationTest {
  private RankView firstRun;
  private RankView secondRun;
  private RankView totalRun;
  private NextSkierView nextSkier;


  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("2022 - Women's Giant Slalom");

    nextSkier = new NextSkierView();

    firstRun = new RankView("First manche", SKIER_NUM);
    secondRun = new RankView("Second manche", SKIER_NUM);
    totalRun = new RankView("Final ranking (first " + SKIER_NUM_TOTAL_RANK + " skiers)", SKIER_NUM_TOTAL_RANK);


    GridPane gridPane = new GridPane();
    gridPane.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    gridPane.add(nextSkier, 0, 0);
    GridPane.setColumnSpan(nextSkier, GridPane.REMAINING);
    gridPane.add(firstRun, 0, 1);
    gridPane.add(secondRun, 1, 1);

    gridPane.add(totalRun, 0, 2);
    GridPane.setColumnSpan(totalRun, GridPane.REMAINING);


    Model model = new Model();
    InputStream is = Main.class.getResourceAsStream("/first");
    assert is != null;
    Scanner s = new Scanner(is);
    Presenter firstPresenter = new SkierPresenter(model, nextSkier, firstRun, FirstStrategy.INSTANCE);
    model.readFilePrimaManche(s);

    Presenter secondPresenter = new SkierPresenter(model,nextSkier,secondRun, SecondStrategy.INSTANCE);
    Presenter finalPresenter = new SkierPresenter(model,nextSkier,totalRun,ThirdStrategy.INSTANCE);

    Scene scene = new Scene(gridPane);
    primaryStage.setScene(scene);
    primaryStage.show();

    //scommentare quando definito model
    //model.notifyObservers();
  }

  @Test
  public void testSomething() {
    assertThat(firstRun.get(2)).startsWith("BRIGNONE Federica").endsWith("57.98");
    assertThat(nextSkier.getName()).startsWith("STJERNESUND Thea Louise");

    doubleClickOn(nextSkier);
    write("57.50");
    press(KeyCode.ENTER);
    release(KeyCode.ENTER);
    assertThat(secondRun.get(0)).startsWith("STJERNESUND").endsWith("57.50");
    assertThat(totalRun.get(0)).startsWith("STJERNESUND").endsWith("1:56.89");

    doubleClickOn(nextSkier);
    write("58.90");
    press(KeyCode.ENTER);
    release(KeyCode.ENTER);


    doubleClickOn(nextSkier);
    write("58.81");
    press(KeyCode.ENTER);
    release(KeyCode.ENTER);

    assertThat(secondRun.get(0)).startsWith("STJERNESUND").endsWith("57.50");
    assertThat(secondRun.get(1)).startsWith("VLHOVA");
    assertThat(secondRun.get(2)).startsWith("LIENSBERGER");

    assertThat(totalRun.get(0)).startsWith("STJERNESUND");
    assertThat(totalRun.get(1)).startsWith("VLHOVA").endsWith("1:58.15");
    assertThat(totalRun.get(2)).startsWith("LIENSBERGER").endsWith("1:58.24");

  }
}
