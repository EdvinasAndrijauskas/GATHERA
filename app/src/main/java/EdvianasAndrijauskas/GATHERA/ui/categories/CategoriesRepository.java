package EdvianasAndrijauskas.GATHERA.ui.categories;

import java.util.ArrayList;
import java.util.List;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCardRepository;

public class CategoriesRepository {
    private final ArrayList<CategoriesEventCard> categoriesEventCardList;
    private static CategoriesRepository instance;

    public CategoriesRepository() {
        this.categoriesEventCardList = new ArrayList<>();
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Sport", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "It", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Something", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, ">???", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Sport", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Sport", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Sport", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Sport", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Sport", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Sport", R.drawable.ic_baseline_star_24));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.football, "Sport", R.drawable.ic_baseline_star_24));
    }

    public static synchronized CategoriesRepository getInstance() {
        if (instance == null)
            instance = new CategoriesRepository();
        return instance;
    }

    public ArrayList<CategoriesEventCard> getCategoriesEventCardList() {
        return categoriesEventCardList;
    }
}
