package EdvianasAndrijauskas.GATHERA.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import EdvianasAndrijauskas.GATHERA.R;


public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private RecyclerView eventCardListRecycleView;
    private EventCardAdapter eventCardAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.init();
        checkIfSignedIn();
        // Setting recycling view
        eventCardListRecycleView = root.findViewById(R.id.home_rv);
        eventCardListRecycleView.hasFixedSize();
        eventCardListRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventCardAdapter = new EventCardAdapter(getContext());
        eventCardListRecycleView.setAdapter(eventCardAdapter);
        homeViewModel.getAllEvents().observe(getViewLifecycleOwner(), eventCardAdapter::updateList);

//        //Finding search by Id
//        SearchView searchView = root.findViewById(R.id.works);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                homeViewModel.searchEventCard(newText);
//                return false;
//            }
//        });

        Button addEventButton = root.findViewById(R.id.home_addEventButton);
        addEventButton.setOnClickListener(view -> { Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navgiation_addEvent); });
        return root;
    }

    private void checkIfSignedIn() {
        homeViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null)
                startLoginActivity();
        });
    }

    private void startLoginActivity() {
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_signin);
    }

}