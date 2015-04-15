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
        overallScore.setText(String.valueOf(biere.overallScore));

        TextView styleScore = (TextView) findViewById(R.id.textViewStyleScore);
        styleScore.setText(String.valueOf(biere.styleScore));

        TextView abv = (TextView) findViewById(R.id.textViewAbv);
        abv.setText(Float.toString(biere.abv));

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
