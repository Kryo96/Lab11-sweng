package it.unimi.di.sweng.slalom.presenters;

import it.unimi.di.sweng.slalom.Main;
import it.unimi.di.sweng.slalom.Observable;
import it.unimi.di.sweng.slalom.model.Model;
import it.unimi.di.sweng.slalom.views.NextSkierView;
import it.unimi.di.sweng.slalom.views.RankView;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SkierPresenter implements Presenter {

    private final Model model;
    private final NextSkierView nextSkierView;
    private final RankView rankView;
    private final Strategy strategy;
    private Map<String, List<Double>> sortedRecords = new HashMap<>();
    private int rowIndex;
    public SkierPresenter(@NotNull Model model, @NotNull NextSkierView nextSkierView, @NotNull RankView rankView, @NotNull Strategy strategy) {
        this.model = model;
        this.nextSkierView = nextSkierView;
        this.rankView = rankView;
        this.strategy = strategy;
        model.addObserver(this);
        if(strategy instanceof FirstStrategy)
            this.nextSkierView.addHandlers(this);
    }

    @Override
    public void update(@NotNull Observable<Map<String, List<Double>>> subject, @NotNull Map<String, List<Double>> state) {
        sortedRecords = strategy.sort(state);
        rowIndex = 0;

        String lastSkier = (String) sortedRecords.keySet().toArray()[sortedRecords.size()-1];
        setRankView();
        next(lastSkier);
    }
    @Override
    public void action(@NotNull String text1, @NotNull String text2) {
        model.readRecord(text1,text2);
        if(sortedRecords.size()==0) {
            nextSkierView.setName("END OF SLALOM");
        } else {
            String lastSkier = (String) sortedRecords.keySet().toArray()[sortedRecords.size()-1];
            nextSkierView.setName(lastSkier);
            sortedRecords.remove(lastSkier);
        }
    }
    private void next(String lastSkier){
        if(sortedRecords.get(lastSkier).size()==1){
            nextSkierView.setName(lastSkier);
            sortedRecords.remove(lastSkier);
            if (rowIndex >= Main.SKIER_NUM) model.removeObserver(this);
        }
    }
    private void setRankView(){
        for (String s : sortedRecords.keySet()){
            List<Double> val = sortedRecords.get(s);
            String toSet;
            if(val.size()<=2)
                toSet = String.format("%-30s%-4s", s, String.format(Locale.US, "%.2f", val.get(val.size() - 1)));
            else {
                toSet = s + " " + String.format(Locale.US, "%.2f", val.get(0)) + " " +
                        String.format(Locale.US, "%.2f", val.get(1)) + " " + somma(val.get(0), val.get(1));
                if(rowIndex>=5)
                    break;
            }
            rankView.set(rowIndex++, toSet);
        }
    }
    private @NotNull String somma(Double firstMancheTime, Double secondMancheTime) {
        StringBuilder s = new StringBuilder();
        if(firstMancheTime+secondMancheTime > 60 ){
            Double value = firstMancheTime+secondMancheTime - 60.00 ;
            s.append("1" + ":" );
            s.append(String.format(Locale.US,"%.2f", value));
        }
        else {
            s.append(firstMancheTime+secondMancheTime);
        }
        return  s.toString();
    }
}
