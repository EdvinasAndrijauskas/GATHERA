package EdvianasAndrijauskas.GATHERA.ui.profile;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import EdvianasAndrijauskas.GATHERA.ui.User.UserRepository;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCard;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCardRepository;

public class ProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private EventCardRepository eventCardRepository;

    public ProfileViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        this.eventCardRepository = EventCardRepository.getInstance();
    }

    public void init() {
        String userId = userRepository.getCurrentUser().getValue().getUid();
        eventCardRepository.init(userId);
    }

    public LiveData<ArrayList<EventCard>> getAllEvents() {
        return eventCardRepository.getAllEventCards();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void signOut() {
        userRepository.signOut();
    }
}