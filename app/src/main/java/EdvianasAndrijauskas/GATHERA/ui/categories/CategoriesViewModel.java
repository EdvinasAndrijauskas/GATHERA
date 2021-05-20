package EdvianasAndrijauskas.GATHERA.ui.categories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import EdvianasAndrijauskas.GATHERA.ui.home.EventCardRepository;

public class CategoriesViewModel extends ViewModel {

    private CategoriesRepository repository;

    public CategoriesViewModel() {
        repository = CategoriesRepository.getInstance();

    }

    public ArrayList<CategoriesEventCard> getCategoryCards() {
        return repository.getCategoriesEventCardList();
    }
}