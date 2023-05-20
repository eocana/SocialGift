package com.example.socialgift.datamanager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MercadoExpressAPI extends DataManagerAPI implements DataManagerCallbacks{

    private static final String MERCADOEXPRESS_BASE_URL = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/";

    protected static String getBaseUrl() {
        return MERCADOEXPRESS_BASE_URL;
    }

    // Método para crear un producto en MercadoExpress
    public void createProduct(Product product, Context context, DataManagerCallback callback) {
        String url = getBaseUrl() + "products";

        // Crea el objeto JSON con los datos del producto
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", product.getName());
            requestBody.put("description", product.getDescription());
            requestBody.put("price", product.getPrice());
            requestBody.put("link", product.getLink());
            requestBody.put("photo", product.getPhoto());
            requestBody.put("is_active", product.getIsActive());
            requestBody.put("category_ids", product.getCategoryIds());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realiza la solicitud POST utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Maneja la respuesta exitosa del servidor
                        // Por ejemplo, puedes convertir el JSON en un objeto Product y llamar al callback onSuccess

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja el error de la solicitud
                        // Por ejemplo, puedes obtener el mensaje de error del VolleyError y llamar al callback onError
                        String errorMessage = error.getMessage();
                        callback.onError(errorMessage);
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Obtiene un producto específico de MercadoExpress
     * @param productId ID del producto a obtener
     * @param context Contexto de la aplicación
     * @param callback Callback para manejar la respuesta del servidor
     */
    public void getAProduct(int productId, Context context, DataManagerCallbackProduct<Product> callback) {
        String url = getBaseUrl() + "products/" + productId;

        // Realiza la solicitud GET utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Maneja la respuesta exitosa del servidor
                        // Por ejemplo, puedes extraer los campos del JSON manualmente
                        try {
                            int id = response.getInt("id");
                            String name = response.getString("name");
                            String description = response.getString("description");
                            String link = response.getString("link");
                            String photo = response.getString("photo");
                            float price = response.getInt("price");
                            int is_active = response.getInt("is_active");
                            JSONArray categoryIdsArray = response.getJSONArray("category_ids");
                            int[] categoryIds = new int[categoryIdsArray.length()];
                            for (int i = 0; i < categoryIdsArray.length(); i++) {
                                categoryIds[i] = categoryIdsArray.getInt(i);
                            }

                            // Extrae otros campos del JSON según corresponda

                            // Crea un objeto Product con los datos extraídos
                            Product product = new Product(id, name, description, link, photo, price, is_active, categoryIds);

                            // Llama al callback onSuccess con el objeto Product
                            callback.onSuccess(product);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Llama al callback onError en caso de error al procesar la respuesta JSON
                            callback.onError("Error al procesar la respuesta del servidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja el error de la solicitud
                        // Por ejemplo, puedes obtener el mensaje de error del VolleyError y llamar al callback onError
                        String errorMessage = error.getMessage();
                        callback.onError(errorMessage);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                // Agrega el encabezado Authorization con el token de acceso
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getAccessToken());
                return headers;
            }
        };

        // Obtén la cola de solicitudes de Volley utilizando el contexto proporcionado
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Editar un producto en MercadoExpress
     * @param product Producto a editar
     * @param context Contexto de la aplicación
     * @param callback Callback para manejar la respuesta del servidor
     */
    public static void editProduct(Product product, Context context, final DataManagerCallback callback) {
        // Construir la URL del producto específico
        String urlProduct = getBaseUrl() + "products/" + product.getId();

        // Crear el objeto JSON con los datos actualizados del producto
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", product.getName());
            requestBody.put("description", product.getDescription());
            requestBody.put("link", product.getLink());
            requestBody.put("photo", product.getPhoto());
            requestBody.put("price", product.getPrice());
            requestBody.put("categoryIds", product.getCategoryIds());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud PUT utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlProduct, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

    public static void searchProduct(String searchTerm, Context context, final DataManagerCallbackProductList<Product> callback) {
        // Construir la URL de búsqueda de productos
        String urlSearch = getBaseUrl() + "products/search?s=" + searchTerm;

        // Crear la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlSearch, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Crear una lista para almacenar los productos
                        List<Product> productList = new ArrayList<>();

                        // Recorrer la respuesta JSON y crear objetos Product directamente
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject productObject = response.getJSONObject(i);
                                int id = productObject.getInt("id");
                                String name = productObject.getString("name");
                                String description = productObject.getString("description");
                                String link = productObject.getString("link");
                                String photo = productObject.getString("photo");
                                float price = productObject.getInt("price");
                                int is_active = productObject.getInt("is_active");
                                JSONArray categoryIdsArray = productObject.getJSONArray("category_ids");
                                int[] categoryIds = new int[categoryIdsArray.length()];
                                for (int a = 0; a < categoryIdsArray.length(); a++) {
                                    categoryIds[a] = categoryIdsArray.getInt(a);
                                }
                                // Crear un objeto Product y agregarlo a la lista
                                Product product = new Product(id, name, description, link, photo, price, is_active, categoryIds);
                                if (product.getIsActive() == 1) {
                                    productList.add(product);
                                }
                            }

                            // Llamar al callback onSuccess con la lista de productos obtenida
                            callback.onSuccess(productList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error al procesar la respuesta del servidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
