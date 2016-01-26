package in.doomers.rahul.ufo;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {

     private MediaPlayer mediaplayer;
    private GamePanel gamepanel;
    private AdView adView;
    private static final String TAG="ADDS  WAIT ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SurfaceView gameView = new SurfaceView(this);
        Log.e(TAG,"ADDS");

        // Create and load the AdView.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-3522504263895158/5110209827");
        adView.setAdSize(AdSize.SMART_BANNER);

        // Create a RelativeLayout as the main layout and add the gameView.
        RelativeLayout mainLayout = new RelativeLayout(this);
        mainLayout.addView(gameView);

        // Add adView to the bottom of the screen.
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_BASELINE);
        mainLayout.addView(adView, adParams);

        // Set the RelativeLayout as the main layout.
        setContentView(mainLayout);
        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.homescreen);
        showBanner();

    }
    public void newgame (View view){
        gamepanel =new GamePanel(this);
    setContentView(gamepanel);}

    private void showBanner() {
        adView.setVisibility(View.VISIBLE);

        adView.loadAd(new AdRequest.Builder().addTestDevice("94FEC83571958B81FEE7403127A5F653").build());


    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}