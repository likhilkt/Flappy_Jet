package com.flappy.race.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.flappy.race.MyGdxGame;
import com.flappy.race.Utils.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {
    protected AdView adView;
    AlertDialog alertDialog;
    protected InterstitialAd interstitialAd;
    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0,
            HIDE = 3, SHOW = 4, DIA = 10;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_ADS: {
                    //adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS: {
                    //adView.setVisibility(View.GONE);
                    break;
                }
                case SHOW: {
                    //if(interstitialAd.isLoaded())
                    //	interstitialAd.show();
                    break;
                }
                case DIA: {
                    alertDialog.show();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		/*AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = true;
		config.useAccelerometer=true;
		config.useWakelock=true;
		config.touchSleepTime=16;
		config.maxSimultaneousSounds = 4;

		//config.touchSleepTime=30;
		initialize(new MyGdxGame(), config);
		*/

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = true;
        config.useAccelerometer = true;
        config.useWakelock = true;
        config.touchSleepTime = 16;
        config.maxSimultaneousSounds = 4;

        RelativeLayout layout = new RelativeLayout(this);
        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        // Create the libgdx View
        View gameView = initializeForView(new MyGdxGame(this), config);

        // Create and setup the AdMob view
//		adView = new AdView(this);
//		adView.setAdSize(AdSize.BANNER);
//		adView.setAdUnitId("ADUNIT ID"); // Put in your secret key here
//
//		AdRequest adRequest = new AdRequest.Builder().build();
//				//.addTestDevice("test deviCE ID").build();
//
//		adView.loadAd(adRequest);

        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //adParams.addRule(RelativeLayout.ALIGN_P);

        //layout.addView(adView, adParams);

        // Hook it all up
        setContentView(layout);

//		interstitialAd = new InterstitialAd(this);
//		interstitialAd.setAdUnitId("AD UNIT ID");
//		requestNewInterstitial();
//		interstitialAd.setAdListener(new AdListener() {
//			@Override
//			public void onAdLoaded() {
//				super.onAdLoaded();
//				//interstitialAd.show();
//			}
//		});
        alertDialog = createAlertDialog();


    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("CC46B5B09D9119D7E86DFBC652368962")
                .build();

        interstitialAd.loadAd(adRequest);
    }


    @Override
    public void showAds(boolean show) {
        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }

    @Override
    public void showInt(boolean show) {
        handler.sendEmptyMessage(show ? SHOW : HIDE);
    }

    @Override
    public void share() {
        String shareBody = "mPassBook\n\nFound an amazing GAME called  flappy jet in Google Play\n" +
                "\nClick on https://play.google.com/store/apps/details?id=com.flappy.race.android";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(sharingIntent);
    }

    @Override
    public void onStartSomeActivity(long someParameter, long somePara2) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.HSCORE, someParameter);
        intent.putExtra(Constants.HDIST, somePara2);

        // do whatever you want with the supplied parameters.
        //if (someParameter == 42) {
        //	intent.putExtra(MY_EXTRA, someOtherParameter);
        //}
        startActivity(intent);

    }

    @Override
    public void showCredits(boolean show) {
        handler.sendEmptyMessage(DIA);
    }


    public AlertDialog createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AndroidLauncher.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.credits, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                });
        return builder.create();
    }


    @Override
    public void fbLike() {
        Uri uri = Uri.parse("https://www.facebook.com/RabbitFoot21");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
