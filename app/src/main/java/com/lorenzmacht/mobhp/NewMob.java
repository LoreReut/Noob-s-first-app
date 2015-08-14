package com.lorenzmacht.mobhp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class NewMob extends ActionBarActivity {
    //I made this variable because I can't use getContext() for some reason
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mob);
        //Hiding the status bar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //Also hiding action bar
        getSupportActionBar().hide();
        context = this;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_mob, menu);
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

    public void returnToMain(View view) {
        //Takes the editText, puts it into monsterName. Same with monsterMaxHP.
        EditText editName = (EditText) findViewById(R.id.monster_name);
        String monsterName = editName.getText().toString();
        EditText editHP = (EditText) findViewById(R.id.monster_MaxHP);
        String monsterMaxHP = editHP.getText().toString();

        Intent intent = new Intent(this, Main.class);
        intent.putExtra("monsterName", monsterName);
        intent.putExtra("monsterMaxHP", monsterMaxHP);
        //Using setResult so onActivityResult can detect it
        setResult(1001, intent);
        finish();
    }

    public void saveToFile(String monsterName, String monsterHP){
        FeedReaderDbHelper reader = new FeedReaderDbHelper(context);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, Main.class);
        setResult(1002, intent);
        finish();
    }
}
