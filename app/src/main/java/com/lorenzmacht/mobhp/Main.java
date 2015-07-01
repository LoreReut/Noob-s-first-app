package com.lorenzmacht.mobhp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.PopupWindow;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    // This method will open a window to create a new mob
    public void openMobActivity(View view) {

        // Defining an Intent to open NewMob.class
        Intent intent = new Intent(this, NewMob.class);
        startActivityForResult(intent, 100);

    }
    //This prepares the content of the popup window that's gonna appear once I LongClick a mob
    public void initPopup(String monsterName, String monsterHP){
        PopupWindow popup;
        TextView popupText;
        Button closePopupButton;
        SeekBar monsterHPChanger;

        popupText = new TextView(this);
        popupText.setText(monsterName);

        monsterHPChanger = new SeekBar(this);
        monsterHPChanger.setMax(Integer.parseInt(monsterHP));
        monsterHPChanger.setProgress(monsterHPChanger.getMax());

        closePopupButton = new Button(this);
        closePopupButton.setText("Ok");

    }

    protected void onActivityResult(int requestCode, int returnCode, Intent intent) {

        if(requestCode==100) {

            String monsterName = intent.getStringExtra("monsterName");
            String monsterMaxHP = intent.getStringExtra("monsterMaxHP");
            if(returnCode == 1001 && !monsterName.equals(null) && monsterMaxHP != null) {
                // Creating a RelativeLayout inside the layout with 'android:id=@+id/rootlayout'
                // Commenting out because I'm spawning it in a FrameLayout instead LinearLayout mobsArea = (LinearLayout) findViewById(R.id.mobsarea);
                final FrameLayout draggedMobsArea = (FrameLayout) findViewById(R.id.draggedmobsarea);
                final LinearLayout mobArea = new LinearLayout(this);
                final LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rootlayout);
                // Commented out because this was linear layout before mobArea.setOrientation(LinearLayout.VERTICAL);
                LayoutParams mobAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                draggedMobsArea.addView(mobArea, mobAreaParams);
                // Inside of it I create a TextView ('this' is the context, can also getContext(); or smth)
                TextView textMonsterName = new TextView(this);
                mobArea.setOrientation(LinearLayout.VERTICAL);
                mobArea.setGravity(Gravity.CENTER);
                textMonsterName.setText(monsterName);
                mobArea.addView(textMonsterName);
                // TEST mobArea.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                // Another TextView for the HP
                TextView textMonsterMaxHP = new TextView(this);
                textMonsterMaxHP.setGravity(Gravity.CENTER);
                textMonsterMaxHP.setText(monsterMaxHP);
                mobArea.addView(textMonsterMaxHP);
                mobArea.setTag("Mob");

                mobArea.setLongClickable(true);
                mobArea.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        mobArea.setBackgroundColor(Color.WHITE);
                        initPopup(monsterName, monsterMaxHP);



                    }});

                mobArea.setOnTouchListener(new View.OnTouchListener() {
                    @Override

                    public boolean onTouch(View view, MotionEvent me) {
                        //Until the end of Else I'm retrieving the action bar height to substract it from the draggedMob
                        TypedValue tv = new TypedValue();
                        int actionBarHeight;
                        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                        {
                            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
                        }
                        else
                        {
                            actionBarHeight = 0;
                        }
                        LayoutParams params = (LayoutParams) view.getLayoutParams();
                        int action = me.getAction();
                        switch (action) {
                            case MotionEvent.ACTION_MOVE:
                                params.topMargin = (int) me.getRawY() - (view.getHeight() / 2) - (draggedMobsArea.getTop() + actionBarHeight) - rootLayout.getPaddingBottom();
                                params.leftMargin = (int) me.getRawX() - (view.getWidth() / 2) - draggedMobsArea.getLeft();
                                view.setLayoutParams(params);
                                break;
                            case MotionEvent.ACTION_UP:
                                params.topMargin = (int) me.getRawY() - (view.getHeight() / 2) - (draggedMobsArea.getTop() + actionBarHeight) - rootLayout.getPaddingBottom();
                                params.leftMargin = (int) me.getRawX() - (view.getWidth() / 2) - draggedMobsArea.getLeft();
                                view.setLayoutParams(params);
                                break;
                            case MotionEvent.ACTION_DOWN:
                                view.setLayoutParams(params);
                                break;
                        }
                        return true;
                    }

                });
                
            }

        }

    }

    public void OnClick(View v) {
        if (v.getId() == R.id.closePopup) {

        }
    }

}
