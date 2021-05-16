package EdvianasAndrijauskas.GATHERA.ui.home;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class EventCardRepository {
//    private final List<EventCard> eventCardArrayList = new ArrayList<>();
//    private final MutableLiveData<List<EventCard>> searchedEventCard = new MutableLiveData<>();
    private static EventCardRepository instance;
    private DatabaseReference myRef;
    private EventCardLiveDataForUser eventCard;
    private ArrayList<String> pushedKeys;

    public EventCardRepository()
    {
        this.pushedKeys = new ArrayList<>();
    }

    public void init() {
        myRef =  FirebaseDatabase.getInstance("https://gathera-2cd58-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Events");
        eventCard = new EventCardLiveDataForUser(myRef);
    }


    public void saveEvent(String stringLocation,String userId,String currentDateString,String selectedCategory,String timeString,String nameOfTheEvent,String descriptionOfTheEvent, int numberOfPeople, String image) {
        myRef.push().setValue(new EventCard(stringLocation,userId, currentDateString, selectedCategory, timeString, nameOfTheEvent, descriptionOfTheEvent,numberOfPeople, image));

        pushedKeys.add(myRef.push().toString());
    }
    public EventCardLiveDataForUser getAllEvents() {
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
