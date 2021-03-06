package EdvianasAndrijauskas.GATHERA.ui.Fragments;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.SwipeItemTouch;
import EdvianasAndrijauskas.GATHERA.ui.ViewModels.ProfileViewModel;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCard;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCardAdapter;

public class ProfileFragment extends Fragment implements EventCardAdapter.OnListItemClickListener {

    private ProfileViewModel profileViewModel;
    private RecyclerView eventCardListRecycleView;
    private EventCardAdapter eventCardAdapter;
    private Application app;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        Button signOff = root.findViewById(R.id.home_signOff);
        app = new Application();

        profileViewModel.init();
        eventCardListRecycleView = root.findViewById(R.id.profile_rv);
        eventCardListRecycleView.hasFixedSize();
        eventCardListRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventCardAdapter = new EventCardAdapter(getContext(),this);
        eventCardListRecycleView.setAdapter(eventCardAdapter);

        profileViewModel.getAllEvents().observe(getViewLifecycleOwner(), allEvents -> {
            String ownUID = profileViewModel.getUserRepository().getCurrentUser().getValue().getUid();
            ArrayList<EventCard> ownEventCards = (ArrayList<EventCard>) allEvents.stream()
                    .filter(p -> p.getUserId().equals(ownUID)).collect(Collectors.toList());
            eventCardAdapter.updateList(ownEventCards);
        });


        signOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut(view);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_signin);
            }
        });

        TextView username = root.findViewById(R.id.username);
        username.setText(profileViewModel.getUserRepository().getCurrentUser().getValue().getDisplayName());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItemTouch(eventCardAdapter));
        itemTouchHelper.attachToRecyclerView(eventCardListRecycleView);


        return root;
    }

    public void signOut(View v) {
        profileViewModel.signOut();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}