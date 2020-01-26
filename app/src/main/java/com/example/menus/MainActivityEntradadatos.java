package com.example.menus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.ListActivity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

//v1.0 v1 final con acceso a datos de uso para poder volver a la app anterior y exportar db a downloads
//v1.1 final ya funciona el import¡¡¡


public class MainActivityEntradadatos extends ListActivity {


    private ListAdapter todoListAdapter;
    private TodoListSQLHelper todoListSQLHelper;

    private static final int PERMISSION_REQUEST_CODE = 200;

    private String previousApptolaunchafterCopy;


    List<String> arraycomidas = new ArrayList<String>();

    private int positiondiacomida;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainelegircomida);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);




        updateTodoList();


        //recuperamos el array de comidas

        //recuperamos


        arraycomidas=getArrayList("mio1");



        //sacamos del intent a posicion a cambiar

        // get intent data
        Intent i = getIntent();

        // Selected image id
        positiondiacomida = i.getExtras().getInt("id");


        Log.d("INFGO","pasada la comida de poscion:"+positiondiacomida);


        //boton add email

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ///////////////////
                /*
                AlertDialog.Builder todoTaskBuilder = new AlertDialog.Builder(MainActivity.this);
                todoTaskBuilder.setTitle("Add Todo Task Item");
                todoTaskBuilder.setMessage("describe the Todo task...");
                final EditText todoET = new EditText(MainActivity.this);
                todoTaskBuilder.setView(todoET);
                todoTaskBuilder.setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String todoTaskInput = todoET.getText().toString();
                        todoListSQLHelper = new TodoListSQLHelper(MainActivity.this);
                        SQLiteDatabase sqLiteDatabase = todoListSQLHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.clear();

                        //write the Todo task input into database table
                        values.put(TodoListSQLHelper.COL1_TASK, todoTaskInput);
                        sqLiteDatabase.insertWithOnConflict(TodoListSQLHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                        //update the Todo task list UI
                        updateTodoList();
                    }
                });

                todoTaskBuilder.setNegativeButton("Cancel", null);

                todoTaskBuilder.create().show();
                */
                /////////////////


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityEntradadatos.this);

                // Get the layout inflater
                LayoutInflater inflater = MainActivityEntradadatos.this.getLayoutInflater();

                View mView = inflater.inflate(R.layout.layout_entradadatos, null);


                //acceso a lso valores
                final EditText name = (EditText)mView.findViewById(R.id.username);








                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
               // builder.setView(inflater.inflate(R.layout.layout_entradadatos, null))
                  builder.setView(mView)

                        // Add action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...



                                String nameInput = name.getText().toString();

                                nameInput = nameInput.toUpperCase(); //converts the string to uppercase

                                //chequear valores etxto:

                                if (!nameInput.isEmpty()  ) {
                                    //esta rellenos


                                    todoListSQLHelper = new TodoListSQLHelper(MainActivityEntradadatos.this);
                                    SQLiteDatabase sqLiteDatabase = todoListSQLHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.clear();

                                    //write the Todo task input into database table
                                    values.put(TodoListSQLHelper.COL1_TASK, nameInput);

                                    sqLiteDatabase.insertWithOnConflict(TodoListSQLHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                                    //update the Todo task list UI
                                    updateTodoList();
                                }

                                else {

                                    //estan vacios:

                                    Toast.makeText(MainActivityEntradadatos.this,
                                            "RELLENA NOMBRE Y EMAIL SO INUTIL!!!",
                                            Toast.LENGTH_LONG).show();

                                }

                            }
                        })
                        .setNegativeButton("CANCEL",   null );

                  builder.create().show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

       /*
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/

       //mio

       return true;

    }

    ////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////

    //update the todo task list UI
    private void updateTodoList() {
        todoListSQLHelper = new TodoListSQLHelper(MainActivityEntradadatos.this);
        SQLiteDatabase sqLiteDatabase = todoListSQLHelper.getReadableDatabase();

        //cursor to read todo task list from database
        Cursor cursor = sqLiteDatabase.query(TodoListSQLHelper.TABLE_NAME,
                new String[]{TodoListSQLHelper._ID, TodoListSQLHelper.COL1_TASK },
                null, null, null, null, null);

        //binds the todo task list with the UI
        todoListAdapter = new SimpleCursorAdapter(
                this,
                R.layout.listecomidas,
                cursor,
                new String[]{TodoListSQLHelper.COL1_TASK},
                new int[]{R.id.namelista},
                0
        );






        this.setListAdapter(todoListAdapter);




    }


    ///////////////////////////////////////////////

    //copying the email  item
    public void onCopyButtonClick(View view) {
        View v = (View) view.getParent();
        TextView todoTV = (TextView) v.findViewById(R.id.namelista);
        String todoTaskItem = todoTV.getText().toString();
        //TODO copy to clipboard

       //  setClipboard(this,todoTaskItem);

        Log.d("INFGO","copiado la comida:"+todoTaskItem);


        //PARA CAMBIAR EL VALOR:
         arraycomidas.set(positiondiacomida,todoTaskItem);


         //guardamos

        //lo guardamos

        saveArrayList((ArrayList<String>) arraycomidas,"mio1");


        //volvemos

        finish();

    }




    /////////////////////////////


    ///////////////////////////////////////

    //deleting  the email item
    public void ondeleteClick(View view) {





///////////////////////////////////////////////////////////////////
///////////////para evitar dobles clicks rapidos //////////////
///////////////////////////////////////////////////////////////////



        View v = (View) view.getParent();
        TextView nombreaborrar = (TextView) v.findViewById(R.id.namelista);
        final String borrarnombre = nombreaborrar.getText().toString();




        AlertDialog.Builder todoTaskBuilder = new AlertDialog.Builder(MainActivityEntradadatos.this);
        todoTaskBuilder.setTitle("Borrar entrada");
        todoTaskBuilder.setMessage(borrarnombre);

        todoTaskBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String deleteTodoItemSql = "DELETE FROM " + TodoListSQLHelper.TABLE_NAME +
                        " WHERE " + TodoListSQLHelper.COL1_TASK + " = '" + borrarnombre + "'";

                todoListSQLHelper = new TodoListSQLHelper(MainActivityEntradadatos.this);
                SQLiteDatabase sqlDB = todoListSQLHelper.getWritableDatabase();
                sqlDB.execSQL(deleteTodoItemSql);
                updateTodoList();

            }
        });

        todoTaskBuilder.setNegativeButton("Cancel", null);

        todoTaskBuilder.create().show();










    }


    @Override
    protected void onResume() {
        super.onResume();

        //lastapk();
    }




    /////////////////////////////////////////////////////////////////////////////////////////




    /**
     *     Save and get ArrayList in SharedPreference
     */

    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

}