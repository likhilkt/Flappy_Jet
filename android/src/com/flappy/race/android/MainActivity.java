package com.flappy.race.android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.flappy.race.Utils.Constants;

public class MainActivity extends Activity/*extends BaseGameActivity*/ {
    long hs, ld;
    TextView t2, t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        t2 = (TextView) findViewById(R.id.textView2);
        t4 = (TextView) findViewById(R.id.textView4);

        hs = 0;
        if (getIntent().hasExtra(Constants.HSCORE)) {
            hs = getIntent().getLongExtra(Constants.HSCORE, 0);
            ld = getIntent().getLongExtra(Constants.HDIST, 0);

            t2.setText("" + hs);
            t4.setText("" + ld);
        }
    }

//    @Override
//    public void onSignInFailed() {
//
//    }
//
//    @Override
//    public void onSignInSucceeded() {
//
//        //findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//
//        //    @Override
//        //    public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                //if(isNetworkAvailable()){
//                 //   int hs=prefs.getInt("likhil.quick.hs", 0);
//
////                Games.Leaderboards.submitScore(getApiClient(),
////                        getString(R.string.number_guesses_leaderboard),
////                        hs);
////                Games.Leaderboards.submitScore(getApiClient(),
////                        getString(R.string.number_guesses_leaderboard_ld),
////                        ld);
////
////                Toast.makeText(getApplicationContext(), "Score Updated", Toast.LENGTH_LONG).show();
////
////                //}
////                //else{
////                //    Toast.makeText(getApplicationContext(), "No Internet Connection..\nPlease enable data", Toast.LENGTH_LONG).show();
////                //}
////       //     }
////       // });
////
////        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View arg0) {
////                // TODO Auto-generated method stub
////
////                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
////                                getApiClient(), getString(R.string.number_guesses_leaderboard_ld)),
////                        2);
////
////            }
////        });
////
////        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View arg0) {
////                // TODO Auto-generated method stub
////
////                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
////                                getApiClient(), getString(R.string.number_guesses_leaderboard)),
////                        2);
////
////            }
////        });
////
////
////
//
//    }
}
