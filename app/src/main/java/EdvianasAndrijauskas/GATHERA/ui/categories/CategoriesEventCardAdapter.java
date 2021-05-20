package EdvianasAndrijauskas.GATHERA.ui.categories;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import EdvianasAndrijauskas.GATHERA.R;

import java.util.ArrayList;

public class CategoriesEventCardAdapter extends RecyclerView.Adapter<CategoriesEventCardAdapter.ViewHolder> {

    private ArrayList<CategoriesEventCard> categoriesEventCardList;

    public CategoriesEventCardAdapter(ArrayList<CategoriesEventCard> categories) {
        this.categoriesEventCardList = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_card_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(categoriesEventCardList.get(position).getTextView());
        holder.icon.setImageResource(categoriesEventCardList.get(position).getIconId());
        holder.picture.setImageResource(categoriesEventCardList.get(position).getImageId());
        if (!categoriesEventCardList.get(position).isFav()) {
            holder.icon.setTag("R.drawable.unlikeCategory");
        } else {
            holder.icon.setTag("R.drawable.likedCategory");
        }
    }

    @Override
    public int getItemCount() {
        return categoriesEventCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageButton icon;
        ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.imageButton);
//            if (icon.getTag().equals("R.drawable.unlikeCategory"))
//                icon.setTag("R.drawable.unlikeCategory");
            picture = itemView.findViewById(R.id.picture);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        if (icon.getTag().equals("R.drawable.unlikeCategory")) {
                            int index = categoriesEventCardList.indexOf(categoriesEventCardList.get(getAdapterPosition()));
                            CategoriesEventCard card = categoriesEventCardList.get(getAdapterPosition());
                            card.setFav(true);
                            categoriesEventCardList.remove(index);
                            categoriesEventCardList.add(0, card);
                            card.setIconId(R.drawable.likedcategory);
                            notifyDataSetChanged();
                        } else {
                            int index = categoriesEventCardList.indexOf(categoriesEventCardList.get(getAdapterPosition()));
                            CategoriesEventCard card = categoriesEventCardList.get(getAdapterPosition());
                            categoriesEventCardList.remove(index);
                            categoriesEventCardList.add(9, card);
                            card.setIconId(R.drawable.unlickedcategory);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}
