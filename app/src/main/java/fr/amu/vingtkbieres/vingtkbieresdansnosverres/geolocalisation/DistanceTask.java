package fr.amu.vingtkbieres.vingtkbieresdansnosverres.geolocalisation;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;

public class DistanceTask extends GoogleRequestAPITask {

    private Context context;
    private int distance;
    private MyMarker destinationPosition;

    public DistanceTask(Location myLocation, MyMarker desitinationPosition) {
        super(myLocation, desitinationPosition);
        this.context = desitinationPosition.getContext();
        distance = 0;
        this.destinationPosition = desitinationPosition;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Document document = super.request();

        Element elementLeg = (Element) document.getElementsByTagName("leg").item(0);
        NodeList nodeList = elementLeg.getElementsByTagName("step");
        int size = nodeList.getLength();

        for(int i = 0; i < size; ++i) {
            Node nodeStep = nodeList.item(i);

            if(nodeStep.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeStep;
                Element distanceVal = (Element) element.getElementsByTagName("distance").item(0);
                NodeList value = distanceVal.getElementsByTagName("value");
                distance = distance + Integer.parseInt(value.item(0).getTextContent());
            }
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(!result) {
            Toast.makeText(context, R.string.directionFounded, Toast.LENGTH_LONG).show();
        }

        String str = "Distance = " + distance;
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();

        destinationPosition.setSnippet("Distance = " + distance);
    }
}
