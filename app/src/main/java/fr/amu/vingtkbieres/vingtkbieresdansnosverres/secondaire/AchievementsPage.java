package fr.amu.vingtkbieres.vingtkbieresdansnosverres.secondaire;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.List;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Achievement;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Database;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.JSONDataException;

public class AchievementsPage extends Fragment {

    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;

    public class AchievementAsyncTask extends AsyncTask<Void, Void, List<Achievement>> {

        @Override
        protected List<Achievement> doInBackground(Void... params) {
            List<Achievement> achievements = null;
            try {
                achievements = Database.loadAchievements(4, getActivity());
            } catch (JSONDataException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return achievements;
        }

        @Override
        protected void onPostExecute(List<Achievement> achievements) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,10,0);
            if (achievements == null) {
                Achievement a = new Achievement (getActivity(), "Néophyte", 5, 0, "YOLO");
                Achievement b = new Achievement (getActivity(), "Drinker", 10, 0, "SWAG");
                Achievement c = new Achievement (getActivity(), "Newbe", 1, 1, "PAPA DELTA");
                Achievement d = new Achievement (getActivity(), "Traveler", 5, 3, "MAXIME SENT LA M****");

                layout1.addView(c, layoutParams);
                layout2.addView(d, layoutParams);
                layout2.addView(a, layoutParams);
                layout3.addView(b, layoutParams);
            }
            else
            {
                for (int i = 0 ; i < achievements.size() ; i++)
                {
                    switch (achievements.get(i).getState())
                    {
                        case Achievement.ACHIEVED:
                            layout1.addView(achievements.get(i), layoutParams);
                            break;
                        case Achievement.IN_PROGRESS:
                            layout2.addView(achievements.get(i), layoutParams);
                            break;
                        case Achievement.NOT_STARTED:
                            layout3.addView(achievements.get(i), layoutParams);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.page_achievements, container, false);

        layout1 = (LinearLayout) v.findViewById(R.id.unlockedLayout);
        layout2 = (LinearLayout) v.findViewById(R.id.inProgressLayout);
        layout3 = (LinearLayout) v.findViewById(R.id.lockedLayout);

        new AchievementAsyncTask().execute();

		return v;
	}
}