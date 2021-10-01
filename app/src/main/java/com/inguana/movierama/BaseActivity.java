package com.inguana.movierama;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.inguana.movierama.adapters.ShowProgressBar;

public class BaseActivity extends AppCompatActivity implements ShowProgressBar {
    //====================================================
    //Base activity functionalities
    //====================================================

    private ProgressBar progressBar;

    @Override
    public void setContentView(int layoutResID) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getLayoutInflater().inflate(R.layout.base_activity, null);
        FrameLayout frameLayout = coordinatorLayout.findViewById(R.id.activityFrameLayoutBaseActivity);
        progressBar = coordinatorLayout.findViewById(R.id.progressBarBaseActivity);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(coordinatorLayout);
    }

    @Override
    public void showProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}




