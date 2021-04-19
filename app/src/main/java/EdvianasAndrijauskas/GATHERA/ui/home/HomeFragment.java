package EdvianasAndrijauskas.GATHERA.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import EdvianasAndrijauskas.GATHERA.R;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView eventCardList;
    EventCardAdapter eventCardAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //Recycling view
        recyclerView = (RecyclerView) root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true); //every item of the RecyclerView has a fix size
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // the event card stuff
        eventCardList = root.findViewById(R.id.rv);
        eventCardList.hasFixedSize();
        eventCardList.setLayoutManager(new LinearLayoutManager(getContext()));

        //Finding search by Id

        SearchView searchView = root.findViewById(R.id.works);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                homeViewModel.searchEventCard(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        // Floating Action button
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_profile);
            }
        });


        eventCardAdapter = new EventCardAdapter();
        eventCardList.setAdapter(eventCardAdapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getSearchedEvent().observe(getViewLifecycleOwner(), eventCardAdapter::updateList);
        return root;
    }


}