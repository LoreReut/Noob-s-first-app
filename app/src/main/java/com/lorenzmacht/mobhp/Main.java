package com.lorenzmacht.mobhp;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    //It creates a drag shadow for dragging a TextView as a small gray rectangle
    private static class MyDragShadowBuilder extends View.DragShadowBuilder {

        private static Drawable shadow;
        public MyDragShadowBuilder (View v) {

            super(v);
            shadow = new ColorDrawable(Color.LTGRAY);

        }
        public void onProvideShadowMetrics (Point size, Point touch) {

            int width, height;
            width = getView().getWidth() / 2;

        }

    }

    protected void onActivityResult(int requestCode, int returnCode, Intent intent) {

        if(requestCode==100) {

            String monsterName = intent.getStringExtra("monsterName");
            String monsterMaxHP = intent.getStringExtra("monsterMaxHP");
            if(returnCode == 1001 && monsterName != null && monsterMaxHP != null) {

                // Creating a RelativeLayout inside the layout with 'android:id=@+id/rootlayout'
                LinearLayout mobsArea = (LinearLayout) findViewById(R.id.mobsarea);
                LinearLayout mobArea = new LinearLayout(this);
                mobArea.setOrientation(LinearLayout.VERTICAL);
                LayoutParams mobsAreaParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                mobsArea.addView(mobArea, mobsAreaParams);
                // Inside of it I create a TextView ('this' is the context, can also getContext(); or smth)
                TextView textMonsterName = new TextView(this);
                textMonsterName.setText(monsterName);
                mobArea.addView(textMonsterName);
                // Another TextView for the HP
                TextView textMonsterMaxHP = new TextView(this);
                textMonsterMaxHP.setText(monsterMaxHP);
                mobArea.addView(textMonsterMaxHP);
                mobArea.setTag("Mob");
                mobArea.setOnLongClickListener(new View.OnLongClickListener() {

                    public boolean onLongClick(View v) {

                        ClipData.Item item = new ClipData.Item(v.getTag());
                        ClipData dragData = new ClipData(v.getTag(), ClipData.MIMETYPE_TEXT_PLAIN, item);
                        View.DragShadowBuilder myShadow = new MyDragShadowBuilder(mobArea);
                        v.startDrag (
                            dragData,
                            myShadow,
                            null,
                            0
                        );

                    }   //Stop giving this error you motherfucker

               });

            }

        }

    }


}
