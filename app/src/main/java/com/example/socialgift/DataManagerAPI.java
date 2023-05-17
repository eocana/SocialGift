package com.example.socialgift;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DataManagerAPI {

    private static final String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/";

    /*
    URL de interes:
        https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/login
        https://balandrau.salle.url.edu/i3/socialgift/api/v1/users

    */

    private static String accessToken;


    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public interface DataManagerCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    public interface DataManagerCallbackLogin {
        void onSuccess(String token);
        void onError(String errorMessage);
    }

    public static void createUser(String name, String lastName, String email, String password, String imageUrl, Context context,DataManagerCallback callback) {
        // Construir el objeto JSON con los datos del usuario
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("last_name", lastName);
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("image", imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud POST utilizando Volley
        String urlUsers = url + "users";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlUsers, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta exitosa del servidor
                        // Por ejemplo, mostrar un mensaje de éxito o realizar otras operaciones necesarias
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        // Por ejemplo, mostrar un mensaje de error o realizar otras operaciones necesarias
                        String errorMessage = error.getMessage();
                        callback.onError(errorMessage);
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


    public static void loginUser(String email, String password, Context context, final DataManagerCallbackLogin callback) {

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud POST utilizando Volley
        String urlUsers = url + "users/login";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlUsers, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta exitosa del servidor
                        try {
                            String accessToken = response.getString("accessToken");
                            setAccessToken(accessToken);
                            callback.onSuccess(getAccessToken());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error al procesar la respuesta del servidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        // Por ejemplo, obtener el mensaje de error del servidor y llamar al callback de fallo
                        String errorMessage = "Error en la solicitud";
                        if (error.networkResponse != null) {
                            String responseBody = new String(error.networkResponse.data);
                            try {
                                JSONObject errorObject = new JSONObject(responseBody);
                                errorMessage = errorObject.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onError(errorMessage);
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

}
