package fr.amu.vingtkbieres.vingtkbieresdansnosverres.secondaire;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;

public class PageAchievements extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.page_achievements, container, false);
	}
}