package EdvianasAndrijauskas.GATHERA.ui.addPage;

import android.app.Application;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.User.UserRepository;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCard;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCardRepository;

public class AddEventViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private EventCardRepository eventCardRepository;

    public AddEventViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        eventCardRepository = EventCardRepository.getInstance();
    }
    public void init() {
        String userId = userRepository.getCurrentUser().getValue().getUid();
        eventCardRepository.init(userId);
    }

    public void saveEventCard(String stringLocation,String currentUser,String currentDateString,String selectedCategory,String timeString,String nameOfTheEvent,String descriptionOfTheEvent, int numberOfPeople, String image) {
        eventCardRepository.saveEvent(stringLocation, currentUser, currentDateString, selectedCategory, timeString, nameOfTheEvent, descriptionOfTheEvent,numberOfPeople, image);
    }

    public LiveData<ArrayList<EventCard>> getEvents() {
        return eventCardRepository.getAllEventCards();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void signOut() {
        userRepository.signOut();
    }

}