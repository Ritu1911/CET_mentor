package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kamala on 6/29/2018.
 */

public class SharePref {
    SharedPreferences mySharePref;
    public SharePref(Context context){

        mySharePref = context.getSharedPreferences("filename",Context.MODE_PRIVATE);

    }
    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = mySharePref.edit();
        editor.putBoolean("NightMode",state);
        editor.commit();
    }
    public Boolean loadNightModeState(){
        Boolean state = mySharePref.getBoolean("NightMode",false);
        return state;
    }
}
