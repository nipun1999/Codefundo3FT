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
    private String APPLICATION_ID = "application_id";
    private String CONTRACT_ID = "contract_id";
    private String USER_ID = "user_id";
    private String PROFILE_PIC_LINK = "profile_pic_link";

    public SessionManager(Context context) {
        this.ctx = context;
        sharedPreferences = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public String getUSER_ID(){
        return sharedPreferences.getString(USER_ID,null);
    }


    public String getPROFILE_PIC_LINK(){
        return sharedPreferences.getString(PROFILE_PIC_LINK,null);
    }

    public void setPROFILE_PIC_LINK(String link){
        editor.putString(PROFILE_PIC_LINK,link);
        editor.commit();
    }

    public void setUSER_ID(String id){
        editor.putString(USER_ID,id);
        editor.commit();
    }

    public String getAADHAR_NO(){
        return sharedPreferences.getString(AADHAR_NO,null);
    }

    public void setAADHAR_NO(String aadharNo){
        editor.putString(AADHAR_NO,aadharNo);
        editor.commit();
    }

    public void setCONTRACT_ID(String id){
        editor.putString(CONTRACT_ID,id);
        editor.commit();
    }

    public String getCONTRACT_ID(){
        return sharedPreferences.getString(CONTRACT_ID,null);
    }

    public void setAPPLICATION_ID(String id){
        editor.putString(APPLICATION_ID,id);
        editor.commit();
    }

    public String getAPPLICATION_ID(){
        return sharedPreferences.getString(APPLICATION_ID,null);
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
