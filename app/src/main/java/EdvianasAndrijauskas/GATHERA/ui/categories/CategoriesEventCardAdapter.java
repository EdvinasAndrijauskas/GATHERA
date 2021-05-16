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
    public CategoriesEventCardAdapter(ArrayList<CategoriesEventCard> categories)
    {
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
            picture = itemView.findViewById(R.id.picture);


//            title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(icon.getDrawable().equals(R.drawable.unlickedcategory))
//                    {
//                        title.setTextColor(Color.parseColor("#FFFF00"));
//
//                    }
//                    else
//                    {
//                        title.setTextColor(Color.parseColor("#FFFFFF"));
//                    }
//                }
//            });
//            picture.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(picture.getContext(), "Pcture pressed", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
