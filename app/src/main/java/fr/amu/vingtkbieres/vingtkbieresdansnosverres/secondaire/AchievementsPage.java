package fr.amu.vingtkbieres.vingtkbieresdansnosverres.secondaire;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Achievement;
import fr.amu.vingtkbieres.vingtkbieresdansnosverres.database.Database;

public class AchievementsPage extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.page_achievements, container, false);

        List<Achievement> achievements = null;
        try {
            Database db = new Database("jdbc:mysql://addresse_bdd/nom_bdd", "", "");
            achievements = db.loadAchievements();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LinearLayout layout1 = (LinearLayout) v.findViewById(R.id.unlockedLayout);
        LinearLayout layout2 = (LinearLayout) v.findViewById(R.id.inProgressLayout);
        LinearLayout layout3 = (LinearLayout) v.findViewById(R.id.lockedLayout);
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

		return v;
	}
}