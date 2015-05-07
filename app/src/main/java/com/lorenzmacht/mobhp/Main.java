package com.lorenzmacht.mobhp;

import android.content.ClipDescription;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
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
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.draggedmobsarea);
        View.OnDragListener mRelativeDragListener = new myDragEventListener();
        relativeLayout.setOnDragListener(mRelativeDragListener);

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
            width = getView().getWidth();
            height = getView().getHeight();
            shadow.setBounds(0, 0, width, height);
            size.set(width,height);
            touch.set(width/2, height/2);

        }
        public void onDrawShadow (Canvas canvas) {
            shadow.draw(canvas);
        }

    }

    protected void onActivityResult(int requestCode, int returnCode, Intent intent) {

        if(requestCode==100) {

            String monsterName = intent.getStringExtra("monsterName");
            String monsterMaxHP = intent.getStringExtra("monsterMaxHP");
            if(returnCode == 1001 && monsterName != null && monsterMaxHP != null) {

                // Creating a RelativeLayout inside the layout with 'android:id=@+id/rootlayout'
                LinearLayout mobsArea = (LinearLayout) findViewById(R.id.mobsarea);
                final LinearLayout mobArea = new LinearLayout(this);
                mobArea.setOrientation(LinearLayout.VERTICAL);
                LayoutParams mobsAreaParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                mobsArea.addView(mobArea, mobsAreaParams);
                // Inside of it I create a TextView ('this' is the context, can also getContext(); or smth)
                TextView textMonsterName = new TextView(this);
                textMonsterName.setText(monsterName);
                mobArea.addView(textMonsterName);
                mobArea.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                // Another TextView for the HP
                TextView textMonsterMaxHP = new TextView(this);
                textMonsterMaxHP.setText(monsterMaxHP);
                mobArea.addView(textMonsterMaxHP);
                mobArea.setTag("Mob");
                mobArea.setOnLongClickListener(new View.OnLongClickListener() {

                    public boolean onLongClick(View v) {

                        //Guide said v.getTag(), but since it needs a text type thing, I needed toString() it
                        ClipData.Item item = new ClipData.Item(v.getTag().toString());
                        //Once MIMETYPE_TEXT_PLAIN is called technically this error should stop existing
                        ClipData dragData = new ClipData((CharSequence) v.getTag(), new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                        //If I don't make mobArea final the following line will give me an error, something tells me I shouldn't be doing this
                        View.DragShadowBuilder myShadow = new MyDragShadowBuilder(mobArea);
                        v.startDrag (
                            dragData,
                            myShadow,
                            null,
                            0
                        );
                        return false;

                    }   //Stop giving this error you motherfucker

               });


            }

        }

    }

    protected class myDragEventListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {

            final int action = event.getAction();
            float posX;
            float posY;
            switch(action){

                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        v.setBackgroundColor(Color.BLUE);
                        v.invalidate();
                        return true;
                    }
                    ClipData.Item startedItem = event.getClipData().getItemAt(0);
                    String startedDragData = startedItem.getText().toString();
                    Log.e("Started dragging data: ", startedDragData);
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(Color.GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    //Updating the log with the current position of the event
                    posX = event.getX();
                    posY = event.getY();
                    Log.e("Object dragged", Float.toString(posX) + " " + Float.toString(posY));

                case DragEvent.ACTION_DROP:
                    // Getting X and Y from the event
                    posX = event.getX();
                    posY = event.getY();
                    Log.e("Object dropped", Float.toString(posX) + " " + Float.toString(posY));
                    // Until here
                    ClipData.Item droppedItem = event.getClipData().getItemAt(0);
                    String droppedDragData = droppedItem.getText().toString();
                    Log.e("Dropped data:", droppedDragData);
                    v.setBackgroundColor(Color.WHITE);

                    /** Doesn't work
                     *  mobArea.setX(posX);
                     *  mobArea.setY(posY);
                     */
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(Color.BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult()) {
                        Log.e("Drag successful", "event.getResult() returned true.");
                    }
                    else {
                        Log.e("Drag unsuccessful", "event.getResult() returned false.");
                    }
                    return true;
                default:
                    Log.e("Invalid drag action:", "Doesn't fit any of the drag event cases.");

            }
            return false;

        }

    }


}
