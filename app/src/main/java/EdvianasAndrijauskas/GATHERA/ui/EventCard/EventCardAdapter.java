package EdvianasAndrijauskas.GATHERA.ui.EventCard;


import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.ViewModels.SharedViewModel;

import java.util.ArrayList;

public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.ViewHolder> {

    private ArrayList<EventCard> eventCardList = new ArrayList<>();
    private Context context;
    private OnListItemClickListener mOnListItemClickListener;

    public EventCardAdapter(Context context, OnListItemClickListener mOnListItemClickListener) {
        this.context = context;
        this.mOnListItemClickListener = mOnListItemClickListener;
    }
    public EventCardAdapter() {
    }

    public Context getContext() {
        return context;
    }

    public void deleteItem(int position) {
        EventCard eventCard = eventCardList.get(position);
        EventCardRepository.getInstance().deleteEvent(eventCard.getId());
    }

    public void editCard(int position) {
        EventCard eventCard = eventCardList.get(position);
        SharedViewModel.getInstance().updateEventCard(eventCard);
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navgiation_editEvent);
        notifyItemChanged(position);
    }

    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
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
        holder.count.setText("People attending:" + eventCardList.get(position).getPeopleAttending() + "/" + eventCardList.get(position).getHowManyPeopleAreComing());
        holder.when.setText(eventCardList.get(position).getDate());
        holder.time.setText(eventCardList.get(position).getTime());
        Glide.with(context).load(eventCardList.get(position).getImage()).override(400, 300).fitCenter().centerCrop().into(holder.icon);
        //here if statement can do that for example background
    }

    public void updateList(ArrayList<EventCard> eventCards) {
        this.eventCardList = eventCards;
        notifyDataSetChanged();
    }
    public void update() {
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return eventCardList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView count;
        TextView time;
        ImageView icon;
        TextView when;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            count = itemView.findViewById(R.id.count);
            time = itemView.findViewById(R.id.time);
            icon = itemView.findViewById(R.id.iconn);
            when = itemView.findViewById(R.id.when);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
