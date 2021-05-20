package EdvianasAndrijauskas.GATHERA.ui.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.ViewModels.SharedViewModel;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCard;

import static android.app.Activity.RESULT_OK;

public class EditFragment extends Fragment {
    private ImageView view;
    private Button createButton, cancelButton;
    private EditText eventName, description, numberOfPeople, spinnerEditText, hourEditText, minuteEditText, location;
    private SharedViewModel sharedViewModel;
    private DatePickerDialog picker;
    private String currentDateString;
    private Uri downloadUri ;
    private StorageReference storageRefs;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri imageData;
    private StorageTask uploadTask;
    private EventCard eventCard;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedViewModel = SharedViewModel.getInstance();
        View root = inflater.inflate(R.layout.edit_event_fragment, container, false);
        eventName = (EditText) root.findViewById(R.id.edit_event_name);
        description = (EditText) root.findViewById(R.id.edit_event_description);
//        spinnerEditText = root.findViewById(R.id.edit_event_categorySpinner);
        numberOfPeople = root.findViewById(R.id.edit_event_maxNumber);
        hourEditText = root.findViewById(R.id.edit_event_hour);
        minuteEditText = root.findViewById(R.id.edit_event_minutes);
        view = root.findViewById(R.id.edit_event_image);
        cancelButton = root.findViewById(R.id.edit_event_cancel);
        location = root.findViewById(R.id.edit_event_location);
        createButton = root.findViewById(R.id.edit_event_createButton);
        storageRefs = FirebaseStorage.getInstance().getReference();
        view = root.findViewById(R.id.edit_event_image);
        Button selectImageButton = root.findViewById(R.id.edit_event_selectImageButton);
        selectImageButton.setOnClickListener(v -> {
            openFileChooser();
        });

        sharedViewModel.getEventCardBy().observe(getViewLifecycleOwner(), this::setFields);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.updateEventCard(eventCard);
            }
        });
        return root;
    }

    private void setFields(EventCard eventCard) {
        spinnerEditText.setInputType(InputType.TYPE_NULL);
        spinnerEditText.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            cldr.set(Calendar.YEAR, year);
                            cldr.set(Calendar.MONTH, month);
                            cldr.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            spinnerEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(cldr.getTime());
                        }
                    }, year, month, day);
            picker.show();
        });

        eventName.setText(eventCard.getEventName());
        description.setText(eventCard.getDescription());
        spinnerEditText.setText(eventCard.getCategory());
        numberOfPeople.setText(eventCard.getHowManyPeopleAreComing());
        hourEditText.setText(eventCard.getTime().substring(0, 2));
        minuteEditText.setText(eventCard.getTime().substring(3, 5));
        location.setText(eventCard.getLocation());
        view.setImageURI(downloadUri);

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageData = data.getData();
            uploadImage();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadImage() {
        if (imageData != null) {

            StorageReference ref = storageRefs.child("Images/" + System.currentTimeMillis() + "." + getFileExtension(imageData));
            uploadTask = ref.putFile(imageData);
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        progressDialog.dismiss();
                        downloadUri = uri;

                        Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
