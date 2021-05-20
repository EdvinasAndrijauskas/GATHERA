package EdvianasAndrijauskas.GATHERA.ui.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import EdvianasAndrijauskas.GATHERA.ui.User.UserRepository;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCardRepository;

public class AddEventViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private EventCardRepository eventCardRepository;

    public AddEventViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        eventCardRepository = EventCardRepository.getInstance();
    }

    public void init() {
        eventCardRepository.init();
    }

    public void saveEventCard(int peopleAtending, String stringLocation, String userId, String currentDateString, String selectedCategory, String timeString, String nameOfTheEvent, String descriptionOfTheEvent, int numberOfPeople, String image) {
        eventCardRepository.saveEvent(peopleAtending, stringLocation, userId, currentDateString, selectedCategory, timeString, nameOfTheEvent, descriptionOfTheEvent, numberOfPeople, image);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void signOut() {
        userRepository.signOut();
    }

}