package EdvianasAndrijauskas.GATHERA.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.addPage.Upload;


public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private RecyclerView eventCardList;
    private EventCardAdapter eventCardAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploadList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.init();
        checkIfSignedIn();
//        uploadList = new ArrayList<>();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot postSnapshot:snapshot.getChildren())
//                {
//                    Upload upload = postSnapshot.getValue(Upload.class);
//                    uploadList.add(upload);
//                }
//                eventCardAdapter = new EventCardAdapter(uploadList,getContext());
//                eventCardList.setAdapter(eventCardAdapter);
//                homeViewModel.getSearchedEvent().observe(getViewLifecycleOwner(), eventCardAdapter::updateList);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
        // the event card stuff
        eventCardList = root.findViewById(R.id.home_rv);
        eventCardList.hasFixedSize();
        eventCardList.setLayoutManager(new LinearLayoutManager(getContext()));
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
        addEventButton.setOnClickListener(view -> Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navgiation_addEvent));


        homeViewModel.getEvent().observe(getViewLifecycleOwner(), eventCard -> {
            if(eventCard != null)
            {
                homeViewModel.saveEventCard(eventCard.getUserId(),eventCard.getDate(),eventCard.getTime(),eventCard.getEventName(),eventCard.getDescription(),eventCard.getHowManyPeopleAreComing(),eventCard.getImage(),eventCard.getCategory());
            }
        } );




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

//    public void saveData(View v) {
//        homeViewModel.(messageEditText.getText().toString());
//    }


}