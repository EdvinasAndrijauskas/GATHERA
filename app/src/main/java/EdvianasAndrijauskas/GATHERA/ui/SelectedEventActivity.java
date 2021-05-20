package EdvianasAndrijauskas.GATHERA.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import java.text.MessageFormat;
import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCard;
import EdvianasAndrijauskas.GATHERA.ui.EventCard.EventCardAdapter;
import EdvianasAndrijauskas.GATHERA.ui.ViewModels.HomeViewModel;

public class SelectedEventActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    private HomeViewModel viewModel;
    private int currentCount;
    private DatabaseReference ref = FirebaseDatabase.getInstance("https://gathera-2cd58-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Events");
    private EventCardAdapter adapter;
    private CheckBox joinCheck;
    private boolean joinCheckFalseValue;
    private EventCard eventCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        setContentView(R.layout.selected_eventcard_activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("EventCard");
        eventCard = gson.fromJson(data, EventCard.class);

        currentCount = eventCard.getPeopleAttending();
        joinCheck = findViewById(R.id.selected_checkBox);
        joinCheckFalseValue = false;

        TextView name = findViewById(R.id.selected_name);
        name.setText(eventCard.getEventName());

        TextView description = findViewById(R.id.selected_description);
        description.setText(eventCard.getDescription());

        TextView date = findViewById(R.id.selected_when);
        date.setText(eventCard.getDate());

        TextView time = findViewById(R.id.selected_time);
        time.setText(eventCard.getTime());

        TextView maximum = findViewById(R.id.selected_count);
        maximum.setText(MessageFormat.format("People attending {0}/{1}", currentCount, eventCard.getHowManyPeopleAreComing()));


        TextView category = findViewById(R.id.selected_category);
        category.setText("Category : "+eventCard.getCategory());

        ImageView image = findViewById(R.id.selected_imageView);
        Glide.with(this)
                .load(eventCard.getImage()).fitCenter().centerCrop().into(image);

        ImageButton shareButton = findViewById(R.id.selected_share);
        shareButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
            intent.putExtra(Intent.EXTRA_SUBJECT, "GATHERA EVENT!!!! Hi join the " + eventCard.getEventName() + "!");
            intent.putExtra(Intent.EXTRA_TEXT, "Come join the event in GATHERA this event" + eventCard.getEventName() + "!" + "on, " + eventCard.getDate() + " at " + eventCard.getTime() + " Will be fun!!");
            startActivity(intent);
        });


        ImageButton exitButton = findViewById(R.id.selected_exitButton);
        exitButton.setOnClickListener(v -> {
            saveCheckBox();

            finish();
        });

        joinCheck.setOnClickListener(v -> {
            if (joinCheck.isChecked()) {
                if (eventCard.getHowManyPeopleAreComing() > currentCount) {
                    currentCount++;
                    String eventId = eventCard.getId();
                    ref.child(eventId).child("peopleAttending").setValue(currentCount);
                    adapter = new EventCardAdapter();
                    adapter.update();
                    maximum.setText(MessageFormat.format("People attending {0}/{1}", currentCount, eventCard.getHowManyPeopleAreComing()));
                }
            }
            if (!joinCheck.isChecked()) {
                currentCount--;
                String eventId = eventCard.getId();
                ref.child(eventId).child("peopleAttending").setValue(currentCount);
                adapter = new EventCardAdapter();
                adapter.update();
                maximum.setText(MessageFormat.format("People attending {0}/{1}", currentCount, eventCard.getHowManyPeopleAreComing()));

            }
        });
        loadCheckBox();
        updateViews();

        if (!joinCheck.isChecked() && eventCard.getHowManyPeopleAreComing() == currentCount) {
            joinCheck.setVisibility(View.INVISIBLE);
        }
    }

    public void saveCheckBox() {
        SharedPreferences sharedPreferences = getSharedPreferences("join", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("boxIsChecked", joinCheck.isChecked());
        editor.apply();
    }


    public void loadCheckBox() {
        SharedPreferences sharedPreferences = getSharedPreferences("join", MODE_PRIVATE);
        joinCheckFalseValue = sharedPreferences.getBoolean("boxIsChecked", false);
    }

    public void updateViews() {
        joinCheck.setChecked(joinCheckFalseValue);
    }

}
