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
    List<Beer> labelItems;
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
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
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
            try {
                int progress;
                for(int i = 0; i < styles.size(); ++i)
                {
                    labelItems = Database.searchBeerByStyle(styles.get(0).id, 0, 15);
                    progress = (int) ((i / (float)styles.size()) * 100);
                    onProgressUpdate(progress);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (JSONDataException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            if(labelItems.isEmpty())
            {
                Toast.makeText(getBaseContext(), "Il n'y à pas de bières associées a cette recherche", Toast.LENGTH_SHORT).show();
            }
            adapter = new ResultatRechercheAdapter(getBaseContext(),
                    R.layout.list_biere, labelItems);

            // Place les éléments
            //Collections.sort(labelItems, String.CASE_INSENSITIVE_ORDER);
            listBiere.setAdapter(adapter);
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

                detailBiere.putExtra("nomBiere", biere.name);

                startActivity(detailBiere);
                onPause();
            };
        });//onCreate()
    }
}


