package fr.amu.vingtkbieres.vingtkbieresdansnosverres.recherche;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Beer;

public class DetailBiere extends ActionBarActivity {
    Beer biere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_biere);

        biere = getIntent().getExtras().getParcelable("nomBiere");

        TextView nom = (TextView) findViewById(R.id.textViewName);
        nom.setText(biere.name);

        TextView style = (TextView) findViewById(R.id.textViewStyle);
        style.setText(biere.style);

        TextView overallScore = (TextView) findViewById(R.id.textViewOverallScore);
        String ovScore = "Score général : ";
        if (biere.overallScore < 0)
            ovScore += "Non connu";
        else
            ovScore += String.valueOf(biere.overallScore);
        overallScore.setText(ovScore);

        TextView styleScore = (TextView) findViewById(R.id.textViewStyleScore);
        String styScore = "Score par style : ";
        if (biere.styleScore < 0)
            styScore += "Non connu";
        else
            styScore += String.valueOf(biere.styleScore);
        styleScore.setText(styScore);

        TextView abv = (TextView) findViewById(R.id.textViewAbv);
        String abvBiere = "Degré d'alcool : ";
        if (biere.abv < 0)
            abvBiere += "Non connu";
        else
            abvBiere += Float.toString(biere.abv);
        abv.setText(abvBiere);

        TextView location = (TextView) findViewById(R.id.textViewLocation);
        location.setText(biere.address +  " " + biere.country);

        String country = biere.country.replaceAll(" ", "_").toLowerCase();
        int resID = this.getResources().getIdentifier(
                country , "drawable", this.getPackageName());

        if(resID == 0)
        {
            resID = this.getResources().getIdentifier(
                    "unknown" , "drawable", this.getPackageName());
        }

        ImageView flagDetail = (ImageView) findViewById(R.id.imageDetailFlag);
        flagDetail.setImageDrawable(this.getResources().getDrawable(resID));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_biere, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
