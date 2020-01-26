package com.example.menus;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private final List<String> comidas;
    private final List<String> favorites;

    // Constructor
    public MyAdapter(Context c, List<String> comidas,List<String> favorites) {
        mContext = c;
        this.comidas=comidas;
        this.favorites=favorites;

    }

    @Override
    public int getCount() {
        return comidas.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // /*
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.ic_launcher);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;



        TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(String.valueOf(position));
        return dummyTextView;


    }
*/




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1
        final String menu = comidas.get(position);
        final String fav=favorites.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);



            convertView = layoutInflater.inflate(R.layout.comida_linearlayout, null);


        }

        // 3

        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_book_name);

        final ImageView imageViewFavorite = (ImageView)convertView.findViewById(R.id.imageview_favorite);


        //0 pongo un tag para saber quien es a al ahora de darle al fav:
        //https://stackoverflow.com/questions/12734793/android-get-position-of-clicked-item-in-gridview

        imageViewFavorite.setTag(Integer.valueOf(position));





        // 4
        //imageView.setImageResource(book.getImageResource());
        //nameTextView.setText(mContext.getString(book.getName()));
        nameTextView.setText( menu);

        if (fav.equals("false")){

            imageViewFavorite.setImageResource(R.drawable.estrellaoff);


        }
        else {
            imageViewFavorite.setImageResource(R.drawable.estrellaon);
        }



        //color distintos

        if (position<=4){

            nameTextView.setBackgroundColor( 0xFFE7995D);


        }

        else
        {
            nameTextView.setBackgroundColor( 0xFFdb9e50);

        }
        return convertView;
    }



}