package com.techyourchance.mvc.common.dependencyinjection;

import com.techyourchance.mvc.common.Constants;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionRoot {

    private Retrofit mRetrofit;
    private DialogsEventBus mDialogsEventBus;

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public StackoverflowApi getStackoverflowApi() {
        return getRetrofit().create(StackoverflowApi.class);
    }

    public DialogsEventBus getDialogsEventBus() {
        if (mDialogsEventBus == null) {
            mDialogsEventBus = new DialogsEventBus();
        }
        return mDialogsEventBus;
    }
}
