package fr.amu.vingtkbieres.vingtkbieresdansnosverres.secondaire;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Leirone on 09/04/2015.
 */
public class MyListener implements View.OnClickListener {
    private String desc;
    private Context c;

    public MyListener (String desc, Context c)
    {
        super ();
        this.desc = desc;
        this.c = c;
    }

    @Override
    public void onClick(View v) {
        Toast toast = Toast.makeText(c, desc, Toast.LENGTH_SHORT);
        toast.show();
    }
}
