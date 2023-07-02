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
    private Neighbour neighbour;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //utilisation du view binding
        ViewNeighbourDetailsBinding binding = ViewNeighbourDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //récupération de l'intent
        Intent intent = getIntent();
        Neighbour neighbour = (Neighbour) intent.getSerializableExtra("neighbour");

        if (neighbour != null) {
            long id = neighbour.getId();
            String name = neighbour.getName();
            String avatarUrl = neighbour.getAvatarUrl();
            String address = neighbour.getAddress();
            String phoneNumber = neighbour.getPhoneNumber();
            String aboutMe = neighbour.getAboutMe();

            // Afficher les données du voisin récupérées ci-dessus

            Glide.with(this)
                    .load(avatarUrl)
                    .into(binding.avatarUrlImageView);

            binding.name1TextView.setText(name);
            binding.name2TextView.setText(name);
            binding.addressTextView.setText(address);
            binding.phoneNumberTextView.setText(phoneNumber);
            binding.aboutMeTextView.setText(aboutMe);

            //associer le nom du voisin à l'url facebook
            String facebookUrl = "https://www.facebook.com/%s";
            String facebookUrlWithNeighbourName = String.format(facebookUrl, name);
            binding.socialNetwork.setText(facebookUrlWithNeighbourName);

        }

        //configuration du bouton de retour
        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // appel à la méthode finish pour revenir à l'activité précédente
                finish();
            }
        });

        // Configuration des vues
        ImageButton favoriteButton = binding.favoriteButton;

        // Récupération du statut de favori
        isFavorite = neighbour.isFavorite();
        setFavoriteButtonAppearance(favoriteButton, isFavorite);

        // Configuration de l'écouteur de clic pour le bouton favori
        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                setFavoriteButtonAppearance(favoriteButton, isFavorite);
                neighbour.setFavorite(isFavorite);

                // Mise à jour de la liste des voisins favoris dans l'API
                List<Neighbour> favoriteNeighbours = neighbourApiService.getFavoriteNeighbours();
                if (isFavorite) {
                    favoriteNeighbours.add(neighbour);
                } else {
                    favoriteNeighbours.remove(neighbour);
                }
                neighbourApiService.setFavoriteNeighbours(favoriteNeighbours);
            }
        });

        // Initialisation du service de l'API
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

// ajouter l'écouteur de clic sur le bouton favori -> utiliser le setfavorite