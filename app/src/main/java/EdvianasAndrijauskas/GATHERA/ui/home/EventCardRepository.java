package EdvianasAndrijauskas.GATHERA.ui.home;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import EdvianasAndrijauskas.GATHERA.R;

public class EventCardRepository {
    private final List<EventCard> eventCardArrayList;
    private final MutableLiveData<List<EventCard>> searchedEventCard = new MutableLiveData<>();
    private static EventCardRepository instance;
    private DatabaseReference myRef;
    private EventCardLiveData eventCard;

    public EventCardRepository() {
        eventCardArrayList = new ArrayList<>();
//        eventCardArrayList.add(new EventCard("Monday", "May", "11:15", "Football", "Cool eveningwith pals", 20, R.drawable.football, 18));
//        eventCardArrayList.add(new EventCard("Tuesday", "September", "15:30", "Basketball", "Cool eveningwith pals", 22, R.drawable.football, 19));
//        eventCardArrayList.add(new EventCard("Wednesday", "October", "18:30", "amazing", "Cool Ou he pals", 19, R.drawable.football, 15));
//        eventCardArrayList.add(new EventCard("Satuday", "September", "9:30", "Wtf is thi", "Cool asdsadas pals", 15, R.drawable.football, 11));
//        eventCardArrayList.add(new EventCard("Sunday", "May", "15:00", "Let's go warriors", "Cool", 2, R.drawable.football, 10));
//        eventCardArrayList.add(new EventCard("Monday", "May", "11:15", "Football", "Cool eveningwith pals", 20, R.drawable.football, 29));
//        eventCardArrayList.add(new EventCard("Tuesday", "September", "15:30", "Basketball", "Cool eveningwith pals", 22, R.drawable.football, 11));
//        eventCardArrayList.add(new EventCard("Wednesday", "October", "18:30", "amazing", "Cool Ou he pals", 19, R.drawable.football, 13));
//        eventCardArrayList.add(new EventCard("Satuday", "September", "9:30", "Wtf is thi", "Cool asdsadas pals", 15, R.drawable.football, 11));
//        eventCardArrayList.add(new EventCard("Sunday", "May", "15:00", "Let's go warriors", "Cool", 2, R.drawable.football, 2));
//        eventCardArrayList.add(new EventCard("Monday", "May", "11:15", "Football", "Cool eveningwith pals", 20, R.drawable.football, 4));
//        eventCardArrayList.add(new EventCard("Tuesday", "September", "15:30", "Basketball", "Cool eveningwith pals", 22, R.drawable.football, 9));
//        eventCardArrayList.add(new EventCard("Wednesday", "October", "18:30", "amazing", "Cool Ou he pals", 19, R.drawable.football, 3));
//        eventCardArrayList.add(new EventCard("Satuday", "September", "9:30", "Wtf is thi", "Cool asdsadas pals", 15, R.drawable.football, 18));
//        eventCardArrayList.add(new EventCard("Sunday", "May", "15:00", "wtf's go warriors", "Cool", 2, R.drawable.football, 17));
//        eventCardArrayList.add(new EventCard("Sunday", "May", "15:00", "wtf's go warriors", "Cool", 2, R.drawable.football, 17));
//        eventCardArrayList.add(new EventCard("Sunday", "May", "15:00", "wtf's go warriors", "Cool", 2, R.drawable.football, 17));
//        searchedEventCard.setValue(eventCardArrayList);
    }

    public static synchronized EventCardRepository getInstance() {
        if (instance == null)
            instance = new EventCardRepository();
        return instance;
    }

    public void init(String userId) {
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        eventCard = new EventCardLiveData(myRef);
    }

    public void saveEventCard(String userId,String date, String time, String eventName, String description, int howManyPeopleAreComing, String image, String category) {
        myRef.setValue(new EventCard(userId,date, category, time, eventName, description, howManyPeopleAreComing, image));
    }

    public EventCardLiveData getEventCard() {
        return eventCard;
    }

    public void searchEventCard(String query) {
        List<EventCard> result = new ArrayList<>();
        for (EventCard p : eventCardArrayList) {
            if (p.getEventName().toLowerCase().contains(query.toLowerCase())) {
                result.add(p);
            }
        }
        searchedEventCard.setValue(result);
    }

    public LiveData<List<EventCard>> getSearchedEvent() {
        return searchedEventCard;
    }
}
