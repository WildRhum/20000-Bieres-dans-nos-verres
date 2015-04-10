package fr.amu.vingtkbieres.vingtkbieresdansnosverres.secondaire;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.List;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Achievement;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Database;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.JSONDataException;

public class AchievementsPage extends Fragment {

    private GridLayout layout1;
    private GridLayout layout2;
    private GridLayout layout3;

    public class AchievementAsyncTask extends AsyncTask<Void, Void, List<Achievement>> {

        private Context context;

        public AchievementAsyncTask (Context c)
        {
            context = c;
        }
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
            if (achievements != null) {
                layout1.setPadding(10, 10, 0, 0);
                layout2.setPadding(10, 10, 0, 0);
                layout3.setPadding(10, 10, 0, 0);

                for (int i = 0 ; i < achievements.size() ; i++)
                {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 50);
                    achievements.get(i).getProgess().setLayoutParams(params);
                    achievements.get(i).getProgess().setPadding(50, 0, 0, 0);
                    switch (achievements.get(i).getState())
                    {
                        case Achievement.ACHIEVED:
                            layout1.addView(achievements.get(i).getName());
                            layout1.addView(achievements.get(i).getProgess());
                            break;
                        case Achievement.IN_PROGRESS:
                            layout2.addView(achievements.get(i).getName());
                            layout2.addView(achievements.get(i).getProgess());
                            break;
                        case Achievement.NOT_STARTED:
                            layout3.addView(achievements.get(i).getName());
                            layout3.addView(achievements.get(i).getProgess());
                            break;
                        default:
                            break;
                    }
                }
            }
            else
                Toast.makeText(context, context.getString(R.string.internetProblem), Toast.LENGTH_LONG).show();
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.page_achievements, container, false);

        layout1 = (GridLayout) v.findViewById(R.id.unlockedLayout);
        layout2 = (GridLayout) v.findViewById(R.id.inProgressLayout);
        layout3 = (GridLayout) v.findViewById(R.id.lockedLayout);

        new AchievementAsyncTask(getActivity()).execute();

		return v;
	}
}