package EdvianasAndrijauskas.GATHERA.ui.EventCard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class EventCardRepository {
    private static EventCardRepository instance;
    private DatabaseReference myRef;
    private EventCardLiveDataForUser eventCard;
    private ArrayList<String> pushedKeys;
    private final MutableLiveData<ArrayList<EventCard>> searchedEventCard = new MutableLiveData<>();

    public EventCardRepository() {
        this.pushedKeys = new ArrayList<>();
    }

    public void init() {
        myRef = FirebaseDatabase.getInstance("https://gathera-2cd58-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Events");
        eventCard = new EventCardLiveDataForUser(myRef);
    }

    public void deleteEvent(String id) {
        myRef.child(id).removeValue();
    }
    public void updateEvent(EventCard event)
    {
        init();
        DatabaseReference editingEventCard = myRef.child(event.getId());
        editingEventCard.child("category").setValue(event.getCategory());
        editingEventCard.child("date").setValue(event.getDate());
        editingEventCard.child("description").setValue(event.getDescription());
        editingEventCard.child("eventName").setValue(event.getEventName());
        editingEventCard.child("howManyPeopleAreComing").setValue(event.getHowManyPeopleAreComing());
        editingEventCard.child("image").setValue(event.getImage());
        editingEventCard.child("location").setValue(event.getLocation());
        editingEventCard.child("peopleAttending").setValue(event.getPeopleAttending());
        editingEventCard.child("time").setValue(event.getTime());
    }

    public void saveEvent(int peopleAtending, String stringLocation, String userId, String currentDateString, String selectedCategory, String timeString, String nameOfTheEvent, String descriptionOfTheEvent, int numberOfPeople, String image) {
        myRef.push().setValue(new EventCard(peopleAtending, stringLocation, userId, currentDateString, selectedCategory, timeString, nameOfTheEvent, descriptionOfTheEvent, numberOfPeople, image));

        pushedKeys.add(myRef.push().toString());
    }

    public EventCardLiveDataForUser getAllEvents() {
        return eventCard;
    }

    public void searchEventCard(String query) {
        ArrayList<EventCard> result = new ArrayList<>();
        for (EventCard p : eventCard.getValue()) {
            if (p.getEventName().toLowerCase().contains(query.toLowerCase())) {
                result.add(p);
            }
        }
        searchedEventCard.setValue(result);
    }

    public LiveData<ArrayList<EventCard>> getSearchedEventCards() {
        return searchedEventCard;
    }

    public static synchronized EventCardRepository getInstance() {
        if (instance == null)
            instance = new EventCardRepository();
        return instance;
    }

}
