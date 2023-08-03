package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * A fragment that displays a list of favorite neighbours.
 * Supports deleting favorite neighbours and updating the list accordingly.
 */

public class FavoriteFragment extends Fragment implements DeleteClickListener {

    private NeighbourApiService mApiService;
    private List<Neighbour> mFavoriteNeighbours;
    private RecyclerView mRecyclerView;

    /**
     * Creates a new instance of the FavoriteFragment.
     * @return The newly created FavoriteFragment instance.
     */

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    /**
     * Called when the fragment is being created.
     * Initializes the NeighbourApiService.
     * @param savedInstanceState The saved instance state.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     * @param inflater           The layout inflater.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The saved instance state.
     * @return The created view.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_neighbours);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Initializes the list of favorite neighbours and sets up the RecyclerView.
     * Called when the fragment is resumed or when there are changes in the list of favorite neighbours.
     */

    private void initList() {
        mFavoriteNeighbours = mApiService.getFavoriteNeighbours();
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavoriteNeighbours, this));
    }

    /**
     * Called when the fragment is becoming visible to the user.
     * Calls the initList() method to initialize the list of favorite neighbours.
     */

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    /**
     * Called when the fragment is starting.
     * Registers the fragment to receive events using EventBus.
     */

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    /**
     * Called when the fragment is stopping.
     * Unregisters the fragment from receiving events using EventBus.
     */

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Event subscriber method that is called when a favorite neighbour deletion event is received.
     * Updates the list of favorite neighbours and refreshes the RecyclerView.
     * @param event The DeleteFavoriteNeighbourEvent containing the neighbour to be deleted.
     */

    @Subscribe
    public void onDeleteFavoriteNeighbour(DeleteFavoriteNeighbourEvent event) {
        Neighbour neighbour = event.neighbour;
        neighbour.setFavorite(false);
        mFavoriteNeighbours.remove(neighbour);
        initList();
    }

    /**
     * Called when the delete button for a neighbour is clicked.
     * Posts a DeleteFavoriteNeighbourEvent to the EventBus to initiate the deletion process.
     * @param neighbour The neighbour object associated with the clicked delete button.
     */

    @Override
    public void onDeleteClick(Neighbour neighbour) {
        EventBus.getDefault().post(new DeleteFavoriteNeighbourEvent(neighbour));
    }
}
