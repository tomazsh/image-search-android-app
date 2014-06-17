package com.nedeljko.imagesearch.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity implements
        ImageSearch.ImageSearchListener, SearchOptionsDialog.SearchOptionsDialogListener {
    private static final String IMAGE_SEARCH_PREFERENCES_NAME = "ImageSearchPreferences";
    private static MainActivity instance;
    private ImageSearch mImageSearch;
    private ImageSearchOptions mImageSearchOptions;
    private ImageArrayAdapter mImageArrayAdapter;
    private GridView mImagesGridView;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        SharedPreferences preferences = getSharedPreferences(IMAGE_SEARCH_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mImageSearchOptions= new ImageSearchOptions(preferences);
        mImageSearch = new ImageSearch(mImageSearchOptions);
        mImageSearch.setListener(this);

        mImageArrayAdapter = new ImageArrayAdapter(this, new ArrayList<Image>());
        mImagesGridView = (GridView)findViewById(R.id.imagesGridView);
        mImagesGridView.setAdapter(mImageArrayAdapter);
        mImagesGridView.setOnScrollListener(new InifniteScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mImageSearch.fetchImages();
            }
        });
        mImagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Image image = mImageArrayAdapter.getItem(i);
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                intent.putExtra("image", image);
                startActivity(intent);

            }
        });
    }

    public static MainActivity getInstance() {
        return instance;
    }

    private void onSearchParametersChange() {
        mImageArrayAdapter.clear();
        mImageArrayAdapter.notifyDataSetChanged();
        mImageSearch.fetchImages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView)searchItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mImageSearch.setSearchQuery(query);
                onSearchParametersChange();
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search_options:
                FragmentManager fm = getFragmentManager();
                SearchOptionsDialog searchOptionsDialog = new SearchOptionsDialog(mImageSearchOptions);
                searchOptionsDialog.show(fm, "fragment_search_options");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onImageSearchSuccess(ArrayList<Image> newImages) {
        if (newImages.size() == 0) {
            Toast.makeText(this, R.string.no_results_found, Toast.LENGTH_LONG).show();
        } else {
            mImageArrayAdapter.addAll(newImages);
            mImageArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onImageSearchFailure() {

    }

    @Override
    public void onFinishSearchOptionsDialogListener() {
        mImageSearch.resetSearch();
        onSearchParametersChange();
    }
}
