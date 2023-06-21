package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import com.openclassrooms.entrevoisins.databinding.ViewNeighbourDetailsBinding;


public class ViewDetailsNeighbourActivity extends AppCompatActivity {

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
    }
}