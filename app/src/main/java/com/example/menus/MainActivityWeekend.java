package com.example.menus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivityWeekend extends AppCompatActivity {


    List<String> arraycomidas = new ArrayList<String>();
    private GridView gridview;
    MyAdapter comidaadpter;

    List<String> arrayfavorites = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weekend);

        MainActivity.esFinde=true;



        //recuperamos


        arraycomidas=getArrayList("mio1finde");


        arrayfavorites=getArrayList("mio2finde");






        if (arraycomidas == null){

            arraycomidas= new ArrayList<String>();
            //locreamos la primera vez
            arraycomidas.add("COMIDA 1");
            arraycomidas.add("COMIDA 2");
            arraycomidas.add("CENA 1");
            arraycomidas.add("CENA 2");



            //lo guardamos

            saveArrayList((ArrayList<String>) arraycomidas,"mio1finde");





        }


        if (arrayfavorites==null){

            arrayfavorites = new ArrayList<String>();
            //locreamos la primera vez
            arrayfavorites.add("false");
            arrayfavorites.add("false");
            arrayfavorites.add("false");
            arrayfavorites.add("false");


            //lo guardamos

            saveArrayList((ArrayList<String>) arrayfavorites,"mio2finde");





        }


        gridview = (GridView) findViewById(R.id.gridview);
        comidaadpter=new MyAdapter(this,arraycomidas,arrayfavorites);

        gridview.setAdapter( comidaadpter);


        /**
         * On Click event for Single Gridview Item
         * */
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), MainActivityEntradadatos.class);
                // passing array index
                i.putExtra("id", position);
                startActivity(i);
            }
        });

    }

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

    @Override
    protected void onResume() {

        MainActivity.esFinde=true;


        //hay q referscar


        //recuperamos


        arraycomidas=getArrayList("mio1finde");

        arrayfavorites=getArrayList("mio2finde");


        comidaadpter=null;

        comidaadpter=new MyAdapter(this,arraycomidas,arrayfavorites);

        gridview.setAdapter( comidaadpter);

/*
        comidaadpter.notifyDataSetChanged();
        gridview.invalidateViews();
       gridview.setAdapter(comidaadpter);
*/


        super.onResume();


    }


    public void clickcongelador(View view) {

        View v = (View) view.getParent();
        ImageView favorite = (ImageView) v.findViewById(R.id.imageview_favorite);
        Integer positionfav=(Integer)view.getTag();

        // if (favorite.getDrawable().equals(getResources().getDrawable(R.drawable.estrellaon))){

        if(favorite.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.estrellaon).getConstantState())){

            favorite.setImageDrawable(getResources().getDrawable(R.drawable.estrellaoff));



            Log.d("INFO","pulsado finde num:"+positionfav);


            //PARA CAMBIAR EL VALOR:
            arrayfavorites.set(positionfav,"false");

            //lo guardamos

            saveArrayList((ArrayList<String>) arrayfavorites,"mio2finde");

        }

        else {

            favorite.setImageDrawable(getResources().getDrawable(R.drawable.estrellaon));

            //PARA CAMBIAR EL VALOR:
            arrayfavorites.set(positionfav,"true");

            //lo guardamos

            saveArrayList((ArrayList<String>) arrayfavorites,"mio2finde");
        }


    }
}
