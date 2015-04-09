package fr.amu.vingtkbieres.vingtkbieresdansnosverres.recherche;

/**
 * Created by Hikyon on 12/03/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Beer;

/**
 * Created by Hikyon on 11/03/2015.
 */

public class ResultatRechercheAdapter extends ArrayAdapter<Beer> {

    int layoutRessourceId;
    List<Beer> data;

    public ResultatRechercheAdapter(Context context, int resource, List<Beer> objects) {
        super(context, resource, objects);
        this.layoutRessourceId = resource;
        this.data = objects;
    }

    // permet de ne pas répéter les éléments!
    @Override
    public int getViewTypeCount() {
        return getCount();
    } // getViewTypeCount()

    @Override
    public int getItemViewType(int position) {
        return position;
    } // getItemViewType()

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );//((Activity)getContext()).getLayoutInflater();

            row = inflater.inflate(layoutRessourceId, null);

            ImageView imageView1 = (ImageView)row.findViewById(R.id.img);
            imageView1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.geoloc));

            TextView textViewNomBiere = (TextView)row.findViewById(R.id.nomBiere);
            textViewNomBiere.setText(data.get(position).name);

            TextView textViewStyleBiere = (TextView)row.findViewById(R.id.styleBiere);
            String style = data.get(position).style;
            if (style == null)
                style = "Inconnu";
            textViewStyleBiere.setText(style);

            String country = data.get(position).country.toLowerCase();
            int resID = getContext().getResources().getIdentifier(
                    country , "drawable", getContext().getPackageName());

            if(resID == 0)
            {
                resID = getContext().getResources().getIdentifier(
                        "unknown" , "drawable", getContext().getPackageName());
            }

            ImageView imageView = (ImageView)row.findViewById(R.id.drapeau);
            imageView.setImageDrawable(getContext().getResources().getDrawable(resID));
        }
        return row;
    }//getView()
}