package EdvianasAndrijauskas.GATHERA.ui.EventCard;

import java.util.ArrayList;

import EdvianasAndrijauskas.GATHERA.R;

public class CategoriesRepository {
    private final ArrayList<CategoriesEventCard> categoriesEventCardList;
    private static CategoriesRepository instance;

    public CategoriesRepository() {
        this.categoriesEventCardList = new ArrayList<>();
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.sports, "Sport", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.it, "IT", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.hobbies, "Hobbies", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.classes, "Classes", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.show, "Shows", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.seminar, "Seminars", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.science, "Science", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.gaming, "Gaming", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.challenge, "Challenges", R.drawable.unlickedcategory));
        categoriesEventCardList.add(new CategoriesEventCard(R.drawable.cooking, "Cooking", R.drawable.unlickedcategory));
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
