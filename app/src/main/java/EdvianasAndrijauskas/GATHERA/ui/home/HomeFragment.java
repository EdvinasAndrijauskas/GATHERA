package EdvianasAndrijauskas.GATHERA.ui.home;

import android.content.Intent;
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
import com.google.gson.Gson;

import java.util.ArrayList;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.SelectedEventActivity;


public class HomeFragment extends Fragment implements EventCardAdapter.OnListItemClickListener {
    private HomeViewModel homeViewModel;
    private RecyclerView eventCardListRecycleView;
    private EventCardAdapter eventCardAdapter;
    private Gson gson;
    private ArrayList<EventCard> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.init();
        checkIfSignedIn();
        // Setting recycling view
        eventCardListRecycleView = root.findViewById(R.id.home_rv);
        eventCardListRecycleView.hasFixedSize();
        eventCardListRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.gson = new Gson();
        eventCardAdapter = new EventCardAdapter(getContext(), this);
        eventCardListRecycleView.setAdapter(eventCardAdapter);

        homeViewModel.getAllEvents().observe(getViewLifecycleOwner(),  eventCardAdapter::updateList);
        homeViewModel.getSearchedEvent().observe(getViewLifecycleOwner(), eventCardAdapter::updateList);
        //Finding search by Id
        SearchView searchView = root.findViewById(R.id.works);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeViewModel.searchEventCard(newText);
                return false;
            }
        });

        Button addEventButton = root.findViewById(R.id.home_addEventButton);
        addEventButton.setOnClickListener(view -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navgiation_addEvent);
        });
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

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(getContext(), SelectedEventActivity.class);
        String toNewView = gson.toJson(homeViewModel.getAllEvents().getValue().get(clickedItemIndex));
        intent.putExtra("EventCard", toNewView);
        startActivityForResult(intent, 1);
    }

}