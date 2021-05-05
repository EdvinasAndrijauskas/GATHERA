package EdvianasAndrijauskas.GATHERA.ui.addPage;

import android.app.Application;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.User.UserRepository;

public class AddEventViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public AddEventViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void signOut() {
        userRepository.signOut();
    }

}