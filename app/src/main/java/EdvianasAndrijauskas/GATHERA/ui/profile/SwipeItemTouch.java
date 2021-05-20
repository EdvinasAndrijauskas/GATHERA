package EdvianasAndrijauskas.GATHERA.ui.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import EdvianasAndrijauskas.GATHERA.R;
import EdvianasAndrijauskas.GATHERA.ui.home.EventCardAdapter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class SwipeItemTouch extends ItemTouchHelper.SimpleCallback {
    private EventCardAdapter eventCardAdapterAdapter;

    public SwipeItemTouch(EventCardAdapter eventCardAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.eventCardAdapterAdapter = eventCardAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(eventCardAdapterAdapter.getContext());
            builder.setTitle("Delete task");
            builder.setMessage("Are you sure you want to delete this task?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eventCardAdapterAdapter.deleteItem(position);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eventCardAdapterAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            eventCardAdapterAdapter.editCard(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(eventCardAdapterAdapter.getContext(), R.color.delete))
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(eventCardAdapterAdapter.getContext(), R.color.edit))
                .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                .create().decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}