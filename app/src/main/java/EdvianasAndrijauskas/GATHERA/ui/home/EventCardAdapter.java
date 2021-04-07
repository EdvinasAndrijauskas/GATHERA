package EdvianasAndrijauskas.GATHERA.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import EdvianasAndrijauskas.GATHERA.R;

import java.util.ArrayList;

public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.ViewHolder> {

    private ArrayList<EventCard> eventCardList;

    public EventCardAdapter(ArrayList<EventCard> list) {
        eventCardList = list;
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
        holder.icon.setImageResource(eventCardList.get(position).getImageViewId());
        holder.when.setText(eventCardList.get(position).getDay() + ", " + eventCardList.get(position).getMonthDay() + " " + eventCardList.get(position).getMonth() + ", " + eventCardList.get(position).getTime());
        //here if statement can do that for example background
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
