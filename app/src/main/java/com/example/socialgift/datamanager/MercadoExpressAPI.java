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
import com.example.socialgift.model.Category;
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
    public static void getAProduct(int productId, Context context, DataManagerCallbackProduct<Product> callback) {
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
                            JSONArray categoryIdsArray = response.getJSONArray("categoryIds");
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

    /**
     * Buscar productos en MercadoExpress y deuelve una lista de productos
     * @param searchTerm
     * @param context
     * @param callback
     */
    //TODO: Implemetar el leveinstein para mejorar el algoritmo de busqueda
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


    public static void deleteProduct(int id, Context context, final DataManagerCallback callback) {
        // Construir la URL del producto específico
        String urlProduct = getBaseUrl() + "products/" + id;

        // Realizar la solicitud DELETE utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, urlProduct, null,
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

    public static void getAllProducts(Context context, DataManagerCallbackProductList<Product> callback) {
        String urlProducts = getBaseUrl() + "products";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlProducts, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Product> productList = new ArrayList<>();

                        // Parsear el JSONArray y obtener la lista de productos
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


                                Product product = new Product(id, name, description, link, photo, price, is_active, categoryIds);
                                productList.add(product);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // Llamar al callback onSuccess con la lista de productos
                        callback.onSuccess(productList);
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

    // ------------------------ CATEGORIAS ------------------------
    public static void createCategory(Category category, Context context, DataManagerCallback callback) {
        // Construir el objeto JSON con los datos de la categoría
        // Realizar la solicitud POST utilizando Volley
        String urlCategories = getBaseUrl() + "categories";

        JSONObject requestBody = new JSONObject();
        try {

            requestBody.put("name", category.getName());
            requestBody.put("description", category.getDescription());
            requestBody.put("photo", category.getPhoto());
            requestBody.put("categoryParentId", category.getCategoryParentId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlCategories, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("APIManager", "Categoría creada exitosamente");
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
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


    public static void getCategory(int categoryId, Context context, DataManagerCallbackCategory<Category> callback) {
        // Construir la URL para obtener la categoría específica
        String urlCategory = getBaseUrl() + "categories/" + categoryId;

        // Realizar la solicitud GET utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlCategory, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta exitosa del servidor
                        Category category = new Category();

                        try {
                            category.setId(response.getInt("id"));
                            category.setName(response.getString("name"));
                            category.setDescription(response.getString("description"));
                            category.setPhoto(response.getString("photo"));
                            category.setCategoryParentId(response.getInt("categoryParentId"));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        callback.onSuccess(category);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
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


    public static void editCategory(Category category, Context context, DataManagerCallback callback) {
        // Construir la URL para editar la categoría específica
        String urlCategory = getBaseUrl() + "categories/" + category.getId();

        // Crear el objeto JSON con los datos de la categoría
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", category.getName());
            requestBody.put("description", category.getDescription());
            requestBody.put("photo", category.getPhoto());
            requestBody.put("categoryParentId", category.getCategoryParentId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud PUT utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlCategory, requestBody,
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

    public static void deleteCategory(int categoryId, Context context, DataManagerCallback callback) {
        // Construir la URL para eliminar la categoría específica
        String urlCategory = getBaseUrl() + "categories/" + categoryId;

        // Realizar la solicitud DELETE utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, urlCategory, null,
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

    /**
     * Obtener todas las categorías
     * @param context
     * @param callback
     */
    public static void getAllCategories(Context context, DataManagerCallbackCategories<Category> callback){

        String urlCategories = getBaseUrl() + "categories";

        // Realizar la solicitud GET utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlCategories, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Crear una lista para almacenar las categorías
                        List<Category> categoryList = new ArrayList<>();

                        // Recorrer el JSONArray de respuesta y parsear cada objeto de categoría
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject categoryObject = response.getJSONObject(i);

                                // Crear una instancia de la categoría y establecer sus atributos
                                Category category = new Category();
                                category.setId(categoryObject.getInt("id"));
                                category.setName(categoryObject.getString("name"));
                                category.setDescription(categoryObject.getString("description"));
                                category.setPhoto(categoryObject.getString("photo"));
                                category.setCategoryParentId(categoryObject.getInt("categoryParentId"));

                                // Agregar la categoría a la lista
                                categoryList.add(category);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Llamar al callback onSuccess con la lista de categorías obtenidas
                        callback.onSuccess(categoryList);
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
