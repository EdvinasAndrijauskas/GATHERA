package EdvianasAndrijauskas.GATHERA.ui.addPage;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.squareup.picasso.Picasso;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCard;

import static android.app.Activity.RESULT_OK;


public class AddEventFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private AddEventViewModel addEventViewModel;
    private ImageView view;
    private Button createButton;
    private EditText eventName, description, numberOfPeople, spinnerEditText, hourEditText, minuteEditText;
    private Spinner category;
    private DatabaseReference reff;
    private EventCard eventCard;
    private DatePickerDialog picker;
    private Uri imageData;
    private StorageReference storageRefs;
    private StorageTask uploadTask;
    private ProgressBar progressBar;
    private String currentDateString;
    private String timeString;
    private String selectedCategory;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);
        View root = inflater.inflate(R.layout.add_event_fragment, container, false);
        eventName = (EditText) root.findViewById(R.id.add_event_name);
        description = (EditText) root.findViewById(R.id.add_event_description);
        category =  root.findViewById(R.id.add_event_categorySpinner);
        numberOfPeople = root.findViewById(R.id.add_event_maxNumber);
        hourEditText = root.findViewById(R.id.add_event_hour);
        minuteEditText = root.findViewById(R.id.add_event_minutes);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gathera-2cd58-default-rtdb.europe-west1.firebasedatabase.app/");
        reff = database.getReference().child("Events");
        view = root.findViewById(R.id.add_event_image);
        storageRefs = FirebaseStorage.getInstance().getReference("Images");
        progressBar = root.findViewById(R.id.add_event_progressBar);
        Button selectImageButton = root.findViewById(R.id.add_event_selectImageButton);
        selectImageButton.setOnClickListener(v -> {
            openFileChooser();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int
                    i, long l) {
                selectedCategory = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        createButton = root.findViewById(R.id.add_event_createButton);
        createButton.setOnClickListener(v ->
        {
            String  maxNumber = numberOfPeople.getText().toString().trim();
            String nameOfTheEvent = eventName.getText().toString().trim();
            String descriptionOfTheEvent = description.getText().toString().trim();
            String hour = hourEditText.getText().toString().trim();
            String minute = minuteEditText.getText().toString().trim();

            if (maxNumber.isEmpty()) {
                numberOfPeople.requestFocus();
                numberOfPeople.setError("Enter number");
                numberOfPeople.setText("");
            }
            if (nameOfTheEvent.isEmpty()) {
                eventName.requestFocus();
                eventName.setError("Enter event name");
            }
//            if (categoryOfTheEvent.isEmpty()) {
//                category.requestFocus();
//                category.setError("Enter category");
//            }
            if (descriptionOfTheEvent.isEmpty()) {
                description.requestFocus();
                description.setError("Enter description");
            }
            if (spinnerEditText.getText().toString().isEmpty()) {
                spinnerEditText.requestFocus();
                spinnerEditText.setError("Enter date");
            }
            timeString = hour + ":" + minute;
            if (isValidTime(timeString)) {
                String currentUser = addEventViewModel.getUserRepository().getCurrentUser().getValue().getUid();
                eventCard = new EventCard(currentUser, currentDateString, selectedCategory, timeString, nameOfTheEvent, descriptionOfTheEvent, Integer.parseInt(maxNumber), uploadFile());
                reff.push().setValue(eventCard);
                reff.getKey();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_home);
            } else {
                hourEditText.requestFocus();
                hourEditText.setError("Enter valid time");
                hourEditText.setText("");
                minuteEditText.requestFocus();
                minuteEditText.setError("Enter valid time");
                minuteEditText.setText("");
            }

        });
        spinnerEditText = root.findViewById(R.id.add_event_editTextForSpinner);
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
        return root;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageData = data.getData();
            //fancier than view.setImage()
            Picasso.get().load(imageData).into(view);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private String uploadFile() {
        if (imageData != null) {
            StorageReference fileReference = storageRefs.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageData));
            String location = System.currentTimeMillis()
                    + "." + getFileExtension(imageData);
            uploadTask = fileReference.putFile(imageData)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
            return location;
        }
        return null;
    }

    public static boolean isValidTime(String time) {
        // Regex to check valid time in 24-hour format.
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern p = Pattern.compile(regex);
        if (time == null) {
            return false;
        }
        Matcher m = p.matcher(time);
        return m.matches();
    }


}


