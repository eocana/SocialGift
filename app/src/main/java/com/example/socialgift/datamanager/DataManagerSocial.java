package com.example.socialgift.datamanager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManagerSocial extends DataManagerAPI implements DataManagerCallbacks{
    public static void getUserFriends(int userId, Context context, DataManagerCallbackUserList<User> callback) {
        // Construir la URL para obtener la lista de amigos del usuario
        String urlUserFriends = url + "users/" + userId + "/friends";

        // Realizar la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlUserFriends, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Crear una lista para almacenar los amigos del usuario
                        List<User> userFriendsList = new ArrayList<>();

                        // Recorrer el JSONArray de respuesta y parsear cada objeto de amigo
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject friendObject = response.getJSONObject(i);

                                // Crear una instancia de amigo y establecer sus atributos
                                User friend = new User();
                                friend.setId(friendObject.getInt("id"));
                                friend.setName(friendObject.getString("name"));
                                friend.setLastName(friendObject.getString("last_name"));
                                friend.setEmail(friendObject.getString("email"));
                                friend.setImage(friendObject.getString("image"));

                                // Agregar el amigo a la lista
                                userFriendsList.add(friend);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Llamar al callback onSuccess con la lista de amigos obtenida
                        callback.onSuccess(userFriendsList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        callback.onError(errorMessage);
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
    public static void getMyFriends(Context context, DataManagerCallbackUserList<User> callback) {
        // Construir la URL para obtener la lista de amigos del usuario autenticado
        String urlMyFriends = url + "friends";

        // Realizar la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlMyFriends, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Crear una lista para almacenar los amigos del usuario autenticado
                        List<User> myFriendsList = new ArrayList<>();

                        // Recorrer el JSONArray de respuesta y parsear cada objeto de amigo
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject friendObject = response.getJSONObject(i);

                                // Crear una instancia de amigo y establecer sus atributos
                                User friend = new User();
                                friend.setId(friendObject.getInt("id"));
                                friend.setName(friendObject.getString("name"));
                                friend.setLastName(friendObject.getString("last_name"));
                                friend.setEmail(friendObject.getString("email"));
                                friend.setImage(friendObject.getString("image"));

                                // Agregar el amigo a la lista
                                myFriendsList.add(friend);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Llamar al callback onSuccess con la lista de amigos obtenida
                        callback.onSuccess(myFriendsList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        callback.onError(errorMessage);
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
    public static void getFriendRequests(Context context, DataManagerCallbackUserList<User> callback) {
        // Construir la URL para obtener las solicitudes de amistad del usuario autenticado
        String urlFriendRequests = url + "friends/requests";

        // Realizar la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlFriendRequests, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Crear una lista para almacenar las solicitudes de amistad del usuario autenticado
                        List<User> friendRequestsList = new ArrayList<>();

                        // Recorrer el JSONArray de respuesta y parsear cada objeto de solicitud de amistad
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject requestObject = response.getJSONObject(i);

                                // Crear una instancia de solicitud de amistad y establecer sus atributos
                                User friendRequest = new User();
                                friendRequest.setId(requestObject.getInt("id"));
                                friendRequest.setName(requestObject.getString("name"));
                                friendRequest.setLastName(requestObject.getString("last_name"));
                                friendRequest.setEmail(requestObject.getString("email"));
                                friendRequest.setImage(requestObject.getString("image"));

                                // Agregar la solicitud de amistad a la lista
                                friendRequestsList.add(friendRequest);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Llamar al callback onSuccess con la lista de solicitudes de amistad obtenida
                        callback.onSuccess(friendRequestsList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        callback.onError(errorMessage);
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
    public static void sendFriendRequest(int userId, Context context, DataManagerCallback callback) {
        // Construir la URL para enviar la solicitud de amistad
        String urlFriendRequest = url + "friends/" + userId;

        // Realizar la solicitud POST utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlFriendRequest, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Llamar al callback onSuccess en caso de éxito
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        callback.onError(errorMessage);
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
    public static void acceptFriendRequest(int requestId, Context context, DataManagerCallback callback) {
        // Construir la URL para aceptar la solicitud de amistad
        String urlAcceptRequest = url + "friends/" + requestId;

        // Realizar la solicitud PUT utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlAcceptRequest, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Llamar al callback onSuccess en caso de éxito
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        callback.onError(errorMessage);
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
    public static void rejectFriendRequest(int requestId, Context context, DataManagerCallback callback) {
        // Construir la URL para rechazar la solicitud de amistad
        String urlRejectRequest = url + "friends/" + requestId;

        // Realizar la solicitud DELETE utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, urlRejectRequest, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Llamar al callback onSuccess en caso de éxito
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        callback.onError(errorMessage);
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


}
