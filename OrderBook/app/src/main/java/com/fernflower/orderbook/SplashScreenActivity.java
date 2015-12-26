package com.fernflower.orderbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fernflower.orderbook.activitys.OrderNameListActivity;
import com.fernflower.orderbook.datebase.DbLayer;


public class SplashScreenActivity extends Activity {


    private static final int SPLASH_DELAY=4600;
    private  RelativeLayout splashBackground;
    private ImageView splashName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        DbLayer.getInstance(this).readSettings();
        init();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                Intent orderNameList = new Intent(SplashScreenActivity.this, OrderNameListActivity.class);
                startActivity(orderNameList);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DELAY);



    }


    public void init(){
        splashBackground= (RelativeLayout)findViewById(R.id.splash_background_relative_layout);
        splashName=(ImageView)findViewById(R.id.splash_name);

        animateView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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

    private void animateView(){

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.fade);
        splashBackground.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        splashName.startAnimation(anim);

    }
}
