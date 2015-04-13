package fr.amu.vingtkbieres.vingtkbieresdansnosverres.menu;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;

/**
 * Created by legeek on 13/04/15.
 */
public class GlobalMenu {
    public static Boolean handle( Context context, MenuItem item ){
        switch ( item.getItemId() ){
            case R.id.global_section_1:
                Toast.makeText( context, "Accueil", Toast.LENGTH_SHORT).show();
                break;

            case R.id.global_section_2:
                Toast.makeText( context, "Mes Bières", Toast.LENGTH_SHORT).show();
                break;

            case R.id.global_section_3:
                Toast.makeText( context, "Mon profil", Toast.LENGTH_SHORT).show();
                break;

            case R.id.global_section_4:
                Toast.makeText( context, "Aide", Toast.LENGTH_SHORT).show();
                break;

            case R.id.global_section_5:
                Toast.makeText( context, "Déconnexion", Toast.LENGTH_SHORT).show();
                break;

            case R.id.global_section_6:
                System.exit( 0 );
                break;

            default:
                return false;
        }

        return true;
    }
}
