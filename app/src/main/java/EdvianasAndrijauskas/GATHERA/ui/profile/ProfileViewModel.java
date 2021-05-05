package EdvianasAndrijauskas.GATHERA.ui.profile;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import EdvianasAndrijauskas.GATHERA.ui.User.UserRepository;
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