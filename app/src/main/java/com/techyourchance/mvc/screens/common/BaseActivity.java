package com.techyourchance.mvc.screens.common;

import android.support.v7.app.AppCompatActivity;

import com.techyourchance.mvc.CustomApplication;
import com.techyourchance.mvc.common.dependencyinjection.CompositionRoot;

public class BaseActivity extends AppCompatActivity {

    protected CompositionRoot getCompositionRoot() {
        return ((CustomApplication) getApplication()).getCompositionRoot();
    }

}
