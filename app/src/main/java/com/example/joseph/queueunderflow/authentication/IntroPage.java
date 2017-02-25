package com.example.joseph.queueunderflow.authentication;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Window;

import com.github.paolorotolo.appintro.AppIntro;

public class IntroPage extends AppIntro {

    firstFragment f1 = new firstFragment();
    secondFragment f2 = new secondFragment();
    thirdFragment f3 = new thirdFragment();
    finalFragment finalFrag = new finalFragment();

    Window window;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          window = getWindow();

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.



        addSlide(f1);
        addSlide(f2);
        addSlide(f3);
        addSlide(finalFrag);




        // OPTIONAL METHODS
        // Override bar/separator color.

        /*
        setBarColor(Color.parseColor("#77dd77"));
        setSeparatorColor(Color.parseColor("#77dd77"));

        */

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.

        /*
        setVibrate(true);
        setVibrateIntensity(30);

        */
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }
}