package com.valentech.imageservertest.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;
import com.valentech.imageservertest.R;
import com.valentech.imageservertest.activity.injection.InjectorActivity;
import com.valentech.imageservertest.model.binding.Image;
import com.valentech.imageservertest.viewmodel.MainViewModel;
import com.valentech.imageservertest.viewmodel.module.MainActivityModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends InjectorActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.photo_grid)
    GridView photoGrid;

    @BindView(R.id.pick_button)
    Button pickButton;

    @Inject
    MainViewModel mainViewModel;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        compositeSubscription.add(mainViewModel.getImages().subscribe(
            images -> photoGrid.setAdapter(new ImageViewItemAdapter(this, 0, images)),
            throwable -> Log.e(TAG, "Unable to get images", throwable)));

        RxView.clicks(pickButton).subscribe(aVoid -> {
            Intent pickIntent = new Intent(this, PostImageActivity.class);
            startActivity(pickIntent);
        });
    }

    @Override
    public Object getModule() {
        return new MainActivityModule();
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeSubscription.clear();
    }


    private class ImageViewItemAdapter extends ArrayAdapter<Image> {
        ImageViewItemAdapter(Context context, int resource, List<Image> imgs) {
            super(context, resource, imgs);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View imageView, @NonNull ViewGroup parent) {
            String itemURL = getItem(position) == null ? "" : getItem(position).getUrl();

            //get a thumbnail instead of the whole photo to save memory
            itemURL = itemURL.replaceAll("upload/", "upload/w_200/");
            imageView = new ImageView(MainActivity.this);
            imageView.setBackgroundColor(getColor(android.R.color.black));
            Picasso.with(MainActivity.this).load(itemURL).into((ImageView) imageView);

            //square
            imageView.setLayoutParams(new LinearLayout.LayoutParams(getWidth(), getWidth()));
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ViewImageDetailActivity.class);
                intent.putExtra(ViewImageDetailActivity.IMAGE_KEY, getItem(position));
                startActivity(intent);
            });
            return imageView;
        }


        private int getWidth() {
            int sideMargins = getPixelsFromDP(16) * 2;
            int gridSpacing = getPixelsFromDP(8) * 2;
            return (Resources.getSystem().getDisplayMetrics().widthPixels - (sideMargins + gridSpacing)) / 3;
        }
    }

    private static int getPixelsFromDP(float dp) {
        return Float.valueOf(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics())).intValue();
    }
}
