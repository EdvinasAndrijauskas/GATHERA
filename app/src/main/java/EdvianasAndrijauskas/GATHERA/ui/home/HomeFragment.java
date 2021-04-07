package EdvianasAndrijauskas.GATHERA.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import EdvianasAndrijauskas.GATHERA.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView eventCardList;
    EventCardAdapter eventCardAdapter;
    ArrayList<EventCard> eventCardArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true); //every item of the RecyclerView has a fix size
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // the event card stuff
        eventCardList = root.findViewById(R.id.rv);
        eventCardList.hasFixedSize();
        eventCardList.setLayoutManager(new LinearLayoutManager(getContext()));



        eventCardArrayList.add(new EventCard("Monday", "May", "11:15", "Football", "Cool eveningwith pals", 20, R.drawable.football, 18));
        eventCardArrayList.add(new EventCard("Tuesday", "September", "15:30", "Basketball", "Cool eveningwith pals", 22, R.drawable.football, 19));
        eventCardArrayList.add(new EventCard("Wednesday", "October", "18:30", "amazing", "Cool Ou he pals", 19, R.drawable.football, 15));
        eventCardArrayList.add(new EventCard("Satuday", "September", "9:30", "Wtf is thi", "Cool asdsadas pals", 15, R.drawable.football, 11));
        eventCardArrayList.add(new EventCard("Sunday", "May", "15:00", "Let's go warriors", "Cool", 2, R.drawable.football, 10));
        eventCardArrayList.add(new EventCard("Monday", "May", "11:15", "Football", "Cool eveningwith pals", 20, R.drawable.football, 29));
        eventCardArrayList.add(new EventCard("Tuesday", "September", "15:30", "Basketball", "Cool eveningwith pals", 22, R.drawable.football, 11));
        eventCardArrayList.add(new EventCard("Wednesday", "October", "18:30", "amazing", "Cool Ou he pals", 19, R.drawable.football, 13));
        eventCardArrayList.add(new EventCard("Satuday", "September", "9:30", "Wtf is thi", "Cool asdsadas pals", 15, R.drawable.football, 11));
        eventCardArrayList.add(new EventCard("Sunday", "May", "15:00", "Let's go warriors", "Cool", 2, R.drawable.football, 2));
        eventCardArrayList.add(new EventCard("Monday", "May", "11:15", "Football", "Cool eveningwith pals", 20, R.drawable.football, 4));
        eventCardArrayList.add(new EventCard("Tuesday", "September", "15:30", "Basketball", "Cool eveningwith pals", 22, R.drawable.football, 9));
        eventCardArrayList.add(new EventCard("Wednesday", "October", "18:30", "amazing", "Cool Ou he pals", 19, R.drawable.football, 3));
        eventCardArrayList.add(new EventCard("Satuday", "September", "9:30", "Wtf is thi", "Cool asdsadas pals", 15, R.drawable.football, 18));
        eventCardArrayList.add(new EventCard("Sunday", "May", "15:00", "Let's go warriors", "Cool", 2, R.drawable.football, 17));



        eventCardAdapter = new EventCardAdapter(eventCardArrayList);
        eventCardList.setAdapter(eventCardAdapter);
        return root;
    }
}