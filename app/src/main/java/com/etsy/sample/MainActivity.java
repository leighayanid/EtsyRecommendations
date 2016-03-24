package com.etsy.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.etsy.sample.adapter.ListingAdapter;
import com.etsy.sample.api.Etsy;
import com.etsy.sample.model.ActiveListings;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_ACTIVE_LISTINGS = "StateActiveListings";
    private RecyclerView mRecyclerview;
    private View mProgressbar;
    private TextView mErrorTextView;
    private ListingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mProgressbar = findViewById(R.id.progressbar);
        mErrorTextView = (TextView) findViewById(R.id.error_view);

        //setup recyclerview
        adapter = new ListingAdapter(this);
        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerview.setAdapter(adapter);

        //if first time running
        if (savedInstanceState == null) {
            showLoading();
            Etsy.getActiveListings(adapter);
        } else {
            if (savedInstanceState.containsKey(STATE_ACTIVE_LISTINGS)) {
                adapter.success((ActiveListings) savedInstanceState.getParcelable(STATE_ACTIVE_LISTINGS), null);
                showList();
            }else{
                showLoading();
                Etsy.getActiveListings(adapter);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ActiveListings activeListings = adapter.getActiveListings();
        if (activeListings != null) {
            outState.putParcelable(STATE_ACTIVE_LISTINGS, activeListings);
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

    public void showLoading() {
        mRecyclerview.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.GONE);
    }

    public void showList() {
        mRecyclerview.setVisibility(View.VISIBLE);
        mProgressbar.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.GONE);
    }

    public void showError() {
        mRecyclerview.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }
}
