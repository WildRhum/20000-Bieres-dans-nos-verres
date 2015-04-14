package fr.amu.vingtkbieres.vingtkbieresdansnosverres.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.mainPage;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.secondaire.ActiviteProfil;

/**
 * Created by legeek on 13/04/15.
 */
public class GlobalMenu {
    public static Boolean handle( Activity context, MenuItem item ){

        Intent intent = null;
        switch ( item.getItemId() ){
            case R.id.global_section_1:
                if( !context.getLocalClassName().equalsIgnoreCase( "mainPage" ) )
                    intent = new Intent( context, mainPage.class );

                break;

            case R.id.global_section_2:
                if( !context.getLocalClassName().equalsIgnoreCase( "secondaire.ActiviteProfile" ) )
                    intent = new Intent( context, ActiviteProfil.class );
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
                Toast.makeText( context, "A bientôt !", Toast.LENGTH_SHORT).show();
                System.exit( 0 );
                break;

            default:
                return false;
        }

        if( intent != null ) {
            context.startActivity(intent);
            context.finish();
        }

        return true;
    }
}
