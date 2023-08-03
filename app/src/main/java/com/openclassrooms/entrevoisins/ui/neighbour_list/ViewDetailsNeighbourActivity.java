package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import com.openclassrooms.entrevoisins.databinding.ViewNeighbourDetailsBinding;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;


public class ViewDetailsNeighbourActivity extends AppCompatActivity {

    private NeighbourApiService neighbourApiService;

    public Neighbour neighbour;
    private boolean isFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Using ViewBinding.
        ViewNeighbourDetailsBinding binding = ViewNeighbourDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Intent Recovery.
        Intent intent = getIntent();
        Neighbour neighbour = (Neighbour) intent.getSerializableExtra("neighbour");

        if (neighbour != null) {
            long id = neighbour.getId();
            String name = neighbour.getName();
            String avatarUrl = neighbour.getAvatarUrl();
            String address = neighbour.getAddress();
            String phoneNumber = neighbour.getPhoneNumber();
            String aboutMe = neighbour.getAboutMe();

            //Show neighbor data retrieved above.
            Glide.with(this)
                    .load(avatarUrl)
                    .into(binding.avatarUrlImageView);

            binding.name1TextView.setText(name);
            binding.name2TextView.setText(name);
            binding.addressTextView.setText(address);
            binding.phoneNumberTextView.setText(phoneNumber);
            binding.aboutMeTextView.setText(aboutMe);

            // Associate neighbor name with facebook url.
            String facebookUrl = "https://www.facebook.com/%s";
            String facebookUrlWithNeighbourName = String.format(facebookUrl, name);
            binding.socialNetwork.setText(facebookUrlWithNeighbourName);
        }

        // Back button configuration
        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call to the finish method to return to the previous activity.
                finish();
            }
        });

        // Configuring Views.
        ImageButton favoriteButton = binding.favoriteButton;

        // Recovering favorite status.
        isFavorite = neighbour.isFavorite();
        setFavoriteButtonAppearance(favoriteButton, isFavorite);

        // Configuring the click listener for the favorite button.
        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                setFavoriteButtonAppearance(favoriteButton, isFavorite);
                neighbour.setFavorite(isFavorite);

                // Updated list of favorite neighbors in the API service.
                List<Neighbour> favoriteNeighbours = neighbourApiService.getFavoriteNeighbours();
                if (isFavorite) {
                    favoriteNeighbours.add(neighbour);
                } else {
                    favoriteNeighbours.remove(neighbour);
                }
                neighbourApiService.setFavoriteNeighbours(favoriteNeighbours);
            }
        });

        // API service initialization.
        neighbourApiService = DI.getNeighbourApiService();
    }

    private void setFavoriteButtonAppearance(ImageButton button, boolean isFavorite) {
        if (isFavorite) {
            int color = ContextCompat.getColor(this, R.color.favorite_yellow);
            button.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            button.clearColorFilter();
        }
    }
}
