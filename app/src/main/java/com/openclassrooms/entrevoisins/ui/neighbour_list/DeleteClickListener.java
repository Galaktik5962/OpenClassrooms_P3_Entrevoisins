package com.openclassrooms.entrevoisins.ui.neighbour_list;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Interface definition for a callback to be invoked when a neighbour deletion is triggered.
 */

public interface DeleteClickListener {

    /**
     * Called when a neighbour deletion is triggered.
     * @param neighbour The neighbour object to be deleted.
     */
    void onDeleteClick(Neighbour neighbour);
}
