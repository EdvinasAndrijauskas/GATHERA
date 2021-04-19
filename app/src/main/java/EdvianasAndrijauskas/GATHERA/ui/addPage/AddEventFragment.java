package EdvianasAndrijauskas.GATHERA.ui.addPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.favorite.FavoriteViewModel;
import EdvianasAndrijauskas.GATHERA.ui.profile.ProfileViewModel;

public class AddEventFragment extends Fragment {

    private AddEventViewModel addEventViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);
        View root = inflater.inflate(R.layout.add_event_fragment, container, false);
        final TextView textView = root.findViewById(R.id.addEvent_text);
        addEventViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}