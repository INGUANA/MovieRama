package com.inguana.movierama.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ErrorMessageSnackbar {

    public static void showErrorMessage(View rootLayout, String errorMessage) {
        Snackbar snackbar = Snackbar
                .make(rootLayout, errorMessage, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
