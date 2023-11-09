package com.example.camefridge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private ArrayList<FavoriteProduct>favoriteProductArrayList;
    private ProductAdapter adapter;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.fab);

        db = new Database(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        toolbar.setTitle("My Favorites");
        setSupportActionBar(toolbar);
        favoriteProductArrayList = new FavoritesDao().allFavorites(db);
        adapter = new ProductAdapter(this,favoriteProductArrayList,db);
        rv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 showAlert();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_ara);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.e("onQueryTextSubmit",query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.e("onQueryTextChange",newText);
        favoriteProductArrayList = new FavoritesDao().searchFavorites(db,newText);
        adapter = new ProductAdapter(this,favoriteProductArrayList,db);
        rv.setAdapter(adapter);
        return false;
    }
    public void showAlert(){

        LayoutInflater layout =LayoutInflater.from(this);
        View design = layout.inflate(R.layout.alert_design,null);

        final EditText productText = design.findViewById(R.id.productText);
        final EditText limitText = design.findViewById(R.id.limitText);

        AlertDialog.Builder pName = new AlertDialog.Builder(this);
        pName.setTitle("Add your Favorite");
        pName.setView(design);
        pName.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String productName = productText.getText().toString().trim();
                String limit = limitText.getText().toString().trim();
                new FavoritesDao().addFavorite(db,productName,Integer.parseInt(limit));
                favoriteProductArrayList = new FavoritesDao().allFavorites(db);
                adapter = new ProductAdapter(MainActivity.this,favoriteProductArrayList,db);
                rv.setAdapter(adapter);


            }
        });

        pName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        pName.create().show();

    }
}


