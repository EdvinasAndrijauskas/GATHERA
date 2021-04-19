package EdvianasAndrijauskas.GATHERA.ui.addPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddEventViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AddEventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addevent fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}