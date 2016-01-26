package in.doomers.rahul.ufo;


import android.util.Log;

/**
 * Created by Rahul on 10/18/2015.
 */
public class HighScore {
    private static final String TAG = "Finally not inheritance";
    public long Highscore;
    public HighScore(long Highscore) {
        this.Highscore = Highscore;
        Log.e(TAG,"SIGN OF RELIEF"+Highscore);
    }

}