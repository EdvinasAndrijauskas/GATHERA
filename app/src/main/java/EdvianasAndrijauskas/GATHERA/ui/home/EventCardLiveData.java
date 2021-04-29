package EdvianasAndrijauskas.GATHERA.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class EventCardLiveData extends LiveData<EventCard> {

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            EventCard eventCard = snapshot.getValue(EventCard.class);
            setValue(eventCard);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    DatabaseReference databaseReference;

    public EventCardLiveData(DatabaseReference ref) {
        databaseReference = ref;
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
