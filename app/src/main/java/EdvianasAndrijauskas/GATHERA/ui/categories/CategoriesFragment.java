package EdvianasAndrijauskas.GATHERA.ui.categories;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import EdvianasAndrijauskas.GATHERA.R;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    private CategoriesViewModel categoriesViewModel;
    private RecyclerView recyclerView;
    private RecyclerView categoriesEventCardList;
    CategoriesEventCardAdapter categoriesEventCardAdapter;
    ArrayList<CategoriesEventCard> categoriesEventCardArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rvCategories);
        recyclerView.setHasFixedSize(true);
     @SuppressLint("WrongConstant") GridLayoutManager manager = new GridLayoutManager(getContext(),2,1,false);

        recyclerView.setLayoutManager(manager);

        categoriesEventCardList = root.findViewById(R.id.rvCategories);
        categoriesEventCardList.hasFixedSize();
        categoriesEventCardList.setLayoutManager(manager);




        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));
        categoriesEventCardArrayList.add(new CategoriesEventCard(R.drawable.football,"Sport",R.drawable.ic_baseline_star_24));


        categoriesEventCardAdapter = new CategoriesEventCardAdapter(categoriesEventCardArrayList);
        categoriesEventCardList.setAdapter(categoriesEventCardAdapter);
        return root;
    }
}