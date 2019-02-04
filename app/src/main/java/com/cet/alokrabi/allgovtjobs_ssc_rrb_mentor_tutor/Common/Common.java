package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Kamala on 11/29/2017.
 */

public class Common {

    public static final String STR_PUSH = "pushNotification";
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static final String[] YOUTUBE_PLAYLISTS = {
            //"PLAwxTw4SYaPnMwH5-FNkErnnq_aSy706S",
            "PLl9w_SO62lENa-HaNqgQ6fs-DuRRDGYN5"



    };

}