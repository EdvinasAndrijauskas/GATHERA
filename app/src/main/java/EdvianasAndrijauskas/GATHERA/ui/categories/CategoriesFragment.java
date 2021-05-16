package EdvianasAndrijauskas.GATHERA.ui.categories;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import EdvianasAndrijauskas.GATHERA.R;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    private CategoriesViewModel categoriesViewModel;
    private RecyclerView categoriesEventCardList;
    private CategoriesEventCardAdapter categoriesEventCardAdapter;
    private CategoriesRepository repository;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        @SuppressLint("WrongConstant") GridLayoutManager manager = new GridLayoutManager(getContext(),2,1,false);
        repository = new CategoriesRepository();
        categoriesEventCardList = root.findViewById(R.id.rvCategories);
        categoriesEventCardList.hasFixedSize();
        categoriesEventCardList.setLayoutManager(manager);
        categoriesEventCardAdapter = new CategoriesEventCardAdapter(repository.getCategoriesEventCardList());
        categoriesEventCardList.setAdapter(categoriesEventCardAdapter);


        return root;
    }

}