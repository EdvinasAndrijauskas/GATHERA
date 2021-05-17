package EdvianasAndrijauskas.GATHERA.ui.home;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import EdvianasAndrijauskas.GATHERA.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.ViewHolder> {

    private ArrayList<EventCard> eventCardList = new ArrayList<>();
    private Context context;

    public EventCardAdapter(Context context)  {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_card_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(eventCardList.get(position).getEventName());
        holder.count.setText("People coming: " + eventCardList.get(position).getHowManyPeopleAreComing());
        holder.description.setText(eventCardList.get(position).getDescription());
        holder.when.setText(eventCardList.get(position).getDate());
        Glide.with(context)
                .load(eventCardList.get(position).getImage())
                .into(holder.icon);
        //here if statement can do that for example background
    }

    public void updateList(ArrayList<EventCard> eventCards) {
        this.eventCardList = eventCards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return eventCardList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView count;
        TextView description;
        ImageView icon;
        TextView when;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            count = itemView.findViewById(R.id.count);
            description = itemView.findViewById(R.id.description);
            icon = itemView.findViewById(R.id.iconn);
            when = itemView.findViewById(R.id.when);
        }
    }

}
