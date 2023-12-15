package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView Adapter for displaying a list of neighbors in a RecyclerView.
 * Each item in the list represents a neighbor and includes their name, avatar, and a delete button.
 * Additionally, clicking on an item opens a detailed view of the neighbor.
 */
public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    public final List<Neighbour> mNeighbours;
    private DeleteClickListener deleteClickListener;

    /**
     * Constructor for the MyNeighbourRecyclerViewAdapter.
     *
     * @param items              The list of neighbors to be displayed.
     * @param deleteClickListener The listener for delete button clicks.
     */
    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, DeleteClickListener deleteClickListener) {
        mNeighbours = items;
        this.deleteClickListener = deleteClickListener;
    }

    /**
     * Inflates the layout for a ViewHolder and returns the initialized ViewHolder.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview_neighbour, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder at the specified position.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        // Set a click listener for the delete button
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // When the delete button is clicked, invoke the onDeleteClick method of the DeleteClickListener
                // Pass the associated Neighbour object to the onDeleteClick method to handle the delete action
               deleteClickListener.onDeleteClick(neighbour);

                }
        });

        // Set a click listener for the entire item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // When the item view is clicked, create an intent to start the ViewDetailsNeighbourActivity
                Intent intent = new Intent(view.getContext(), ViewDetailsNeighbourActivity.class);

                // Put the selected Neighbour object as an extra in the intent, making it available to the next activity
                intent.putExtra("neighbour", (Serializable) neighbour);

                // Start the ViewDetailsNeighbourActivity using the created intent
                view.getContext().startActivity(intent);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    /**
     * ViewHolder class representing the views for each item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
