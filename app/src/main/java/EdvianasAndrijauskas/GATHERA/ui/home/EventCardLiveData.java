package EdvianasAndrijauskas.GATHERA.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventCardLiveData extends LiveData<ArrayList<EventCard>> {

    private final ValueEventListener listener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            ArrayList<EventCard> list = new ArrayList<>();
            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                EventCard eventCard = dataSnapshot.getValue(EventCard.class);
                list.add(eventCard);
                setValue(list);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    DatabaseReference databaseReference;

    public EventCardLiveData(DatabaseReference reference){
        databaseReference = reference;
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
