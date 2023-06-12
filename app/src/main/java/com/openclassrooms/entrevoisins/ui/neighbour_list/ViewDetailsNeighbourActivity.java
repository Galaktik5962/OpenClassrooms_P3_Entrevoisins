package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;

public class ViewDetailsNeighbourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_neighbour_details);

        //récupération de l'intent
        Intent intent = getIntent();

        //extraction des données chargées dans l'intent si elles sont non nulles
        if (intent != null) {
            long id = intent.getLongExtra("id", -1);
            String name = intent.getStringExtra("name");
            String avatarUrl = intent.getStringExtra("avatarUrl");
            String address = intent.getStringExtra("address");
            String phoneNumber = intent.getStringExtra("phoneNumber");
            String aboutMe = intent.getStringExtra("aboutMe");

            // Afficher les données du voisin récupérées ci-dessus

            ImageView avatarUrlImageView = findViewById(R.id.avatarUrl);
            Glide.with(this)
                    .load(avatarUrl)
                    .into(avatarUrlImageView);

            TextView name1TextView = findViewById(R.id.name_1);
            name1TextView.setText(name);

            TextView name2TextView = findViewById(R.id.name_2);
            name2TextView.setText(name);

            TextView addressTextView = findViewById(R.id.address);
            addressTextView.setText(address);

            TextView phoneNumberTextView = findViewById(R.id.phoneNumber);
            phoneNumberTextView.setText(phoneNumber);

            TextView socialNetworkTextview = findViewById(R.id.socialNetwork);
            //associer le nom du voisin à l'url facebook
            String facebookUrl = "https://www.facebook.com/%s";
            String facebookUrlWithNeighbourName = String.format(facebookUrl, name);
            socialNetworkTextview.setText(facebookUrlWithNeighbourName);

            TextView aboutMeTitleTextView = findViewById(R.id.aboutMeTitle);
            aboutMeTitleTextView.setText("A propos de moi");

            TextView aboutMeTextView = findViewById(R.id.aboutMe);
            aboutMeTextView.setText(aboutMe);
        }

        //configuration du bouton de retour
        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appel à la méthode finish pour revenir à l'activité précédente
                finish();
            }
        });
    }
}