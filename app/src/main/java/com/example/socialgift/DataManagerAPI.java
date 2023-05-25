package com.example.socialgift;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift.model.User;
import com.example.socialgift.model.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataManagerAPI {

    private static String accessToken;

    private static String mailSession;

    private static String passwordSession;

    private static UserSession userSession;

    private static int MyIdSession;
    private static final String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/";

    /*
    URL de interes:
        https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/login
        https://balandrau.salle.url.edu/i3/socialgift/api/v1/users

    */




    public static void setMailSession(String mailSession) {DataManagerAPI.mailSession = mailSession;}

    public static void setMyIdSession(int myIdSession) { MyIdSession = myIdSession;  }

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void logOut() {
        userSession = null;
        accessToken = null;
        MyIdSession = -1;
        mailSession = null;
    }

    public static User getObjectUser() {
        return userSession.getUser();
    }

    public interface DataManagerCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    public interface DataManagerCallbackUser<User> {
        void onSuccess(User user);
        void onError(String errorMessage);
    }

    public interface DataManagerCallbackLogin {
        void onSuccess(String token);
        void onError(String errorMessage);
    }

    /**
     * Método para crear un usuario
     * @param name
     * @param lastName
     * @param email
     * @param password
     * @param imageUrl
     * @param context
     * @param callback
     */
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


    /**
     * Método para loguear un usuario, aquí se obtiene el acces token y el mail del usuario
     * @param email
     * @param password
     * @param context
     * @param callback
     */
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
                            passwordSession = password;
                            setMailSession(email);
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

    /**
     * Método para obtener el usuario actual, aquí se obtiene el id del usuario
     * @param context
     * @param callback
     */
    public static void getMyUser(Context context, DataManagerCallbackUser<User> callback) {
        // Construir la URL para obtener el usuario actual
        String urlUsers = url + "users/search?s=" + mailSession;

        // Crear la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlUsers, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject userObject = response.getJSONObject(0);

                            // Crear el objeto User con los datos obtenidos
                            User user = new User();
                            user.setId(userObject.getInt("id"));
                            user.setName(userObject.getString("name"));
                            user.setPassword(passwordSession);
                            passwordSession = null;
                            user.setLastName(userObject.getString("last_name"));
                            user.setEmail(userObject.getString("email"));
                            user.setImage(userObject.getString("image"));

                            // Llamar al callback onSuccess con el usuario obtenido
                            setMyIdSession(user.getId());
                            userSession = new UserSession(user);
                            userSession.setToken(getAccessToken());
                            callback.onSuccess(user);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Llamar al callback onError en caso de error en el formato de la respuesta JSON
                            callback.onError("Error al procesar la respuesta del servidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error en la solicitud
                        callback.onError("Error en la solicitud: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                // Agregar el encabezado Authorization con el token de acceso
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + DataManagerAPI.getAccessToken());
                return headers;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


    /**
     * Método para actualizar un usuario
     * @param firstName
     * @param lastName
     * @param imageUrl
     * @param context
     * @param callback
     */
    public static void updateUser(String firstName, String lastName, String imageUrl, Context context, final DataManagerCallback callback) {
        String urlUser = url + "users";

        userSession.setName(firstName);
        userSession.setLastName(lastName);
        userSession.setImage(imageUrl);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", userSession.getName());
            requestBody.put("last_name", userSession.getLastName());
            requestBody.put("email", userSession.getEmail());
            requestBody.put("password", userSession.getPassword());
            requestBody.put("image", userSession.getImage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlUser, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                        Log.d("API_SUCCES_UPDATE_USER", "Usuario actualizado correctamente");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        Log.e("API_ERROR_UPDATE_USER", "Error en la solicitud");
                        callback.onError("Error en la solicitud: " + error.getMessage());
                    }
                })
            {
            @Override
            public Map<String, String> getHeaders() {
                // Agregar el encabezado Authorization con el token de acceso
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + DataManagerAPI.getAccessToken());
                return headers;
            }
        };



        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

}
