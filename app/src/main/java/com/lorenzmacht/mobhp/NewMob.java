package com.lorenzmacht.mobhp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


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

    //Something tells me this is very wrong, this method is supposed to take the text in editText
    //and send it to the main activity, where it will print a TextView with that text.
    public void returnToMain(View view){
        EditText editText = (EditText) findViewById(R.id.edit_monster);
        String monsterName = editText.getText().toString();
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("methodName", "printMob");
        intent.putExtra("monsterName", monsterName);
        startActivity(intent);
    }
}
