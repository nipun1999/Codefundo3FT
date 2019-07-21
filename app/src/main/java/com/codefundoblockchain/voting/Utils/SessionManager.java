package com.codefundoblockchain.voting.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    Context ctx;
    private static final String PREF_NAME = "VotZure";

    private String BEARER_TOKEN = "bearer_token";
    private String EMAIL = "email";
    private String MOBILE_NO = "mobile_no";
    private String AADHAR_NO = "aadhar_no";
    private String FIRST_NAME = "first_name";
    private String LAST_NAME = "last_name";

    public SessionManager(Context context) {
        this.ctx = context;
        sharedPreferences = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public String getAADHAR_NO(){
        return sharedPreferences.getString(AADHAR_NO,null);
    }

    public void setAADHAR_NO(String aadharNo){
        editor.putString(AADHAR_NO,aadharNo);
        editor.commit();
    }

    public String getLAST_NAME(){
        return sharedPreferences.getString(LAST_NAME,null);
    }

    public void setLAST_NAME(String last_name){
        editor.putString(LAST_NAME,last_name);
        editor.commit();
    }

    public void setFIRST_NAME(String first_name){
        editor.putString(FIRST_NAME,first_name);
        editor.commit();
    }

    public String getFIRST_NAME(){
        return sharedPreferences.getString(FIRST_NAME,null);
    }

    public String getMOBILE_NO(){
        return sharedPreferences.getString(MOBILE_NO,null);
    }

    public void setMOBILE_NO(String mobile_no){
        editor.putString(MOBILE_NO,mobile_no);
        editor.commit();
    }

    public void setEMAIL(String email){
        editor.putString(EMAIL,email);
        editor.commit();
    }

    public String getEMAIL(){
        return sharedPreferences.getString(EMAIL,null);
    }

    public String getBEARER_TOKEN() {
        return sharedPreferences.getString(BEARER_TOKEN,null);
    }

    public void setBEARER_TOKEN(String token) {
        editor.putString(BEARER_TOKEN,token);
        editor.commit();
    }
}
