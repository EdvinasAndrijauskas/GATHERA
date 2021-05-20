package EdvianasAndrijauskas.GATHERA.ui.ViewModels;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import EdvianasAndrijauskas.GATHERA.ui.EventCard.CategoriesEventCard;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.CategoriesRepository;

public class CategoriesViewModel extends ViewModel {

    private CategoriesRepository repository;

    public CategoriesViewModel() {
        repository = CategoriesRepository.getInstance();

    }

    public ArrayList<CategoriesEventCard> getCategoryCards() {
        return repository.getCategoriesEventCardList();
    }
}