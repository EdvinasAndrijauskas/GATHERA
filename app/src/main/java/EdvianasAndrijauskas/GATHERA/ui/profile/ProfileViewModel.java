package EdvianasAndrijauskas.GATHERA.ui.profile;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import EdvianasAndrijauskas.GATHERA.ui.data.UserRepository;
public class ProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public ProfileViewModel(Application app) {
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