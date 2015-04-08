package com.lorenzmacht.mobhp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Main extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printMob();
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

    //This method will open a window to create a new mob
    public void newMob(View view){
        //Defining an Intent to open NewMob.class
        Intent intent = new Intent(this, NewMob.class);
        startActivityForResult(intent, 100);
    }

    //This is listening for a new intent to catch its extra so it knows it has to execute printMob
    /* Commenting out because I'm using onActivityResult()
    @Override
    protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);
    if(intent.getStringExtra("methodName").equals("printMob")) {
        printMob();
        }
    }
    */

    protected void onActivityResult(int requestCode, int returnCode, Intent intent) {
        if(requestCode==100) {
            String monsterName = intent.getStringExtra("monsterName");
            if(returnCode==1001 && monsterName != null) {
                printMob();
            }
        }
    }


    //This method will print the mobs on the main screen
    public void printMob(){

        //Making a string with the name of the monster
        Intent intent = getIntent();
        String monsterName = intent.getStringExtra("monsterName");

        //Creating a RelativeLayout inside the layout with 'android:id=@+id/rootlayout'
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootlayout);

        //Inside of it I create a textView ('this' is the context, can also getContext(); or smth)
        TextView textView = new TextView(this);
        textView.setText(monsterName);
        linearLayout.addView(textView);
    }


}
