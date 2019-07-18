package com.androidtutz.anushka.ebookshop;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidtutz.anushka.ebookshop.databinding.ActivityMainBinding;
import com.androidtutz.anushka.ebookshop.model.Book;
import com.androidtutz.anushka.ebookshop.model.Category;
import com.androidtutz.anushka.ebookshop.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoriesList;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers handlers;
    private Category selectedCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        handlers=new MainActivityClickHandlers();
        activityMainBinding.setClickHandlers(handlers);

        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {

                categoriesList=(ArrayList<Category>)categories;
                for(Category c:categories){

                    Log.i("MyTAG",c.getCategoryName());
                }
                showOnSpinner();
            }
        });

        mainActivityViewModel.getBooksOfASelectedCategory(3).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                for(Book b:books){
                    Log.i("MyTAG",b.getBookName());
                }
            }
        });

    }


    private void showOnSpinner(){

        ArrayAdapter<Category> categoryArrayAdapter=new ArrayAdapter<Category>(this,R.layout.spinner_item,categoriesList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);



    }

    public class MainActivityClickHandlers{

        public void onFABClicked(View view){

            Toast.makeText(getApplicationContext()," FAB Clicked",Toast.LENGTH_LONG).show();
        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {

            selectedCategory = (Category) parent.getItemAtPosition(pos);

            String message = " id is " + selectedCategory.getId() + "\n name is " + selectedCategory.getCategoryName() + "\n email is " + selectedCategory.getCategoryDescription();

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), message, Toast.LENGTH_LONG).show();


        }

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
}
