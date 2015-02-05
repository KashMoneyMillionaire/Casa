package com.example.kash.casa.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.TextView;

import com.example.kash.casa.Activities.LoginRegisterActivity;
import com.example.kash.casa.R;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000,
            WINK_TIME_OUT = 200,
            SMILE = 1, WINK = 2;
    Timer timer = new Timer();
    Handler handler;
    TextView x;
    Activity activity;

    Callback callback = new Callback() {
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == WINK){
                x.setText(";)");
            } else {
                x.setText(":)");

                Intent i = new Intent(activity, LoginRegisterActivity.class);
                startActivity(i);

                finish();
            }

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        x = (TextView) findViewById(R.id.splash_smile);
        activity = this;

        handler = new Handler(callback);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.schedule(new SetText(WINK), 0);
                timer.schedule(new SetText(SMILE), WINK_TIME_OUT);
            }
        }, SPLASH_TIME_OUT);
    }

    class SetText extends TimerTask {
        private int arg1;

        public SetText(int text) {
            this.arg1 = text;
        }

        public void run() {
            Message message = new Message();
            message.arg1 = arg1;
            handler.sendMessage(message);
        }
    }
}
