package EdvianasAndrijauskas.GATHERA.ui.ViewModels;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import EdvianasAndrijauskas.GATHERA.ui.User.UserRepository;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCard;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCardRepository;

public class ProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private EventCardRepository eventCardRepository;

    public ProfileViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        this.eventCardRepository = EventCardRepository.getInstance();
    }

    public void init() {
        eventCardRepository.init();
    }


    public LiveData<ArrayList<EventCard>> getAllEvents() {
        return eventCardRepository.getAllEvents();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void signOut() {
        userRepository.signOut();
    }
}