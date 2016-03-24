package com.etsy.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.sample.MainActivity;
import com.etsy.sample.R;
import com.etsy.sample.model.ActiveListings;
import com.etsy.sample.model.Listing;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 3/18/2016.
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingHolder> implements Callback<ActiveListings> {

    private MainActivity activity;
    private LayoutInflater inflater; //inflates layout listing xml to recyclerview
    private ActiveListings activeListings;

    //constructor. need context for the layout inflater
    public ListingAdapter(MainActivity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);

    }

    @Override
    public ListingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListingHolder(inflater.inflate(R.layout.layout_listing, parent, false));
    }

    @Override
    public void onBindViewHolder(ListingHolder holder, int position) {
        final Listing listing = activeListings.results[position];
        holder.mTitle.setText(listing.title);
        holder.mShopName.setText(listing.Shop.shop_name);
        holder.mPrice.setText(listing.price);
        Picasso.with(holder.mImageview.getContext())
                .load(listing.Images[0].url_570xN)
                .into(holder.mImageview);
    }

    @Override
    public int getItemCount() {
        if (activeListings == null)
            return 0;
        if (activeListings.results == null)
            return 0;

        return activeListings.results.length;
    }

    @Override
    public void success(ActiveListings activeListings, Response response) {
        this.activeListings = activeListings;
        notifyDataSetChanged();
        this.activity.showList();
    }

    @Override
    public void failure(RetrofitError error) {
        this.activity.showError();
    }

    public ActiveListings getActiveListings() {
        return activeListings;
    }

    public class ListingHolder extends RecyclerView.ViewHolder {

        public ImageView mImageview;
        public TextView mTitle;
        public TextView mShopName;
        public TextView mPrice;

        public ListingHolder(View itemView) {
            super(itemView);
            mImageview = (ImageView) itemView.findViewById(R.id.listing_image);
            mTitle = (TextView) itemView.findViewById(R.id.listing_title);
            mShopName = (TextView) itemView.findViewById(R.id.listing_shop_name);
            mPrice = (TextView) itemView.findViewById(R.id.listing_price);
        }
    }
}
