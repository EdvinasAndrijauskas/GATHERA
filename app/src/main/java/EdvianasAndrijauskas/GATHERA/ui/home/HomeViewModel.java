package EdvianasAndrijauskas.GATHERA.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import EdvianasAndrijauskas.GATHERA.ui.User.UserRepository;

public class HomeViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final EventCardRepository eventCardRepository;

    public HomeViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        eventCardRepository = EventCardRepository.getInstance();
    }

    public void init() {
        eventCardRepository.init();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public LiveData<ArrayList<EventCard>> getAllEvents() {
        return eventCardRepository.getAllEvents();
    }

        public void searchEventCard(String query) {
            eventCardRepository.searchEventCard(query);
        }

        public LiveData<ArrayList<EventCard>> getSearchedEvent() {
            return eventCardRepository.getSearchedEventCards();
        }
}