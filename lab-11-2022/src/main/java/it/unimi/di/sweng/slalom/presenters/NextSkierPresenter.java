package it.unimi.di.sweng.slalom.presenters;

import it.unimi.di.sweng.slalom.Main;
import it.unimi.di.sweng.slalom.model.Model;
import it.unimi.di.sweng.slalom.views.NextSkierView;
import it.unimi.di.sweng.slalom.views.RankView;
import org.jetbrains.annotations.NotNull;

public class NextSkierPresenter implements Presenter{
    private final NextSkierView nextSkierVew;
    private final RankView rankView;
    private final Model model;
    private int rowIndex = 1;

    public NextSkierPresenter(@NotNull Model model, @NotNull NextSkierView nextSkierView, @NotNull RankView rankView){
        this.model = model;
        this.nextSkierVew = nextSkierView;
        this.rankView = rankView;
        nextSkierView.addHandlers(this);
        String name_time = rankView.get(Main.SKIER_NUM - rowIndex);
        nextSkierVew.setName(name_time.substring(0,name_time.length()-5).trim());
    }

    @Override
    public void action(@NotNull String name, @NotNull String time) {
        model.readRecord(name,time);
        if(rowIndex == Main.SKIER_NUM)
            nextSkierVew.setName("END OF SLALOM");
        else{
            String name_time = rankView.get(Main.SKIER_NUM - rowIndex - 1);
            nextSkierVew.setName(name_time.substring(0,name_time.length()-5).trim());
            rowIndex++;
        }
    }
}
