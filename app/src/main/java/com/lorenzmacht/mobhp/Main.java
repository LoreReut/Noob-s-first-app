package com.lorenzmacht.mobhp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Main extends ActionBarActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Also hiding action bar
        getSupportActionBar().hide();
        //Next line is because I couldn't write monsterHPChanger = new SeekBar(this); inside runnable
        context = this;
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


    protected void onActivityResult(int requestCode, int returnCode, Intent intent) {

        if(requestCode==100) {

            final String monsterName = intent.getStringExtra("monsterName");
            final String monsterMaxHP = intent.getStringExtra("monsterMaxHP");
            if(returnCode == 1001 && !monsterName.equals(null) && monsterMaxHP != null) {
                // Creating a RelativeLayout inside the layout with 'android:id=@+id/rootlayout'
                final TextView doEet;
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
                final TextView textMonsterMaxHP = new TextView(this);
                textMonsterMaxHP.setGravity(Gravity.CENTER);
                textMonsterMaxHP.setText(monsterMaxHP);
                mobArea.addView(textMonsterMaxHP);
                mobArea.setTag("Mob");
                doEet = new TextView(context);
                doEet.setText("Sih");


                mobArea.setLongClickable(true);

                mobArea.setOnTouchListener(new View.OnTouchListener() {
                    @Override

                    public boolean onTouch(View view, MotionEvent me) {


                        final Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            public void run(){
                                if (doEet.getText()=="Sih") {
                                    final SeekBar monsterHPChanger;
                                    final TextView currentHP;
                                    LinearLayout ll;
                                    int intMonsterMaxHP = Integer.parseInt(monsterMaxHP);
                                    //Creating an AlertDialog
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);

                                    builder.setMessage("Changing Mob HP");
                                    ll = new LinearLayout(context);
                                    ll.setGravity(Gravity.CENTER);
                                    ll.setOrientation(LinearLayout.VERTICAL);
                                    currentHP = new TextView(context);
                                    currentHP.setText(monsterMaxHP);
                                    currentHP.setGravity(Gravity.CENTER);
                                    ll.addView(currentHP);
                                    monsterHPChanger = new SeekBar(context);
                                    monsterHPChanger.setMax(intMonsterMaxHP);
                                    monsterHPChanger.setProgress(intMonsterMaxHP);
                                    monsterHPChanger.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                                    ll.addView(monsterHPChanger);
                                    ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                                    builder.setView(ll);
                                    /** Will only use if necessary
                                     *  monsterHPChanger.setProgress(monsterHPChanger.getMax());
                                     */
                                    builder.setPositiveButton("Set new HP", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            textMonsterMaxHP.setText(monsterHPChanger.getProgress()+"");
                                            dialog.dismiss();
                                            return;
                                        }

                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });

                                    monsterHPChanger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                        public void onStartTrackingTouch(SeekBar sb) {

                                        }

                                        public void onProgressChanged(SeekBar sb, int id, boolean wat) {
                                            currentHP.setText(monsterHPChanger.getProgress() + "");

                                        }

                                        public void onStopTrackingTouch(SeekBar sb) {

                                        }
                                    });
                                    builder.create().show();
                                } else {return;}


                            }
                        };
                        //This variable tells me if I should execute the runnable or not


                        LayoutParams params = (LayoutParams) view.getLayoutParams();
                        int action = me.getAction();
                        switch (action) {
                            case MotionEvent.ACTION_MOVE:
                                params.topMargin = (int) me.getRawY() - (view.getHeight() / 2) - (draggedMobsArea.getTop()) - rootLayout.getPaddingBottom();
                                params.leftMargin = (int) me.getRawX() - (view.getWidth() / 2) - draggedMobsArea.getLeft();
                                view.setLayoutParams(params);
                                break;
                            case MotionEvent.ACTION_UP:
                                params.topMargin = (int) me.getRawY() - (view.getHeight() / 2) - (draggedMobsArea.getTop()) - rootLayout.getPaddingBottom();
                                params.leftMargin = (int) me.getRawX() - (view.getWidth() / 2) - draggedMobsArea.getLeft();
                                view.setLayoutParams(params);
                                doEet.setText("Noh");

                                break;
                            case MotionEvent.ACTION_DOWN:
                                view.setLayoutParams(params);
                                doEet.setText("Sih");
                                handler.postDelayed(runnable,2500);
                                break;
                        }
                        return true;
                    }

                });
                
            }
            else {
                return;
            }

        }

    }
}
