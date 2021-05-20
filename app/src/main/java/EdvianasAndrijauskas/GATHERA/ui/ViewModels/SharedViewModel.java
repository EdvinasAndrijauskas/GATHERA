package EdvianasAndrijauskas.GATHERA.ui.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCard;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCardRepository;

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
