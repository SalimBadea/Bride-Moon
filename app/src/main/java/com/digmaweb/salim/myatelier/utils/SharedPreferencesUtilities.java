package com.digmaweb.salim.myatelier.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtilities {
    SharedPreferences sharedPreferences;

    public SharedPreferencesUtilities(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    ///// create the key name for what you want to save in shared
    private static String TOKEN = "token";
    private static String REG_TOKEN = "regtoken";

    private static String ISLOGGEDIN = "loggedIn";
    private static String ISCOMPLETE = "iscomplete";
    private static String EMAIL = "email";
    private static String PASSWORD = "password";
    private static String NEWPASSWORD = "newpassword";
    private static String USER_NAME = "username";
    private static String USER_ID = "userid";
    private static String PRODUCT_Id = "id";
    private static String Budget_Count = "count";
    private static String PROFILE_IMAGE = "profileimage";
    private static String PRO_NAME = "marketcard";
    private static String PRO_PHONE = "taxcard";
    private static String PRO_TITLE = "identitycard";
    private static String REGIST_CARD = "registcard";

    /// now create a setter and getter to edit the value and to get it
    /// setter

    public void setPro_Id(String id) {
        sharedPreferences.edit().putString(PRODUCT_Id, id).apply();
    }

    public String getPro_Id() {
        return sharedPreferences.getString(PRODUCT_Id, null);
    }

    public void setBudget_Count(int count) {
        sharedPreferences.edit().putInt(Budget_Count, count).apply();
    }

    public int getBudget_Count() {
        return sharedPreferences.getInt(Budget_Count, 0);
    }

    public void setUserName(String userName) {
        sharedPreferences.edit().putString(USER_NAME, userName).apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, null);
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public void setUserId(String id) {
        sharedPreferences.edit().putString(USER_ID, id).apply();
    }

    public void setPASSWORD(String password) {
        sharedPreferences.edit().putString(PASSWORD, password).apply();
    }

    public String getPASSWORD() {
        return sharedPreferences.getString(PASSWORD, null);
    }

    public String getNEWPASSWORD() {
        return sharedPreferences.getString(NEWPASSWORD, null);
    }

    public void setNEWPASSWORD(String newpassword) {
        sharedPreferences.edit().putString(NEWPASSWORD, newpassword).apply();
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString(EMAIL, email).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, null);
    }

    public void setToken(String token) {
        //  key  , value
        sharedPreferences.edit().putString(TOKEN, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN, null);
    }

    public void setRegToken(String regToken) {

        //  key  , value
        sharedPreferences.edit().putString(REG_TOKEN, regToken).apply();

    }

    public String getRegToken() {

        return sharedPreferences.getString(REG_TOKEN, null);

    }

    public void setLoggedIn(boolean isLoggedIn) {
        //  key  , value
        sharedPreferences.edit().putBoolean(ISLOGGEDIN, isLoggedIn).apply();
    }

    public boolean getIS_Complete() {
        return sharedPreferences.getBoolean(ISCOMPLETE, false);
    }

    public void setIs_Complete(boolean isComplete) {
        //  key  , value
        sharedPreferences.edit().putBoolean(ISCOMPLETE, isComplete).apply();
    }

    public boolean getLoggedIn() {
        return sharedPreferences.getBoolean(ISLOGGEDIN, false);
    }

    public void setProfileImage(String profileImage){
        sharedPreferences.edit().putString(PROFILE_IMAGE , profileImage).apply();
    }

    public String getProfileImage(){
        return sharedPreferences.getString(PROFILE_IMAGE , null);
    }

    public void setProName(String proName) {
        sharedPreferences.edit().putString(PRO_NAME, proName).apply();
    }

    public String getProName() {
        return sharedPreferences.getString(PRO_NAME, null);
    }

    public void setProPhone(String proPhone) {
        sharedPreferences.edit().putString(PRO_PHONE, proPhone).apply();
    }

    public String getProPhone() {
        return sharedPreferences.getString(PRO_PHONE, null);
    }

    public void setProTitle(String proTitle) {
        sharedPreferences.edit().putString(REGIST_CARD, proTitle).apply();
    }

    public String getProTitle() {
        return sharedPreferences.getString(REGIST_CARD, null);
    }

    public void setIdentityCard(String identityCard) {
        sharedPreferences.edit().putString(PRO_TITLE, identityCard).apply();
    }

    public String getIdentityCard() {
        return sharedPreferences.getString(PRO_TITLE, null);
    }
}
