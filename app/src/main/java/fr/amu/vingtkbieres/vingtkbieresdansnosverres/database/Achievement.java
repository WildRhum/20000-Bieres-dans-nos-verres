package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.secondaire.MyListener;

/**
 * Created by Leirone on 26/03/2015.
 */
public class Achievement extends LinearLayout {
    private TextView name;
    private ProgressBar progess;
	private int state;

	public static final int NOT_STARTED = 0;
	public static final int IN_PROGRESS = 1;
	public static final int ACHIEVED = 2;

	public Achievement (Context c, String name, int toReach, int current, String desc) {
        super(c);
        setOrientation(VERTICAL);
        this.name = new TextView (c);
        this.setOnClickListener(new MyListener(desc, getContext()));
        this.progess = new ProgressBar (c, null, android.R.attr.progressBarStyleHorizontal);
		this.name.setText(name);
        this.progess.setMax(toReach);
        this.progess.setProgress(current);

		if (current == 0)
			state = NOT_STARTED;
		else if (current == toReach)
			state = ACHIEVED;
		else
			state = IN_PROGRESS;

        addView(this.name);
        addView(this.progess);
	}

    public int getState () { return state; }
}
