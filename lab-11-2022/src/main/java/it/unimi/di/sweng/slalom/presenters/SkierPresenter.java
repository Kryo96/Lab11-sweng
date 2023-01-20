package it.unimi.di.sweng.slalom.presenters;

import it.unimi.di.sweng.slalom.Main;
import it.unimi.di.sweng.slalom.Observable;
import it.unimi.di.sweng.slalom.Observer;
import it.unimi.di.sweng.slalom.model.Model;
import it.unimi.di.sweng.slalom.views.RankView;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SkierPresenter implements Observer<Map<String,List<Double>>> {

    private final Model model;
    private final RankView rankView;
    private final Strategy strategy;
    private Map<String, List<Double>> sortedRecords = new HashMap<>();

    public SkierPresenter(@NotNull Model model, RankView rankView, Strategy strategy){
        this.model = model;
        this.rankView = rankView;
        this.strategy = strategy;
        model.addObserver(this);
    }

    @Override
    public void update(@NotNull Observable<Map<String, List<Double>>> subject, @NotNull Map<String, List<Double>> state) {
        sortedRecords = strategy.sort(state);
        setRankView();
        if(sortedRecords.size() == Main.SKIER_NUM && sortedRecords.values().stream().toList().get(0).size() == 1)
            model.removeObserver(this);
    }

    private void setRankView(){
        int rowIndex = 0;
        for (String s : sortedRecords.keySet()){
            List<Double> val = sortedRecords.get(s);
            String toSet;
            if(val.size()<=2)
                toSet = String.format("%-30s%-4s", s, String.format(Locale.US, "%.2f", val.get(val.size() - 1)));
            else {
                toSet = String.format("%-30s%-10s%-10s%-4s", s, format(val.get(0)), format(val.get(1)),somma(val.get(0), val.get(1)));
                if(rowIndex >= Main.SKIER_NUM_TOTAL_RANK)
                    break;
            }
            rankView.set(rowIndex++, toSet);
        }
    }

    private @NotNull String format(@NotNull Double val){
       return String.format(Locale.US, "%.2f", val);
    }

    private @NotNull String somma(@NotNull Double firstMancheTime, @NotNull Double secondMancheTime) {
        double totalTime = firstMancheTime + secondMancheTime;
        int min = (int) (totalTime/60);
        double sec = totalTime%60;
        DecimalFormat df = new DecimalFormat("00.00");
        String time = df.format(sec);
        if(min > 0) time = min + ":" + time;
        return time;
    }
}
