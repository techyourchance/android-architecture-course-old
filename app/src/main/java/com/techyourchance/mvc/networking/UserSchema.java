package com.techyourchance.mvc.networking;

import com.google.gson.annotations.SerializedName;

public class UserSchema {

    @SerializedName("display_name")
    private final String mUserDisplayName;

    @SerializedName("profile_image")
    private final String mUserAvatarUrl;

    public UserSchema(String userDisplayName, String userAvatarUrl) {
        mUserDisplayName = userDisplayName;
        mUserAvatarUrl = userAvatarUrl;
    }

    public String getUserAvatarUrl() {
        return mUserAvatarUrl;
    }

    public String getUserDisplayName() {
        return mUserDisplayName;
    }

}
