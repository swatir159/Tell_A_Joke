package com.udacity.gradle.builditbigger;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;



public class MainActivity extends AppCompatActivity {

    //private Joker myJoker;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    InterstitialAd mInterstitialAd;
    boolean mIsAppPaidVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIsAppPaidVersion = getResources().getBoolean(R.bool.is_paid_version);
        Log.d(LOG_TAG, "Am I the paid version?: " + mIsAppPaidVersion);

        if (!mIsAppPaidVersion) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId( getResources().getString(R.string.interstitial_ad_unit_id));  //"ca-app-pub-3940256099942544/1033173712"
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    getAndDisplayJoke();
                }
            });
            requestNewInterstitial();
        }
    }

    private void getAndDisplayJoke() {

        new EndpointsAsyncTask(this, null).execute();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                              .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                              .build();

        mInterstitialAd.loadAd(adRequest);
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

    public void tellJoke(View view) {
        if (!mIsAppPaidVersion && mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        else {
            getAndDisplayJoke();
        }

    }



}
