package fr.amu.vingtkbieres.vingtkbieresdansnosverres;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.geolocalisation.MapsActivity;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.recherche.RechercheActivity;

public class mainPage extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        SharedPreferences runCheck = getSharedPreferences("hasRunBefore", 0); // charge les préférences
        Boolean hasRun = runCheck.getBoolean("hasRun", false); // récupére la valeur de hasRun
        if (!hasRun) {
            // L'application n'a pas été lancée, ou il n'y a plus de données
            SharedPreferences settings = getSharedPreferences("hasRunBefore", 0);
            SharedPreferences.Editor edit = settings.edit();
            edit.putBoolean("hasRun", true); // maintenant, elle a été lancée
            edit.commit();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder.setTitle("Bienvenue dans l'application 20K Biere!")
                    .setMessage("Pour utiliser l'application, cliquez sur les images!")
                    .setIcon(R.drawable.ic_launcher)
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();           
        }
        // L'application a déjà été lancée, alors utilisation normale

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_main);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer_main,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.accueil);
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                mTitle = getString(R.string.title_section4);
                break;
            case 6:
                System.exit(0);
        }
        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
            ImageButton boutonGeoloc = (ImageButton) rootView.findViewById(R.id.imageBoutonGeoloc);
            boutonGeoloc.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    startActivity(intent);
                }
            });

            ImageButton boutonRecherche = (ImageButton) rootView.findViewById(R.id.imageBoutonRecherche);
            boutonRecherche.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RechercheActivity.class);
                    startActivity(intent);
                }
            });

            TextView note = (TextView) rootView.findViewById(R.id.note);
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
            int randomInt = random.nextInt(quote.size()-1);
            note.setText(quote.get(randomInt));

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((mainPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
