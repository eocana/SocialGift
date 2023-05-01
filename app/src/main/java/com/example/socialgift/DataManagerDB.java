package com.example.socialgift;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socialgift.model.Product;
import com.example.socialgift.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.commons.lang3.StringUtils;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DataManagerDB {
    private static FirebaseFirestore db;

    // Connection to DB Firestore
    public static void connectDataManagerDB() {
        db = FirebaseFirestore.getInstance();
        Log.d("DB", "Connection OK");
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
     * @return
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


    // BLOQUE USER

    /**
     * Añade un nuevo usuario a Firestore.
     *
     * @param uuid El UUID del usuario.
     * @param email El email del usuario.
     * @param image La URL de la imagen del usuario.
     * @param lastName El apellido del usuario.
     * @param name El nombre del usuario.
     */
    public static void addUser(String uuid, String email, String image, String lastName, String name) {
        String documentName = email.split("@")[0];
        Map<String, Object> user = new HashMap<>();
        user.put("UUID", uuid);
        user.put("email", email);
        user.put("name", name);
        user.put("last_name", lastName);
        user.put("image", image);



        db.collection("users").document(documentName)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DB_USERS", "User added to Firestore");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DB_USERS", "Error adding user to Firestore", e);
                    }
                });
    }


    /**
     * Actualiza un usuario en Firestore.
     *
     * @param user El usuario a actualizar.
     */
    public static void updateUser(User user) {
        DocumentReference userRef = db.collection("users").document(user.getEmail().split("@")[0]);
        userRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DB_USERS", "User "+user.getEmail()+" update to Firestore");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DB_USERS", "Error updating user "+user.getEmail()+" in to Firestore", e);
                    }
                });
    }

    /**
     * Elimina un usuario de Firestore.
     *
     * @param documentId El ID del documento del usuario a eliminar.
     */
    public static void deleteUser(String documentId) {
        db.collection("users").document(documentId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DB_USERS", "Deleting :+ "+documentId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DB_USERS", "Error deleting "+documentId, e);
                    }
                });

    }

    /**
     * Obtiene un usuario de Firestore según su correo electrónico
     * @param email El correo electrónico del usuario
     * @return Un objeto User si se encuentra el usuario en Firestore, o null si no se encuentra
     */
    public static User getUserByEmail(String email) {
        String documentName = email.split("@")[0];
        Task<DocumentSnapshot> documentTask = db.collection("users").document(documentName).get();
        try {
            DocumentSnapshot document = Tasks.await(documentTask);
            if (document.exists()) {
                User user = document.toObject(User.class);
                return user;
            } else {
                return null;
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("DB_USERS", "Error getting user by email from Firestore", e);
            return null;
        }
    }
    // FIN BLOQUE USER

    //BLOQUE PRODUCT
    /**
     * Add a product to Firestore
     * @param product The product to add
     */
    public static void addProduct(Product product) {
        String documentName = product.getUUID();
        Map<String, Object> productData = new HashMap<>();
        productData.put("UUID", product.getUUID());
        productData.put("description", product.getDescription());
        productData.put("id_category", product.getId_category());
        productData.put("link", product.getLink());
        productData.put("name", product.getName());
        productData.put("photo", product.getPhoto());
        productData.put("price", product.getPrice());

        db.collection("products").document(documentName)
                .set(productData)
                .addOnSuccessListener(aVoid -> Log.d("DB_PRODUCTS", "Product added to Firestore"))
                .addOnFailureListener(e -> Log.e("DB_PRODUCTS", "Error adding product to Firestore", e));
    }

    /**
     * Update an existing product in Firestore
     * @param product The updated product
     */
    public static void updateProduct(Product product) {
        DocumentReference productRef = db.collection("products").document(product.getUUID());
        productRef.set(product).addOnSuccessListener(aVoid -> Log.d("DB_PRODUCTS", "Product "+product.getUUID()+" updated in Firestore"))
                .addOnFailureListener(e -> Log.e("DB_PRODUCTS", "Error updating product "+product.getUUID()+" in Firestore", e));
    }

    /**
     * Delete a product from Firestore
     * @param uuid The UUID of the product to delete
     */
    public static void deleteProduct(String uuid) {
        db.collection("products").document(uuid).delete()
                .addOnSuccessListener(aVoid -> Log.d("DB_PRODUCTS", "Product "+uuid+" deleted from Firestore"))
                .addOnFailureListener(e -> Log.e("DB_PRODUCTS", "Error deleting product "+uuid+" from Firestore", e));
    }

    /**
     * Obtiene todos los productos de la base de datos.
     *
     * @return Una lista con todos los productos de la base de datos.
     */
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        db.collection("products").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Product product = document.toObject(Product.class);
                products.add(product);
            }
        }).addOnFailureListener(e -> Log.e("DB_PRODUCTS", "Error getting all products", e));

        return products;
    }


    /**
     * Busca los productos que más se parecen al término de búsqueda especificado.
     * Usa la distancia de Levenshtein junto a constains para sacar sacar mejores resultados
     * @param query El término de búsqueda.
     * @return Una lista con los 5 (como maximo) productos que más se asemejan al término de búsqueda.
     */
    public static List<Product> searchProducts(String query) {
        List<Product> result = new ArrayList<>();

        db.collection("products").get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<Product> products = new ArrayList<>();

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Product product = document.toObject(Product.class);
                String productName = product.getName();
                int distance = levenshteinDistance(productName.toLowerCase(), query.toLowerCase());

                if (productName.toLowerCase().contains(query) && distance < 3) {
                    products.add(product);
                }
            }

            // Ordenar la lista de productos por distancia de Levenshtein
            products.sort(new Comparator<Product>() {
                @Override
                public int compare(Product p1, Product p2) {
                    String p1Name = p1.getName();
                    String p2Name = p2.getName();
                    int p1Distance = levenshteinDistance(p1Name.toLowerCase(), query.toLowerCase());
                    int p2Distance = levenshteinDistance(p2Name.toLowerCase(), query.toLowerCase());
                    return Integer.compare(p1Distance, p2Distance);
                }
            });

            // Limitar a los 5 primeros resultados
            products = products.subList(0, Math.min(products.size(), 5));

            result.addAll(products);
            // Mostrar los resultados
            //for (Product product : products) {
                //.d("DB_PRODUCTS", product.getName());
            //}
        }).addOnFailureListener(e -> Log.e("DB_PRODUCTS", "Error searching products", e));

        return result;
    }



}
