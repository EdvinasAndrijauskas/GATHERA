package EdvianasAndrijauskas.GATHERA.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class EventCardRepository {
//    private final List<EventCard> eventCardArrayList = new ArrayList<>();
//    private final MutableLiveData<List<EventCard>> searchedEventCard = new MutableLiveData<>();
    private static EventCardRepository instance;
    private DatabaseReference myRef;
    private EventCardLiveData eventCard;

    public EventCardRepository() {
    }


    public void init(String userId) {
//        myRef.child(userId);
        myRef =  FirebaseDatabase.getInstance("https://gathera-2cd58-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Events");
        eventCard = new EventCardLiveData(myRef);
    }

    public void saveEvent(String stringLocation,String currentUser,String currentDateString,String selectedCategory,String timeString,String nameOfTheEvent,String descriptionOfTheEvent, int numberOfPeople, String image) {
        myRef.push().setValue(new EventCard(stringLocation, currentUser, currentDateString, selectedCategory, timeString, nameOfTheEvent, descriptionOfTheEvent,numberOfPeople, image));
    }
    public EventCardLiveData getAllEventCards() {
        return eventCard;
    }

//    public void searchEventCard(String query) {
//        List<EventCard> result = new ArrayList<>();
//        for (EventCard p : eventCardArrayList) {
//            if (p.getEventName().toLowerCase().contains(query.toLowerCase())) {
//                result.add(p);
//            }
//        }
//        searchedEventCard.setValue(result);
//    }

    public static synchronized EventCardRepository getInstance() {
        if (instance == null)
            instance = new EventCardRepository();
        return instance;
    }
}
