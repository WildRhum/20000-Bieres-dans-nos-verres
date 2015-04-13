package fr.amu.vingtkbieres.vingtkbieresdansnosverres.recherche;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Beer;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Database;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.JSONDataException;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Style;

public class ResultatRechercheActivity extends Activity {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    protected ListView listBiere = null;
    List<Beer> labelItems = new ArrayList<Beer>();
    ResultatRechercheAdapter adapter;
    ArrayList<Style> styles = new ArrayList<Style>();

    // permet de réaliser la barre d'attente
    private ProgressDialog mProgressDialog;

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Chargement des bières ...");
                mProgressDialog.setCancelable(true);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    private class asyncDbTest extends AsyncTask< Void, Void, Void > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<Beer> tmpBeer;
            try {
                for(int i = 0; i < styles.size(); ++i)
                {
                    // place les bières dans un arrayList temporaire
                    tmpBeer = Database.searchBeerByStyle(styles.get(i).id, 0, 15);

                    for(int j = 0; j < tmpBeer.size(); ++j) {
                        // ajoute les valeurs petit à petit dans la liste
                        labelItems.add(tmpBeer.get(j));
                    }

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
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            if(labelItems.isEmpty())
            {
                Toast.makeText(getBaseContext(), "Il n'y à pas de bières associées a cette recherche", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                adapter = new ResultatRechercheAdapter(getBaseContext(),
                        R.layout.list_biere, labelItems);

                // Place les éléments
                //Collections.sort(labelItems, String.CASE_INSENSITIVE_ORDER);
                listBiere.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_resultat_recherche);
            listBiere = (ListView) findViewById(R.id.listBiere);
            listBiere.setFastScrollEnabled(true);

            styles = this.getIntent().getParcelableArrayListExtra("style");

            for (int i =0; i < styles.size(); ++i)
                System.out.println("Valeur: " + styles.get(i).text);

            if (styles.isEmpty())
                System.out.println("Styles vide Resultat");

            asyncDbTest asyncTask = new asyncDbTest();
            asyncTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listBiere.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent detailBiere = new Intent(ResultatRechercheActivity.this, DetailBiere.class);
                Beer biere = (Beer) adapter.getItemAtPosition(position);

                detailBiere.putExtra("nomBiere", biere);

                startActivity(detailBiere);
                onPause();
            };
        });//onCreate()
    }
}


