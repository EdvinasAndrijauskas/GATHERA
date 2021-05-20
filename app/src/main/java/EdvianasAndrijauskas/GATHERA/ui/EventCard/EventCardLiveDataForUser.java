package EdvianasAndrijauskas.GATHERA.ui.EventCard;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventCardLiveDataForUser extends LiveData<ArrayList<EventCard>> {

    private MutableLiveData<ArrayList<EventCard>> usersEvents;
    private final ValueEventListener listener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            ArrayList<EventCard> list = new ArrayList<>();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                EventCard eventCard = dataSnapshot.getValue(EventCard.class);
                eventCard.setId(dataSnapshot.getKey());
                list.add(eventCard);
                setValue(list);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    DatabaseReference databaseReference;

    public EventCardLiveDataForUser(DatabaseReference reference) {
        this.databaseReference = reference;
        this.usersEvents = new MutableLiveData<>();
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }

}
