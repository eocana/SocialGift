package com.example.socialgift.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

public class SessionManager {
    private static final String PREF_NAME = "SessionPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    public static final String KEY_TOKEN_TIMESTAMP = "token_timestamp";
    public static final long TOKEN_EXPIRATION_TIME = 2 * 60 * 1000; // 2 minutos


    private static SessionManager instance;
    private Context context;
    private SharedPreferences sharedPreferences;

    private SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void saveToken(String email, String password, String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putLong(KEY_TOKEN_TIMESTAMP, System.currentTimeMillis());
        editor.apply();

        scheduleExpiration();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void saveCredentials(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public boolean isTokenValid() {
        String token = sharedPreferences.getString(KEY_TOKEN, null);
        long timestamp = sharedPreferences.getLong(KEY_TOKEN_TIMESTAMP, 0);
        long currentTime = System.currentTimeMillis();

        // Verificar si el token existe y si ha expirado
        if (token != null && currentTime - timestamp <= TOKEN_EXPIRATION_TIME) {
            return true;
        } else {
            return false;
        }
    }



    public boolean isLoggedIn() {
        return sharedPreferences.contains(KEY_EMAIL) && sharedPreferences.contains(KEY_PASSWORD);
    }

    public void logout() {
        clearToken();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_PASSWORD);
        editor.apply();
    }

    private void scheduleExpiration() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TokenExpirationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Calcula el tiempo de expiración
        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.MINUTE, 2);

        // Programa la tarea de eliminación
        alarmManager.set(AlarmManager.RTC, expirationTime.getTimeInMillis(), pendingIntent);
    }

    private void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }

    public static class TokenExpirationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SessionManager.getInstance(context).clearToken();
        }
    }
}
