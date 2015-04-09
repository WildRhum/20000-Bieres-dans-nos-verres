package fr.amu.vingtkbieres.vingtkbieresdansnosverres.recherche;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import org.json.JSONException;

import java.util.ArrayList;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Database;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.JSONDataException;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Style;

public class RechercheActivity extends ActionBarActivity {
    ArrayList<Style> arrayStyle = new ArrayList<>();
    ArrayList<CheckBox> checkBox;

    private class asyncDbTest extends AsyncTask< Void, Void, Void >{
        ArrayList<Style> styles;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                styles = new ArrayList<>( Database.getAllStyle() );
                for( Style s : styles ){
                    System.out.println( s.text );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (JSONDataException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            arrayStyle = styles;
            creerCheckBox();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        asyncDbTest asyncTask = new asyncDbTest();
        asyncTask.execute();

//        for(int i = 0; i < arrayStyle.size(); ++i)
//        {
//            System.out.println("TEST VALEUR :" + arrayStyle.get(i).text);
//        }
//
//        if (arrayStyle.isEmpty())
//            System.out.println("ArrayStyle vide!");

        Button b = (Button) findViewById(R.id.boutonRecherche);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Style> styleChoisi = new ArrayList<Style>();

                for(int i = 0; i < arrayStyle.size(); ++i)
                {
                    if(!arrayStyle.get(i).text.equals("null"))
                    {
                        if(checkBox.get(i).isChecked()){
                            styleChoisi.add(arrayStyle.get(i));
                        }
                    }
                }

                Intent intent = new Intent(RechercheActivity.this, ResultatRechercheActivity.class);
                intent.putParcelableArrayListExtra("style", styleChoisi);
                startActivity(intent);
            }
        });
    }

    private void creerCheckBox() {
        checkBox = new ArrayList<>();
        LinearLayout ll = (LinearLayout) findViewById(R.id.rechercheBiere);

        // creation des checkBox
        for(int i = 0; i < arrayStyle.size(); i++){
            CheckBox newCheckBox = new CheckBox(this);
            newCheckBox.setId(i);
            newCheckBox.setText(arrayStyle.get(i).text);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(100,0,0,0);
            newCheckBox.setLayoutParams(layoutParams);

            checkBox.add(newCheckBox);

            // ajout des checkBox au layout
            ll.addView(newCheckBox);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recherche, menu);
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
