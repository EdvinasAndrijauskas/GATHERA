package EdvianasAndrijauskas.GATHERA.ui.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import EdvianasAndrijauskas.GATHERA.R;

public class SignInFragment extends Fragment {

    private static final int RC_SIGN_IN = 42;
    private static final int RESULT_OK = -1;
    private SignInViewModel signInViewModel;
    private BottomNavigationView navView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signin, container, false);
        checkIfSignedIn();
        Button signInButton = root.findViewById(R.id.sign_in_button);
        navView = (BottomNavigationView) getActivity().findViewById(R.id.nav_view);
        if(navView != null)
        {
            navView.setVisibility(View.INVISIBLE);
        }

        signInButton.setOnClickListener(view -> signIn(view));
        return root;
    }

    private void checkIfSignedIn() {
        signInViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null)
                goToMainFragment();
        });
    }

    private void goToMainFragment() {
        if(navView != null)
        {
            navView.setVisibility(View.VISIBLE);
        }
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_home);
    }

    public void signIn(View v) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build());

            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.icon_gthera).setTheme(R.style.Theme_GATHERA)
                    .build();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInRequest(resultCode);
        }
    }

    private void handleSignInRequest(int resultCode) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(getContext(), "Welcome " + signInViewModel.getCurrentUser().getValue().getDisplayName(), Toast.LENGTH_SHORT).show();
            goToMainFragment();
        }
        else
            Toast.makeText(getContext(), "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
    }

}