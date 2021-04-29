package EdvianasAndrijauskas.GATHERA.ui.addPage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import EdvianasAndrijauskas.GATHERA.R;

import static android.app.Activity.RESULT_OK;


public class AddEventFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final int GALLERY_REQUEST_CODE = 123;
    private AddEventViewModel addEventViewModel;
    private ImageView view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);
        View root = inflater.inflate(R.layout.add_event_fragment, container, false);
        Button button = root.findViewById(R.id.pickdate);
        button.setOnClickListener(v -> {
            DialogFragment datePicker = new AddEventFragment();
            datePicker.show(getChildFragmentManager(), "date picker");
        });

        view = root.findViewById(R.id.add_event_image);
        Button selectImageButton = root.findViewById(R.id.add_event_selectImageButton);

        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pick an image"), GALLERY_REQUEST_CODE);
        });

        return root;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Toast.makeText(getContext(), currentDateString, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Uri imageData = data.getData();
            view.setImageURI(imageData);
        }
    }
}


