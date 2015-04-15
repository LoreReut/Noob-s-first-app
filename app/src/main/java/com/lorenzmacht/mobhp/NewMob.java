package com.lorenzmacht.mobhp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NewMob extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mob);


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
        //Takes the editText, puts it into monsterName which is not being used since I first
        //want to check if it even works. Same with monsterMaxHP.
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
}
