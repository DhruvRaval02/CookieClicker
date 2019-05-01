package com.example.dhruv.cookieclicker;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView cookieMonster;
    ImageView imageViewInCode;
    ImageView oscarViewInCode;
    TextView score;
    static int cookieClickCounter = 0;
    static int elmoProduction = 0;
    static int oscarProduction = 0;
    ConstraintLayout layout;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    int elmoAmount = 0;
    int oscarAmount = 0;
    int elmoCost = 50;
    int oscarCost = 100;
    Thread elmoThread;
    Boolean runThread = true;
    TextView elmoDefault;
    TextView cps;
    TextView oscarDefault;
    int upgradeAmountElmo= 0;
    int upgradeAmountOscar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookieMonster = findViewById(R.id.ID_cookie);
        score = findViewById(R.id.ID_score);
        layout = findViewById(R.id.ID_layout);
        linearLayout = findViewById(R.id.ID_linearLayout);
        linearLayout2 = findViewById(R.id.ID_linearLayout2);
        elmoDefault = findViewById(R.id.ID_elmoDefault);
        oscarDefault = findViewById(R.id.ID_oscarDefault);
        cps = findViewById(R.id.ID_cps);

        final ScaleAnimation cookieAnimation = new ScaleAnimation(0.85f, 1f, 0.85f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        cookieAnimation.setDuration(400);
        final ScaleAnimation elmoAnimationIn = new ScaleAnimation(0.25f, 1f, 0.25f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        elmoAnimationIn.setDuration(400);
        final ScaleAnimation elmoAnimationOut = new ScaleAnimation(.75f, 0f, .75f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        elmoAnimationOut.setDuration(400);


        cookieMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookieMonster.startAnimation(cookieAnimation);
                addScoreOnClick();
                score.setText(cookieClickCounter + " Cookies");

                final TextView plusOneCode = new TextView(MainActivity.this);
                plusOneCode.setId(View.generateViewId());
                plusOneCode.setText("+1");
                plusOneCode.setTextColor(Color.WHITE);
                plusOneCode.setTextSize(24);

                ConstraintLayout.LayoutParams plusOneParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                plusOneCode.setLayoutParams(plusOneParams);

                layout.addView(plusOneCode);

                ConstraintSet constraintSetPlusOne = new ConstraintSet();
                constraintSetPlusOne.clone(layout);

                constraintSetPlusOne.connect(plusOneCode.getId(), constraintSetPlusOne.BOTTOM, cookieMonster.getId(), constraintSetPlusOne.BOTTOM);
                constraintSetPlusOne.connect(plusOneCode.getId(), constraintSetPlusOne.TOP, cookieMonster.getId(), constraintSetPlusOne.TOP);
                constraintSetPlusOne.connect(plusOneCode.getId(), constraintSetPlusOne.LEFT, cookieMonster.getId(), constraintSetPlusOne.LEFT);
                constraintSetPlusOne.connect(plusOneCode.getId(), constraintSetPlusOne.RIGHT, cookieMonster.getId(), constraintSetPlusOne.RIGHT);

                constraintSetPlusOne.setVerticalBias(plusOneCode.getId(), (float)(Math.random()*0.25));
                constraintSetPlusOne.setHorizontalBias(plusOneCode.getId(), (float)(Math.random()*1));

                constraintSetPlusOne.applyTo(layout);

                Animation plusOneMovement = new TranslateAnimation(0f, 0f, 225f, 0f);
                plusOneMovement.setDuration(1000);
                plusOneCode.startAnimation(plusOneMovement);
                plusOneMovement.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        layout.removeView(plusOneCode);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        imageViewInCode = new ImageView(MainActivity.this);
        imageViewInCode.setId(View.generateViewId());
        imageViewInCode.setImageResource(R.drawable.elmo);
        imageViewInCode.setVisibility(View.INVISIBLE);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        imageViewInCode.setLayoutParams(params);

        layout.addView(imageViewInCode);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        constraintSet.connect(imageViewInCode.getId(), constraintSet.BOTTOM, score.getId(), constraintSet.TOP);
        constraintSet.connect(imageViewInCode.getId(), constraintSet.TOP, layout.getId(), constraintSet.TOP);
        constraintSet.connect(imageViewInCode.getId(), constraintSet.LEFT, layout.getId(), constraintSet.LEFT);
        constraintSet.connect(imageViewInCode.getId(), constraintSet.RIGHT, layout.getId(), constraintSet.RIGHT);

        constraintSet.setVerticalBias(imageViewInCode.getId(), 0.25f);
        constraintSet.setHorizontalBias(imageViewInCode.getId(), 0.95f);

        constraintSet.applyTo(layout);

        imageViewInCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeAmountElmo++;
                elmoScore();
                removeScoreWithElmo();
                elmoCost += (elmoProduction * 10);
                score.setText(cookieClickCounter + " Cookies");

                elmoDefault.setVisibility(View.VISIBLE);

                    ImageView elmo = new ImageView(MainActivity.this);
                    elmo.setId(View.generateViewId());
                    elmo.setImageResource(R.drawable.elmosmall);

                    LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    elmo.setLayoutParams(linParams);
                    if(upgradeAmountElmo <= 5) {
                        linearLayout.addView(elmo);
                    }
                    elmo.startAnimation(elmoAnimationIn);

                elmoAmount++;
                elmoDefault.setText("Elmos: " + elmoAmount);
            }
        });

        oscarViewInCode = new ImageView(MainActivity.this);
        oscarViewInCode.setId(View.generateViewId());
        oscarViewInCode.setImageResource(R.drawable.oscar);
        oscarViewInCode.setVisibility(View.INVISIBLE);

        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        oscarViewInCode.setLayoutParams(params2);

        layout.addView(oscarViewInCode);

        ConstraintSet constraintSet2 = new ConstraintSet();
        constraintSet2.clone(layout);

        constraintSet2.connect(oscarViewInCode.getId(), constraintSet.BOTTOM, score.getId(), constraintSet.TOP);
        constraintSet2.connect(oscarViewInCode.getId(), constraintSet.TOP, layout.getId(), constraintSet.TOP);
        constraintSet2.connect(oscarViewInCode.getId(), constraintSet.LEFT, layout.getId(), constraintSet.LEFT);
        constraintSet2.connect(oscarViewInCode.getId(), constraintSet.RIGHT, layout.getId(), constraintSet.RIGHT);

        constraintSet2.setVerticalBias(oscarViewInCode.getId(), 0.25f);
        constraintSet2.setHorizontalBias(oscarViewInCode.getId(), 0.00f);

        constraintSet2.applyTo(layout);

        oscarViewInCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeAmountOscar++;
                oscarScore();
                removeScoreWithOscar();
                oscarCost += (oscarProduction * 10);
                score.setText(cookieClickCounter + " Cookies");

                oscarDefault.setVisibility(View.VISIBLE);

                ImageView oscar = new ImageView(MainActivity.this);
                oscar.setId(View.generateViewId());
                oscar.setImageResource(R.drawable.oscarsmall);

                LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                oscar.setLayoutParams(linParams);

                if(upgradeAmountOscar <= 5) {
                    linearLayout2.addView(oscar);
                }
                oscar.startAnimation(elmoAnimationIn);

                oscarAmount++;
                oscarDefault.setText("Oscars: " + oscarAmount);

            }
        });

        elmoThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(runThread) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(cookieClickCounter >= elmoCost && imageViewInCode.getVisibility() == View.INVISIBLE){
                                imageViewInCode.setVisibility(View.VISIBLE);
                                imageViewInCode.startAnimation(elmoAnimationIn);
                            }
                            else if(cookieClickCounter < elmoCost && imageViewInCode.getVisibility() == View.VISIBLE){
                                imageViewInCode.setVisibility(View.INVISIBLE);
                                imageViewInCode.startAnimation(elmoAnimationOut);
                            }

                            if(cookieClickCounter >= oscarCost && oscarViewInCode.getVisibility() == View.INVISIBLE){
                                oscarViewInCode.setVisibility(View.VISIBLE);
                                oscarViewInCode.startAnimation(elmoAnimationIn);
                            }
                            else if(cookieClickCounter < oscarCost && oscarViewInCode.getVisibility() == View.VISIBLE){
                                oscarViewInCode.setVisibility(View.INVISIBLE);
                                oscarViewInCode.startAnimation(elmoAnimationOut);
                            }
                            updateGraphics();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    cookieClickCounter += elmoProduction;
                    cookieClickCounter += oscarProduction;
                }
            }
        });
        elmoThread.start();
    }

    public synchronized void addScoreOnClick(){
        cookieClickCounter++;
    }

    public synchronized  void removeScoreWithElmo(){
        cookieClickCounter -= 50;
    }

    public synchronized void elmoScore(){
        elmoProduction += 2;
    }

    public synchronized void removeScoreWithOscar(){
        cookieClickCounter -= 100;
    }

    public synchronized void oscarScore(){
        oscarProduction += 4;
    }

    public void updateGraphics(){
        score.setText(cookieClickCounter + " Cookies");
        cps.setText("Cookies Per Second: " + (elmoProduction + oscarProduction));
    }

}
