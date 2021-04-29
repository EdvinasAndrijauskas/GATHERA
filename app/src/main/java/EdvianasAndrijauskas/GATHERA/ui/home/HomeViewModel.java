package EdvianasAndrijauskas.GATHERA.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import EdvianasAndrijauskas.GATHERA.ui.data.UserRepository;

public class HomeViewModel extends AndroidViewModel {
        private final UserRepository userRepository;
        private final EventCardRepository eventRepository;

    public HomeViewModel(Application app){
            super(app);
            userRepository = UserRepository.getInstance(app);
            eventRepository = EventCardRepository.getInstance();
        }

        public void init() {
            String userId = userRepository.getCurrentUser().getValue().getUid();
            eventRepository.init(userId);
        }

        public LiveData<FirebaseUser> getCurrentUser(){
            return userRepository.getCurrentUser();
        }

        public void saveEventCard(String day, String month, String time, String eventName, String description, int howManyPeopleAreComing, int imageId, int monthDay) {
            eventRepository.saveEventCard(day,month,time,eventName,description,howManyPeopleAreComing,imageId,monthDay);
        }

        public LiveData<EventCard> getEvent() {
            return eventRepository.getEventCard();
        }


        public void searchEventCard(String query) {
            eventRepository.searchEventCard(query);
        }

        public LiveData<List<EventCard>> getSearchedEvent() {
            return eventRepository.getSearchedEvent();
        }
    }