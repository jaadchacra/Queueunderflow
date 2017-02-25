package com.example.joseph.queueunderflow.viewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.joseph.queueunderflow.R;
import com.example.joseph.queueunderflow.cardpage.PostRecycler;
import com.example.joseph.queueunderflow.headquarters.MainPage;
import com.example.joseph.queueunderflow.headquarters.queuebuilder.SimpleQuestion;

import java.util.ArrayList;

/**
 * Created by josep on 2/12/2017.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Uri> mResources;
    private ArrayList<Uri> mBitmap;
    private boolean fromAsk;
    private ArrayList<Integer> calcHeights;
    private int counter = 0;

    public int getCalcHeight() {
        return calcHeight;
    }

    public void setCalcHeight(int calcHeight) {
        this.calcHeight = calcHeight;
    }

    int  calcHeight;
    PostRecycler.PhotoHolder mAdapter;

    public ViewPagerAdapter(Context mContext, ArrayList<Uri> mResources,boolean fromAsk) {
        this.mContext = mContext;
        this.mResources = mResources;
        this.fromAsk = fromAsk;
    }

    public ViewPagerAdapter(Context mContext, ArrayList<Uri> mResources, PostRecycler.PhotoHolder mAdapter) {
        this.mContext = mContext;
        this.mBitmap = mResources;
        this.mAdapter = mAdapter;
        this.fromAsk = true;
        calcHeights = new ArrayList<>();
    }

    @Override
    public int getCount() {

        if(fromAsk){
            return mBitmap.size();
        }else {
            if (mResources.size() == 0) {
                return 1;
            } else {
                return mResources.size();
            }
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);


        if(fromAsk){
            Glide.with(mContext)
                    .load(mBitmap.get(position))
                    .fitCenter()
                    .listener(new RequestListener<Uri, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                            if(counter <3){
                                counter = counter + 1;
                                calcHeight = resource.getIntrinsicHeight();
                                calcHeights.add(calcHeight);
                                if(calcHeights.size() == 1){
                                    mAdapter.firstPic(calcHeight);
                                }else{
                                    mAdapter.bindPhoto(calcHeight);
                                }

                            }

                            return false;
                        }
                    })
                    .dontAnimate()
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }else{
            if(mResources.size() == 0){


                Glide.with(mContext)
                        .load("http://i63.tinypic.com/2ce57j4.jpg")
                        .fitCenter()
                        .dontAnimate()
                        .into(imageView);
            }else{
                 SimpleTarget target = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        // do something with the bitmap
                        // for demonstration purposes, let's just set it to an ImageView
                        imageView.setImageBitmap( bitmap );



                    }
                };

                Glide.with(mContext)
                        .load(mResources.get(position))
                        .asBitmap()
                        .fitCenter()
                        .dontAnimate()
                        .into(target);

            }

        }






        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}