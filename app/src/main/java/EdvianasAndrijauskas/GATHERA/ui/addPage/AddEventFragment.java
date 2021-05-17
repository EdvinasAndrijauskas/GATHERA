package EdvianasAndrijauskas.GATHERA.ui.addPage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
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

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.squareup.picasso.Picasso;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCard;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.app.Activity.RESULT_OK;


public class AddEventFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private AddEventViewModel addEventViewModel;
    private ImageView view;
    private Button createButton, cancelButton;
    private EditText eventName, description, numberOfPeople, spinnerEditText, hourEditText, minuteEditText, location;
    private Spinner category;
    private EventCard eventCard;
    private DatePickerDialog picker;
    private Uri imageData;
    private StorageReference storageRefs;
    private StorageTask uploadTask;
    private String currentDateString;
    private String timeString;
    private String selectedCategory;
    private String currentUser;
    private String downloadUri = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);
        View root = inflater.inflate(R.layout.add_event_fragment, container, false);
        eventName = (EditText) root.findViewById(R.id.add_event_name);
        description = (EditText) root.findViewById(R.id.add_event_description);
        category = root.findViewById(R.id.add_event_categorySpinner);
        numberOfPeople = root.findViewById(R.id.add_event_maxNumber);
        hourEditText = root.findViewById(R.id.add_event_hour);
        minuteEditText = root.findViewById(R.id.add_event_minutes);
        storageRefs = FirebaseStorage.getInstance().getReference();
        view = root.findViewById(R.id.add_event_image);
        cancelButton = root.findViewById(R.id.add_event_cancel);
        location = root.findViewById(R.id.add_event_location);
        addEventViewModel.init();
        currentUser = addEventViewModel.getUserRepository().getCurrentUser().getValue().getUid();


        BottomNavigationView navView = (BottomNavigationView) getActivity().findViewById(R.id.nav_view);
        if (navView != null) {
            navView.setVisibility(View.GONE);
        }

        cancelButton.setOnClickListener(v ->
        {
            navView.setVisibility(View.VISIBLE);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_home);
        });

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
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        createButton = root.findViewById(R.id.add_event_createButton);
        createButton.setOnClickListener(v ->
        {
            UploadDownloadImageFileToFirebaseStorage();
            String maxNumber = numberOfPeople.getText().toString().trim();
            String nameOfTheEvent = eventName.getText().toString().trim();
            String descriptionOfTheEvent = description.getText().toString().trim();
            String hour = hourEditText.getText().toString().trim();
            String minute = minuteEditText.getText().toString().trim();
            String stringLocation = location.getText().toString().trim();

            if (maxNumber.isEmpty()) {
                numberOfPeople.requestFocus();
                numberOfPeople.setError("Enter number");
                numberOfPeople.setText("");
                return;
            }
            if (stringLocation.isEmpty()) {
                location.requestFocus();
                location.setError("Enter Location");
                location.setText("");
                return;
            }
            if (nameOfTheEvent.isEmpty()) {
                eventName.requestFocus();
                eventName.setError("Enter event name");
                return;
            }

            if (descriptionOfTheEvent.isEmpty()) {
                description.requestFocus();
                description.setError("Enter description");
                return;
            }
            if (spinnerEditText.getText().toString().isEmpty()) {
                spinnerEditText.requestFocus();
                spinnerEditText.setError("Enter date");
                return;
            }
            timeString = hour + ":" + minute;
            if (!isValidTime(timeString)) {
                hourEditText.requestFocus();
                hourEditText.setError("Enter valid time");
                hourEditText.setText("");
                minuteEditText.requestFocus();
                minuteEditText.setError("Enter valid time");
                minuteEditText.setText("");
                return;
            }
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    addEventViewModel.saveEventCard(stringLocation, currentUser, currentDateString, selectedCategory, timeString, nameOfTheEvent, descriptionOfTheEvent, Integer.parseInt(maxNumber), downloadUri);
                }
            }, 6000);


            navView.setVisibility(View.VISIBLE);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_home);

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
            //fancier way to set image than view.setImage()
            view.setImageURI(imageData);
//            Picasso.get().load(imageData).into(view);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void UploadDownloadImageFileToFirebaseStorage() {
        if (imageData != null) {
            StorageReference ref = storageRefs.child("Images/" + System.currentTimeMillis() + "." + getFileExtension(imageData));
            uploadTask = ref.putFile(imageData);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Image Uploaded Successfully ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            Toast.makeText(getContext(), "Please Select Image or Add Image Name", Toast.LENGTH_SHORT).show();
        }
        final StorageReference ref = storageRefs.child("Images/" + System.currentTimeMillis() + "." + getFileExtension(imageData));
        uploadTask = ref.putFile(imageData);
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
                    downloadUri = uri.toString();
                    System.out.println(downloadUri);
                } else {

                }
            }
        });
    }

//    private void uploadFile() {
//        if (imageData != null) {
//
//            StorageReference fileReference = storageRefs.child(imageData.getLastPathSegment());
//            name = imageData.getLastPathSegment();
//            uploadTask = fileReference.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();
////                    System.out.println(name);
////                    System.out.println(storageRefs.child(name));
//                    storageRefs.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            downloadUri = uri.toString();
//                        }
//                    });
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
//                    int IntValue = (int) Math.round(progress);
//                    Toast.makeText(getContext(), IntValue + "% uploaded please wait", Toast.LENGTH_SHORT).show();
//                }
//            })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }

//    private String getUrlAsync() {
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//        StorageReference dateRef = storageRef.child("Images/" + name);
//        System.out.println(dateRef);
//        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                downloadUri = uri.toString();
//            }
//        });
//        return downloadUri;
//    }

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


