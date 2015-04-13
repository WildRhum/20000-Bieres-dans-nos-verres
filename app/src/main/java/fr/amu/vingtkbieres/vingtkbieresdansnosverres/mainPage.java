package fr.amu.vingtkbieres.vingtkbieresdansnosverres;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.geolocalisation.MapsActivity;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.recherche.RechercheActivity;

public class mainPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ImageButton boutonGeoloc = (ImageButton) findViewById(R.id.imageBoutonGeoloc);
        boutonGeoloc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mainPage.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton boutonRecherche = (ImageButton) findViewById(R.id.imageBoutonRecherche);
        boutonRecherche.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mainPage.this, RechercheActivity.class);
                startActivity(intent);
            }
        });

        TextView note = (TextView) findViewById(R.id.note);
        ArrayList<String> quote = new ArrayList<>();
        quote.add("Toutes les bières ne sont pas faites avec du houblon!");
        quote.add("La lumière est dangereuse pour vos bière! Gardez les a l'abri!");
        quote.add("Les travailleurs des pyramides égyptiennes ont été payés avec de la bière: 1 gallon (4L) par jour!");
        quote.add("La plus ancienne recette de bière  connue a plus de 4000 ans, créée par les Sumériens");
        quote.add("Au Moyen Age, la bière était plus consommée que l'eau, alcool la rendait plus sûre");
        quote.add("Les limaces aussi aiment la bière!");
        quote.add("Au Moyen Age, la bière était plus consommée que l'eau, alcool la rendait plus sûre");
        quote.add("La bière n'était pas considéré comme une boisson alcolisée en russie jusqu'en 2013!");

        // génère une fact sur la bière aléatoirement a la création de la page principale et l'affiche
        Random random = new Random();
        int randomInt = random.nextInt(quote.size() - 1);
        note.setText(quote.get(randomInt));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }
}
