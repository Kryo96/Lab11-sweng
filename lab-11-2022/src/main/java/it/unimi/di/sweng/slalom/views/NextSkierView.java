package it.unimi.di.sweng.slalom.views;


import it.unimi.di.sweng.slalom.presenters.Presenter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

public class NextSkierView extends Region {
  @NotNull
  private final TextField time;
  @NotNull
  private final Label name;

  public NextSkierView() {
    name = new Label("NEXT SKIER");
    time = new TextField();
    name.setFont(Font.font("sans", 20));
    name.setMinWidth(300);
    time.setMaxWidth(100);
    time.setFont(Font.font("sans", 20));
    setBackground(new Background(
        new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5.0), Insets.EMPTY)));
    setBorder(new Border(
        new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(2))));

    name.setPadding(new Insets(10, 10, 10, 10));
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));

    grid.add(name, 0, 0);
    grid.add(time, 1, 0);

    this.getChildren().add(grid);
  }

  public void addHandlers(@NotNull Presenter presenter) {
    time.setOnAction(eh -> presenter.action(name.getText(), time.getText()));   // tasto invio nella casella di testo
  }

  public void setName(@NotNull String nome) {
    name.setText(nome);
    time.setText("");
  }

  public @NotNull String getName() {
    return name.getText();
  }


}
