package EdvianasAndrijauskas.GATHERA.ui;

import android.util.EventLog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import EdvianasAndrijauskas.GATHERA.ui.home.EventCard;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCardRepository;

public class SharedViewModel extends ViewModel {
    private static SharedViewModel instance;
    private EventCardRepository eventCardRepository;
    private MutableLiveData<EventCard> eventCard = new MutableLiveData<>();

    public SharedViewModel() {
        this.eventCardRepository = new EventCardRepository();
    }

    public void updateEventCard(EventCard eventCard) {
        eventCardRepository.updateEvent(eventCard);
    }

    public LiveData<EventCard> getEventCardBy() {
            return eventCard;
    }

    public static synchronized SharedViewModel getInstance() {
        if (instance == null)
            instance = new SharedViewModel();
        return instance;
    }
}
