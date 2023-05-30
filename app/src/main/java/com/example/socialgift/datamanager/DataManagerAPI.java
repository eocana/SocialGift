package com.example.socialgift.datamanager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift.model.Gift;
import com.example.socialgift.model.User;
import com.example.socialgift.model.UserSession;
import com.example.socialgift.model.Wishlist;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DataManagerAPI implements DataManagerCallbacks{

    //TODO: Dividir DataManagerAPI en bloques de funcionalidad?
    /*
    URL de interes:
        https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/login
        https://balandrau.salle.url.edu/i3/socialgift/api/v1/users

    */

    private static String accessToken;

    private static String mailSession;

    private static String passwordSession;

    private static UserSession userSession;

    private static int MyIdSession;
    protected static final String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/";



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

    private static Date parseDateFromString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User getObjectUser() {
        return userSession.getUser();
    }

    /**
     * La distancia de Levenshtein, distancia entre palabras es el número mínimo de operaciones
     * requeridas para transformar una cadena de caracteres en otra
     * (Esta funcion nos servira de cara al futuro para buscar a personas!)
     * Más información donde he sacado la idea:
     *
     * <a href="https://www.youtube.com/watch?v=4oTFJOQpmRY">Comparación Cadenas Distancia Levenshtein</a>
     * @param s Cadena 1
     * @param t Cadena 2
     * @return la disntacia (int)
     */
    public static int levenshteinDistance(String s, String t) {
        int m = s.length();
        int n = t.length();
        int[][] dp = new int[m+1][n+1];
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                int cost = s.charAt(i-1) == t.charAt(j-1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i-1][j]+1, dp[i][j-1]+1), dp[i-1][j-1]+cost);
            }
        }
        return dp[m][n];
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

    public static void getUser(int id_user, Context context, DataManagerCallbackUser<User> callback){
        String urlUsers = url + "users/" + id_user;

        // Crear la solicitud GET utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlUsers, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Crear el objeto User con los datos obtenidos
                            User user = new User();
                            user.setId(response.getInt("id"));
                            user.setName(response.getString("name"));
                            user.setLastName(response.getString("last_name"));
                            user.setEmail(response.getString("email"));
                            user.setImage(response.getString("image"));

                            // Llamar al callback onSuccess con el usuario obtenido
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
     * !!WARNING IF YOU DELETE THE USER CAN BE CRASH THE APP!!
     * Método para eliminar un usuario
     * @param context
     * Lo implementamos para que el usuario pueda eliminar su cuenta y para la asignatura
     */
    public static void deleteMyUser(Context context){
        String urlUser = url + "users";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, urlUser, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("API_SUCCES_DELETE_USER", "Usuario eliminado correctamente");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        Log.e("API_ERROR_DELETE_USER", "Error en la solicitud");
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

    /**
     * Método para buscar usuarios por nombre, apellido o email. Se implementa el metodo leveinshtein
     * para que la busqueda sea mas eficiente.
     * @param searchTerm El término de búsqueda
     * @param context El contexto de la aplicación
     * @param callback El callback para manejar la respuesta
     */
    public static void searchUser(String searchTerm, Context context, final DataManagerCallbackUserList<User> callback) {
        // Construir la URL para buscar usuarios
        String urlUsers = url + "users/search?s=" + searchTerm;

        // Crear la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlUsers, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Crear una lista para almacenar los usuarios encontrados
                            List<User> userList = new ArrayList<>();

                            // Recorrer la respuesta JSON y crear objetos User con los datos obtenidos
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userObject = response.getJSONObject(i);
                                User user = new User();
                                user.setId(userObject.getInt("id"));
                                user.setName(userObject.getString("name"));
                                user.setLastName(userObject.getString("last_name"));
                                user.setEmail(userObject.getString("email"));
                                user.setImage(userObject.getString("image"));

                                userList.add(user);
                            }

                            //Calcular la distancia de levenshtein para ordenar la lista
                            // de usuarios por cercania a la busqueda
                            userList.sort(new Comparator<User>() {
                                @Override
                                public int compare(User u1, User u2) {
                                    String u1Name = u1.getName();
                                    String u1LastName = u1.getLastName();
                                    String u1Email = u1.getEmail();

                                    String u2Name = u2.getName();
                                    String u2LastName = u2.getLastName();
                                    String u2Email = u2.getEmail();

                                    int u1Distance = Math.min(Math.min(levenshteinDistance(u1Name.toLowerCase(), searchTerm.toLowerCase()),
                                                    levenshteinDistance(u1LastName.toLowerCase(), searchTerm.toLowerCase())),
                                            levenshteinDistance(u1Email.toLowerCase(), searchTerm.toLowerCase()));

                                    int u2Distance = Math.min(Math.min(levenshteinDistance(u2Name.toLowerCase(), searchTerm.toLowerCase()),
                                                    levenshteinDistance(u2LastName.toLowerCase(), searchTerm.toLowerCase())),
                                            levenshteinDistance(u2Email.toLowerCase(), searchTerm.toLowerCase()));

                                    return Integer.compare(u1Distance, u2Distance);
                                }
                            });


                            // Llamar al callback onSuccess con la lista de usuarios encontrados
                            callback.onSuccess(userList);

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
     * Función que obtiene las wishlists de mi usuario
     * @param context
     * @param callback
     */
    public static void wishlistsMyUser(Context context, DataManagerCallbackWishlists<Wishlist> callback){
        String urlWishlists = url + "users/" + userSession.getId() + "/wishlists";

        // Crear la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlWishlists, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Crear una lista para almacenar las wishlists

                            List<Wishlist> wishlistList = new ArrayList<>();

                            // Recorrer cada elemento del JSONArray
                            for (int i = 0; i < response.length(); i++) {

                                // Obtener el objeto JSON de cada wishlist
                                JSONObject wishlistObject = response.getJSONObject(i);

                                // Obtener los datos de la wishlist
                                int id = wishlistObject.getInt("id");
                                String name = wishlistObject.getString("name");
                                String description = wishlistObject.getString("description");
                                int userId = wishlistObject.getInt("user_id");
                                String creationDateString = wishlistObject.getString("creation_date");

                                // Verificar si el campo 'end_date' está presente en el JSON
                                String endDateString = wishlistObject.has("end_date") ? wishlistObject.getString("end_date") : null;

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                                Date creationDate = sdf.parse(creationDateString);
                                Date endDate = null;
                                if (endDateString != null) {
                                    endDate = sdf.parse(endDateString);
                                }

                                // Crear una lista para almacenar los gifts de la wishlist
                                List<Gift> giftList = new ArrayList<>();

                                // Verificar si el campo 'gifts' está presente en el JSON
                                if (wishlistObject.has("gifts")) {
                                    // Obtener el JSONArray de gifts de la wishlist
                                    JSONArray giftArray = wishlistObject.getJSONArray("gifts");
                                    // Recorrer cada elemento del JSONArray de gifts
                                    for (int j = 0; j < giftArray.length(); j++) {
                                        // Obtener el objeto JSON de cada gift
                                        JSONObject giftObject = giftArray.getJSONObject(j);

                                        // Obtener los datos del gift
                                        int giftId = giftObject.getInt("id");
                                        int wishlistId = giftObject.getInt("wishlist_id");
                                        String productUrl = giftObject.getString("product_url");
                                        int priority = giftObject.getInt("priority");
                                        boolean booked = giftObject.getBoolean("booked");

                                        // Crear el objeto Gift y agregarlo a la lista
                                        Gift gift = new Gift(giftId, wishlistId, productUrl, priority, booked);
                                        giftList.add(gift);
                                    }
                                }

                                // Crear el objeto Wishlist y agregarlo a la lista de wishlists
                                Wishlist wishlist = new Wishlist(id, name, description, userSession.getId(), creationDate);

                                if (endDate != null) {
                                    wishlist.setEndDate(endDate);
                                }
                                if (giftList.size() > 0) {
                                    wishlist.setGifts(giftList);
                                }

                                wishlistList.add(wishlist);
                            }

                            // Llamar al callback onSuccess con la lista de wishlists
                            callback.onSuccess(wishlistList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Llamar al callback onError en caso de error en el formato de la respuesta JSON
                            callback.onError("Error al procesar la respuesta del servidor");
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
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

    public static void wishlistsUser(int id, Context context, DataManagerCallbackWishlists<Wishlist> callback){
        String urlWishlists = url + "users/" + id + "/wishlists";

        // Crear la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlWishlists, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Crear una lista para almacenar las wishlists

                            List<Wishlist> wishlistList = new ArrayList<>();

                            // Recorrer cada elemento del JSONArray
                            for (int i = 0; i < response.length(); i++) {

                                // Obtener el objeto JSON de cada wishlist
                                JSONObject wishlistObject = response.getJSONObject(i);

                                // Obtener los datos de la wishlist
                                int id = wishlistObject.getInt("id");
                                String name = wishlistObject.getString("name");
                                String description = wishlistObject.getString("description");
                                int userId = wishlistObject.getInt("user_id");
                                String creationDateString = wishlistObject.getString("creation_date");

                                // Verificar si el campo 'end_date' está presente en el JSON
                                String endDateString = wishlistObject.has("end_date") ? wishlistObject.getString("end_date") : null;

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                                Date creationDate = sdf.parse(creationDateString);
                                Date endDate = null;
                                if (endDateString != null) {
                                    endDate = sdf.parse(endDateString);
                                }

                                // Crear una lista para almacenar los gifts de la wishlist
                                List<Gift> giftList = new ArrayList<>();

                                // Verificar si el campo 'gifts' está presente en el JSON
                                if (wishlistObject.has("gifts")) {
                                    // Obtener el JSONArray de gifts de la wishlist
                                    JSONArray giftArray = wishlistObject.getJSONArray("gifts");
                                    // Recorrer cada elemento del JSONArray de gifts
                                    for (int j = 0; j < giftArray.length(); j++) {
                                        // Obtener el objeto JSON de cada gift
                                        JSONObject giftObject = giftArray.getJSONObject(j);

                                        // Obtener los datos del gift
                                        int giftId = giftObject.getInt("id");
                                        int wishlistId = giftObject.getInt("wishlist_id");
                                        String productUrl = giftObject.getString("product_url");
                                        int priority = giftObject.getInt("priority");
                                        boolean booked = giftObject.getBoolean("booked");

                                        // Crear el objeto Gift y agregarlo a la lista
                                        Gift gift = new Gift(giftId, wishlistId, productUrl, priority, booked);
                                        giftList.add(gift);
                                    }
                                }

                                // Crear el objeto Wishlist y agregarlo a la lista de wishlists
                                Wishlist wishlist = new Wishlist(id, name, description, userSession.getId(), creationDate);

                                if (endDate != null) {
                                    wishlist.setEndDate(endDate);
                                }
                                if (giftList.size() > 0) {
                                    wishlist.setGifts(giftList);
                                }

                                wishlistList.add(wishlist);
                            }

                            // Llamar al callback onSuccess con la lista de wishlists
                            callback.onSuccess(wishlistList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Llamar al callback onError en caso de error en el formato de la respuesta JSON
                            callback.onError("Error al procesar la respuesta del servidor");
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
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

    public static void getGiftsReserved(int userId, Context context, DataManagerCallbackListGift<Gift> callback){
        String urlGiftsReserved = url + "users/" + userId + "/gifts/reserved";

        // Realizar la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlGiftsReserved, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Crear una lista para almacenar los regalos reservados
                        List<Gift> giftsReservedList = new ArrayList<>();

                        // Recorrer el JSONArray de respuesta y parsear cada objeto de regalo reservado
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject giftObject = response.getJSONObject(i);

                                // Crear una instancia de regalo reservado y establecer sus atributos
                                Gift giftReserved = new Gift();
                                giftReserved.setId(giftObject.getInt("id"));
                                giftReserved.setWishlist_id(giftObject.getInt("wishlist_id"));
                                giftReserved.setProduct_url(giftObject.getString("product_url"));
                                giftReserved.setPriority(giftObject.getInt("priority"));
                                giftReserved.setBooked(giftObject.getBoolean("booked"));

                                // Agregar el regalo reservado a la lista
                                giftsReservedList.add(giftReserved);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Llamar al callback onSuccess con la lista de regalos reservados obtenidos
                        callback.onSuccess(giftsReservedList);
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
    //-----------------------FIN BLOQUE USERS---------------------------------//

    /**************************************************************************
     * BLOQUE WISHLIST
     **************************************************************************/

    /**
     * Función que crea una wishlist
     * @param wishlist
     * @param context
     * @param callback
     */
    public static void createWishlist(Wishlist wishlist, Context context, final DataManagerCallback callback) {
        // Construir el objeto JSON con los datos de la wishlist
        JSONObject requestBody = new JSONObject();
        try {

            requestBody.put("name", wishlist.getName());
            requestBody.put("description", wishlist.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            String currentDate = sdf.format(new Date());
            requestBody.put("date", currentDate);

            if (wishlist.getEnd_date() != null) {
                requestBody.put("finished_at", wishlist.getEnd_date());
            }

            if (wishlist.getGifts() != null) {
                JSONArray giftsArray = new JSONArray();
                for (Gift gift : wishlist.getGifts()) {
                    JSONObject giftObject = new JSONObject();
                    // Agregar los campos del gift según corresponda
                    giftObject.put("id", gift.getId());
                    giftObject.put("wishlist_id", gift.getWishlistId());
                    giftObject.put("product_url", gift.getProductUrl());
                    giftObject.put("priority", gift.getPriority());
                    giftObject.put("booked", gift.isBooked());
                    giftsArray.put(giftObject);
                }
                requestBody.put("gifts", giftsArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud POST utilizando Volley
        String urlWishlists = url + "wishlists";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlWishlists, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta exitosa del servidor
                        callback.onSuccess();
                        Log.d("API_SUCCES_CREATE_WISHLIST", "Wishlist creada correctamente, "+wishlist.getName());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        String errorMessage = error.getMessage();
                        callback.onError(errorMessage);
                    }
                }){
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
     * Función que obtiene una wishlist por su ID
     * @param wishlistId ID de la wishlist
     * @param context
     * @param callback
     */
    public static void getWishlist(int wishlistId, Context context, DataManagerCallbackWishlist<Wishlist> callback) {
        // Construir la URL para obtener la wishlist específica por su ID
        String urlWishlist = url + "wishlists/" + wishlistId;

        // Crear la solicitud GET utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlWishlist, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Wishlist wishlist = new Wishlist();
                            int id = response.getInt("id");
                            int userId = response.getInt("user_id");
                            String name = response.getString("name");
                            String description = response.getString("description");
                            String creationDateStr = response.getString("creation_date");
                            String endDateStr = response.getString("end_date");
                            System.out.println("----------------------endDateStr: " + endDateStr);

                            // Convertir las fechas a objetos Date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                            Date creationDate = parseDateFromString(creationDateStr);

                            Date endDate = parseDateFromString(endDateStr);
                            wishlist.setEndDate(endDate);

                            wishlist.setId(id);
                            wishlist.setName(name);
                            wishlist.setDescription(description);
                            wishlist.setIdUser(userId);
                            wishlist.setCreationDate(creationDate);


                            // Llamar al callback onSuccess con el objeto Wishlist
                            callback.onSuccess(wishlist);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String errorMessage = "Error al procesar la respuesta del servidor";
                            callback.onError(errorMessage);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        callback.onError(errorMessage);
                    }
                }){
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
     * Función que hace un update de una wishlist
     * @param wishlist wishlist a actualizar
     * @param context
     * @param callback
     */
    public static void editWishlist(Wishlist wishlist, Context context, DataManagerCallback callback) {
        // Obtener el ID de la wishlist
        int wishlistId = wishlist.getId();
        String urlWishlist = url + "wishlists/" + wishlistId;

        // Crear el objeto JSON con los datos de la wishlist
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id", wishlist.getId());
            requestBody.put("name", wishlist.getName());
            requestBody.put("description", wishlist.getDescription());
            requestBody.put("user_id", wishlist.getIdUser());
            if (wishlist.getGifts() != null) {
                JSONArray giftsArray = new JSONArray();
                for (Gift gift : wishlist.getGifts()) {
                    JSONObject giftObject = new JSONObject();
                    // Agregar los campos del gift según corresponda
                    giftObject.put("id", gift.getId());
                    giftObject.put("wishlist_id", gift.getWishlistId());
                    giftObject.put("product_url", gift.getProductUrl());
                    giftObject.put("priority", gift.getPriority());
                    giftObject.put("booked", gift.isBooked());
                    giftsArray.put(giftObject);
                }
                requestBody.put("gifts", giftsArray);
            }


            requestBody.put("creation_date", wishlist.getCreationDate());


            if (wishlist.getEnd_date() != null){
                requestBody.put("end_date", wishlist.getEnd_date());
            }
            // Agregar otros campos de la wishlist según sea necesario
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud PUT utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlWishlist, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta exitosa del servidor
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        String errorMessage = error.getMessage();
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

    //------------------------FIN WISHLISTS

    /**************************************************************************
     * BLOQUE GIFTS
     **************************************************************************/

    public static void createGift(Gift gift, Context context, DataManagerCallback callback) {
        // Construir el objeto JSON con los datos del regalo
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("wishlist_id", gift.getWishlistId());
            requestBody.put("product_url", gift.getProductUrl());
            requestBody.put("priority", gift.getPriority());
            requestBody.put("booked", gift.isBooked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud POST utilizando Volley
        String urlGifts = url + "gifts";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlGifts, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta exitosa del servidor
                        callback.onSuccess();
                        Log.d("API_SUCCES_CREATE_GIFT", "Gift creado exitosamente");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        String errorMessage = error.getMessage();
                        callback.onError(errorMessage);
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    public static void getGift(int giftId, Context context, DataManagerCallbackGift<Gift> callback) {
        // Construir la URL para obtener el regalo específico
        String urlGift = url + "gifts/" + giftId;

        // Crear la solicitud GET utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlGift, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtener los datos del regalo del JSON
                            int id = response.getInt("id");
                            int wishlistId = response.getInt("wishlist_id");
                            String productUrl = response.getString("product_url");
                            int priority = response.getInt("priority");
                            boolean booked = response.getBoolean("booked");

                            // Crear el objeto Gift con los datos obtenidos
                            Gift gift = new Gift(id, wishlistId, productUrl, priority, booked);

                            // Llamar al callback onSuccess con el regalo obtenido
                            callback.onSuccess(gift);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Llamar al callback onError en caso de error en el formato de la respuesta JSON
                            String errorMessage = "Error al procesar la respuesta del servidor";
                            callback.onError(errorMessage);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error en la solicitud
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
    public static void editGift(Gift gift, Context context, DataManagerCallback callback) {
        // Construir la URL para editar el regalo específico
        String urlGift = url + "gifts/" + gift.getId();

        // Crear el objeto JSON con los datos del regalo
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("wishlist_id", gift.getWishlistId());
            requestBody.put("product_url", gift.getProductUrl());
            requestBody.put("priority", gift.getPriority());
            requestBody.put("booked", gift.isBooked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud PUT utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlGift, requestBody,
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
    public static void bookGift(int giftId, Context context, DataManagerCallback callback) {
        // Construir la URL para marcar el regalo como reservado
        String urlBookGift = url + "gifts/" + giftId + "/book";

        // Realizar la solicitud POST utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlBookGift, null,
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
    public static void unbookGift(int giftId, Context context, DataManagerCallback callback){
        // Construir la URL para marcar el regalo como reservado
        String urlBookGift = url + "gifts/" + giftId + "/book";

        // Realizar la solicitud POST utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, urlBookGift, null,
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
    public static void getGiftUser(int giftId, Context context, DataManagerCallbackUser<User> callback) {
        // Construir la URL para obtener el usuario asociado al regalo
        String urlGiftUser = url + "gifts/" + giftId + "/user";

        // Crear la solicitud GET utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlGiftUser, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtener los datos del usuario de la respuesta JSON
                            int userId = response.getInt("id");
                            String userName = response.getString("name");
                            String userLastName = response.getString("last_name");
                            String userEmail = response.getString("email");
                            String userImage = response.getString("image");

                            // Crear el objeto User con los datos obtenidos
                            User user = new User();
                            user.setId(userId);
                            user.setName(userName);
                            user.setLastName(userLastName);
                            user.setEmail(userEmail);
                            user.setImage(userImage);

                            // Llamar al callback onSuccess con el usuario obtenido
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

    //------------------FIN BLOQUE GIFTS------------------//



}
