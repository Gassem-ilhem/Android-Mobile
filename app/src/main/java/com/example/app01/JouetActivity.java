package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class JouetActivity extends AppCompatActivity {
         DatabaseHelper db;

           ListView mListView;
            SimpleCursorAdapter mAdaper;
    String[] from  = new String[] { "product_id", "product_name", "product_price", "product_quantity" };
    int[] IDS = new int[] { R.id.product_id, R.id.product_name, R.id.product_price, R.id.product_quantity };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouet);

        db = new DatabaseHelper(this);
        Cursor cursor = null;
        if (db != null) {
         cursor = db.fetch();
        }
        mListView = (ListView) findViewById(R.id.subject_list_view);
        mListView.setEmptyView(findViewById(R.id.empty_view));

        mAdaper = new SimpleCursorAdapter(this, R.layout.activity_jouet, cursor, from, IDS, 0);
        mAdaper.notifyDataSetChanged();
        mListView.setAdapter(mAdaper);



        // OnCLickListiner For List Items
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView parent, View view, int position, long viewId) {
                    TextView idTextView = (TextView) view.findViewById(R.id.product_id);
                    TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
                    TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
                     TextView quantiteTextView = (TextView) view.findViewById(R.id.product_quantity );

                    String id = idTextView.getText().toString();
                    String name = nameTextView.getText().toString();
                    String price = priceTextView.getText().toString();
                     String quantite = quantiteTextView.getText().toString();
                    Intent modify_intent = new Intent(getApplicationContext(), ModifyProductActivity.class);
                    modify_intent.putExtra("name", name);
                    modify_intent.putExtra("price", price);
                    modify_intent.putExtra("id", id);
                    modify_intent.putExtra("quantite", quantite);

                    startActivity(modify_intent);
                    }
    });
    }
}