package EdvianasAndrijauskas.GATHERA.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    EventCardRepository repository = new EventCardRepository();

    public void searchEventCard(String query) {
        repository.searchEventCard(query);
    }

    public LiveData<List<EventCard>> getSearchedEvent() {
        return repository.getSearchedEvent();
    }
}