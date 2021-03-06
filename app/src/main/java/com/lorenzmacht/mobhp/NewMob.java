package com.lorenzmacht.mobhp;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.List;


public class NewMob extends ListActivity {

    //I made this variable because I can't use getContext() for some reason
    private Context context;
    private MonstersDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mob);

        dataSource = new MonstersDataSource(this);
        dataSource.open();

        List<Monster> monsters = dataSource.getAllMonsters();
        final ArrayAdapter<Monster> adapter = new ArrayAdapter<Monster>(this, R.layout.simple_centered_list_item_1, monsters);
        setListAdapter(adapter);
        //Hiding the status bar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        /*
         * COMMENTING OUT BECAUSE I NO LONGER extends ActionBarActivity
         * AND I HAVE NO IDEA WHAT'S GONNA HAPPEN NEXT
         * Also hiding action bar
         * getSupportActionBar().hide();
         */

        context = this;

        ListView list = getListView();
        list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg4)
            {
                Object o = adapter.getItem(position);
                String oText = o.toString();
                Log.e("ListView", "Works, clicked monster: " + oText);
                Monster monster = dataSource.getMonster(oText);
                EditText editName = (EditText) findViewById(R.id.monster_name);
                EditText editHP = (EditText) findViewById(R.id.monster_MaxHP);
                editName.setText(monster.getMonsterName());
                editHP.setText(monster.getMonsterMaxHP() + "");
            }
        });

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
        EditText editHP = (EditText) findViewById(R.id.monster_MaxHP);

        String monsterName = editName.getText().toString();
        String monsterMaxHP = editHP.getText().toString();

        Intent intent = new Intent(this, Main.class);
        intent.putExtra("monsterName", monsterName);
        intent.putExtra("monsterMaxHP", monsterMaxHP);
        //Using setResult so onActivityResult can detect it
        setResult(1001, intent);
        finish();
    }

    public void saveToFile(View view) {

        EditText editName = (EditText) findViewById(R.id.monster_name);
        EditText editHP = (EditText) findViewById(R.id.monster_MaxHP);
        String monsterName = editName.getText().toString();
        int monsterMaxHP = Integer.parseInt(editHP.getText().toString());


        Monster monster = null;
        ArrayAdapter<Monster> adapter = (ArrayAdapter<Monster>) getListAdapter();

        monster = dataSource.insertMonster(monsterName, monsterMaxHP);
        adapter.add(monster);

    }

    public void deleteMob(View view) {
        EditText editName = (EditText) findViewById(R.id.monster_name);
        dataSource.deleteMonster(editName.getText().toString());

        CharSequence toastText = editName.getText().toString() + " deleted. Mob will not be shown next time.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, Main.class);
        setResult(1002, intent);
        finish();
    }

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }

}
