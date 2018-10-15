package com.tidyhomz.admin.tidyappbeta.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.tidyhomz.admin.tidyappbeta.R;


/**
 * Created by Admin on 5/23/2017.
 */

public class ProgressBar extends AlertDialog {

    public ProgressBar(Context context) {
        super(context);


        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.dialog_progress);


    }



}
