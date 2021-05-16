package EdvianasAndrijauskas.GATHERA.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCard;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCardAdapter;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView eventCardListRecycleView;
    private EventCardAdapter eventCardAdapter;
    private ArrayList<EventCard> listOfAllCards = new ArrayList<>();
    private ArrayList<EventCard> myList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        Button signOff = root.findViewById(R.id.home_signOff);

        profileViewModel.init();
        eventCardListRecycleView = root.findViewById(R.id.profile_rv);
        eventCardListRecycleView.hasFixedSize();
        eventCardListRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventCardListRecycleView.setAdapter(eventCardAdapter);



        signOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut(view);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_signin);
            }
        });

        TextView username = root.findViewById(R.id.username);
        username.setText(profileViewModel.getUserRepository().getCurrentUser().getValue().getDisplayName());


        return root;
    }
    public void signOut(View v) {
        profileViewModel.signOut();
        //has to be bind somewhere(probably in profile (where do I want it ))
    }

}