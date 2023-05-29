package com.example.socialgift.datamanager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socialgift.model.Category;
import com.example.socialgift.model.Friendship;
import com.example.socialgift.model.Gift;
import com.example.socialgift.model.Product;
import com.example.socialgift.model.User;
import com.example.socialgift.model.Wishlist;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DataManagerDB_old {
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
    public static Task<User> getUserByEmail(String email) {
        String documentName = email.split("@")[0];
        DocumentReference documentRef = db.collection("users").document(documentName);

        return documentRef.get().continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    User user = document.toObject(User.class);
                    return user;
                } else {
                    return null;
                }
            } else {
                Exception e = task.getException();
                throw e;
            }
        });
    }
    // FIN BLOQUE USER

    //BLOQUE PRODUCT
    /**
     * Add a product to Firestore
     * @param product The product to add
     */
/*
    public static void addProduct(Product product) {
        String documentName = product.getId();
        Map<String, Object> productData = new HashMap<>();
        productData.put("UUID", product.getId());
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

    */
/**
     * Update an existing product in Firestore
     * @param product The updated product
     *//*

    public static void updateProduct(Product product) {
        DocumentReference productRef = db.collection("products").document(product.getId());
        productRef.set(product).addOnSuccessListener(aVoid -> Log.d("DB_PRODUCTS", "Product "+product.getId()+" updated in Firestore"))
                .addOnFailureListener(e -> Log.e("DB_PRODUCTS", "Error updating product "+product.getId()+" in Firestore", e));
    }
*/

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

        Task<QuerySnapshot> task = db.collection("products").get();
        try {
            QuerySnapshot queryDocumentSnapshots = Tasks.await(task);
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
            for (Product product : products) {
                Log.d("DB_PRODUCTS", product.getName());
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("DB_PRODUCTS", "Error searching products", e);
        }

        return result;
    }


    //FIN BLOQUE PRODUCTS

    //INICIO WISHLISTS
    /**
     * Crea una nueva wishlist en la base de datos.
     * @param userId el UUID del usuario que crea la wishlist
     * @param name el nombre de la wishlist
     * @param finishedAt la fecha límite para completar la wishlist, puede ser null si no se especifica
     */
    public static void createWishlist(String userId, String name, Date finishedAt) {
        // Generar el ID de la wishlist
        String wishlistId = UUID.randomUUID().toString();

        // Crear el objeto de la wishlist
        Map<String, Object> wishlist = new HashMap<>();
        wishlist.put("UUID", wishlistId);
        wishlist.put("created_at", new Date());
        wishlist.put("id_user", userId);
        wishlist.put("name", name);

        if (finishedAt != null) {
            wishlist.put("finished_at", finishedAt);
        }

        // Guardar la wishlist en la base de datos
        db.collection("wishlist").document(wishlistId).set(wishlist)
                .addOnSuccessListener(aVoid -> Log.d("DB_WISHLIST", "Wishlist created successfully"))
                .addOnFailureListener(e -> Log.e("DB_WISHLIST", "Error creating wishlist", e));
    }


    /**
     * Obtiene una wishlist por su id.
     *
     * @param wishlistId El id de la wishlist a buscar.
     * @return La wishlist si se encuentra, o null si no existe.
     */
    public static Wishlist getWishlist(String wishlistId) {
        Wishlist wishlist = null;
        DocumentSnapshot document = null;

        try {
            Task<DocumentSnapshot> task = db.collection("wishlist").document(wishlistId).get();
            Tasks.await(task);
            if (task.isSuccessful()) {
                document = task.getResult();
                if (document.exists()) {
                    wishlist = document.toObject(Wishlist.class);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("DB_WISHLIST", "Error getting wishlist", e);
        }

        return wishlist;
    }

    /**
     * Función que actualiza una wishlist
     */
    /*public static void updateWishlist(Wishlist wishlist) {
        Map<String, Object> data = new HashMap<>();
        data.put("created_at", wishlist.getCreated_at());

        if (wishlist.getFinished_at() != null) {
            data.put("finished_at", wishlist.getFinished_at());
        }

        data.put("id_user", wishlist.getId_user());
        data.put("name", wishlist.getName());

        db.collection("wishlist").document(wishlist.getId()).set(data)
                .addOnSuccessListener(aVoid -> Log.d("DB_WISHLIST", "Wishlist updated successfully"))
                .addOnFailureListener(e -> Log.e("DB_WISHLIST", "Error updating wishlist", e));
    }*/

    /**
     * Elimina una wishlist de la base de datos.
     *
     * @param wishlistId El ID de la wishlist a eliminar.
     */
    public static void deleteWishlist(String wishlistId) {
        db.collection("wishlist").document(wishlistId).delete()
                .addOnSuccessListener(aVoid -> Log.d("DB_WISHLIST", "Wishlist deleted: " + wishlistId))
                .addOnFailureListener(e -> Log.e("DB_WISHLIST", "Error deleting wishlist: " + wishlistId, e));
    }

    /**
     * Funcion que retorna todas las wihslist del usuario
     * @param userId UUID del usuario
     * @return List<Wishlist> del usuario UUID
     */
    public static List<Wishlist> getUserWishlists(String userId) {
        List<Wishlist> result = new ArrayList<>();

        db.collection("wishlist")
                .whereEqualTo("id_user", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Wishlist wishlist = document.toObject(Wishlist.class);
                        result.add(wishlist);
                    }
                })
                .addOnFailureListener(e -> Log.e("DB_WISHLIST", "Error getting user wishlists", e));

        return result;
    }

    //FIN BLOQUE WISHLIST

    //INICIO BLOQUE GIFT
    //TODO getGiftsByProduct(String productId): Obtiene todos los regalos que tienen como producto
    // asociado el UUID especificado. (Necesario?)
    //TODO getGiftsByWishlist(String wishlistId): Obtiene todos los regalos que tienen como wishlist
    // asociada el UUID especificado. (Necesario?)
    /**
     * Crea un nuevo regalo asociado a una wishlist y a un producto.
     * @param wishlistId El UUID de la wishlist a la que se asocia el regalo.
     * @param productId  El UUID del producto que se regala.
     * @param priority   La prioridad del regalo en la wishlist.
     * @return El UUID del regalo creado.
     */
    public static void createGift(String productId, String wishlistId, int priority, String userIdBooked) {
        Map<String, Object> gift = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        gift.put("UUID", uuid);
        gift.put("id_product", productId);
        gift.put("id_wishlist", wishlistId);
        gift.put("priority", priority);
        if (!userIdBooked.isEmpty() || !userIdBooked.equals("") || !userIdBooked.equals(" ")){
            gift.put("user_id_booked", userIdBooked);
        }

        db.collection("gifts").document(uuid).set(gift)
                .addOnSuccessListener(aVoid -> Log.d("DB_GIFTS", "Gift created successfully"))
                .addOnFailureListener(e -> Log.e("DB_GIFTS", "Error creating gift", e));
    }

    /**
     * Obtiene un regalo de la base de datos.
     *
     * @param id El UUID del regalo.
     * @return El regalo correspondiente al UUID especificado, o null si no existe.
     */
   /* public static Gift getGift(String id) {
        Gift gift = null;
        try {
            DocumentSnapshot document = Tasks.await(db.collection("gifts").document(id).get());
            if (document.exists()) {
                gift = document.toObject(Gift.class);
                gift.setId(document.getId());
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("DB_GIFTS", "Error getting gift", e);
        }
        return gift;
    }*/

    /**
     * Actualiza los datos de un regalo en la base de datos.
     *
     * @param id           El UUID del regalo a actualizar.
     * @param idProduct    El UUID del producto del regalo.
     * @param idWishlist   El UUID de la wishlist a la que pertenece el regalo.
     * @param priority     La prioridad del regalo dentro de la wishlist.
     * @param userIdBooked El UUID del usuario que ha reservado el regalo.
     */
    public static void updateGift(String id, String idProduct, String idWishlist, int priority, String userIdBooked) {
        DocumentReference giftRef = db.collection("gifts").document(id);

        Map<String, Object> updates = new HashMap<>();
        updates.put("id_product", idProduct);
        updates.put("id_wishlist", idWishlist);
        updates.put("priority", priority);
        if (!userIdBooked.isEmpty() || !userIdBooked.equals("") || !userIdBooked.equals(" ")){
            updates.put("user_id_booked", userIdBooked);
        }

        giftRef.update(updates)
                .addOnSuccessListener(aVoid -> Log.d("DB_GIFTS", "Gift updated successfully"))
                .addOnFailureListener(e -> Log.e("DB_GIFTS", "Error updating gift", e));
    }

    /**
     * Elimina un regalo de la colección "gifts" de Firestore, según su UUID.
     *
     * @param id El UUID del regalo a eliminar.
     */
    public static void deleteGift(String id) {
        db.collection("gifts").document(id).delete()
                .addOnSuccessListener(aVoid -> Log.d("DB_GIFTS", "Gift deleted successfully"))
                .addOnFailureListener(e -> Log.e("DB_GIFTS", "Error deleting gift", e));
    }

    /**
     * Obtiene los regalos que han sido reservados por un usuario.
     *
     * @param userId El UUID del usuario que ha reservado los regalos.
     * @return Una lista de los regalos reservados por el usuario.
     */
    public static List<Gift> getGiftsByUser(String userId) {
        List<Gift> result = new ArrayList<>();

        db.collection("gifts").whereEqualTo("user_id_booked", userId)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Gift gift = document.toObject(Gift.class);
                        result.add(gift);
                    }
                }).addOnFailureListener(e -> Log.e("DB_GIFTS", "Error getting gifts by user", e));

        return result;
    }



    //FIN BLOQUE GIFT

    //INICIO FRIENDSHIP
    /**
     * Crea una nueva petición de amistad desde un usuario a otro.
     *
     * @param idUserFrom El UUID del usuario que realiza la petición de amistad.
     * @param idUserTo   El UUID del usuario al que se dirige la petición de amistad.
     */
    public static void createFriendshipRequest(String idUserFrom, String idUserTo) {
        db.collection("friendship")
                .whereEqualTo("id_user_from", idUserFrom)
                .whereEqualTo("id_user_to", idUserTo)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Ya existe una solicitud de amistad entre estos dos usuarios
                        Log.d("DB_FRIENDSHIP", "Ya existe una solicitud de amistad entre estos dos usuarios");
                    } else {
                        db.collection("friendship")
                                .whereEqualTo("id_user_from", idUserTo)
                                .whereEqualTo("id_user_to", idUserFrom)
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots2 -> {
                                    if (!queryDocumentSnapshots2.isEmpty()) {
                                        // Ya existe una solicitud de amistad entre estos dos usuarios
                                        Log.d("DB_FRIENDSHIP", "Ya existe una solicitud de amistad entre estos dos usuarios");
                                    } else {
                                        // Crear nueva solicitud de amistad
                                        Friendship friendship = new Friendship(idUserFrom, idUserTo, new Timestamp(System.currentTimeMillis()), "pending");
                                        db.collection("friendship").document().set(friendship)
                                                .addOnSuccessListener(aVoid -> Log.d("DB_FRIENDSHIP", "Solicitud de amistad creada"))
                                                .addOnFailureListener(e -> Log.e("DB_FRIENDSHIP", "Error al crear solicitud de amistad", e));
                                    }
                                })
                                .addOnFailureListener(e -> Log.e("DB_FRIENDSHIP", "Error al buscar solicitud de amistad", e));
                    }
                })
                .addOnFailureListener(e -> Log.e("DB_FRIENDSHIP", "Error al buscar solicitud de amistad", e));
    }



    /**
     * Obtiene las solicitudes de amistad pendientes para un usuario.
     *
     * @param userId El UUID del usuario al que se le buscarán las solicitudes de amistad pendientes.
     * @return Una lista de objetos Friendship con el campo "status" en "pending" y donde el "id_user_to" coincide con el "userId".
     */
    public static List<Friendship> getPendingFriendRequests(String userId) {
        List<Friendship> result = new ArrayList<>();

        db.collection("friendship")
                .whereEqualTo("id_user_to", userId)
                .whereEqualTo("status", "pending")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Friendship friendship = document.toObject(Friendship.class);
                        result.add(friendship);
                    }
                })
                .addOnFailureListener(e -> Log.e("DB_FRIENDSHIP", "Error getting pending friend requests", e));

        return result;
    }

    /**
     * Obtiene una lista de los UUIDs de los usuarios amigos de un usuario dado.
     * @param userId El UUID del usuario cuyos amigos se desean obtener.
     * @return Una lista con los UUIDs de los usuarios amigos del usuario dado.
     */
    public static List<String> getAcceptedFriendships(String userId) {
        List<String> friendIds = new ArrayList<>();

        db.collection("friendship")
                .whereEqualTo("status", "accepted")
                .whereEqualTo("id_user_from", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Friendship friendship = document.toObject(Friendship.class);
                        friendIds.add(friendship.getId_user_to());
                    }
                })
                .addOnFailureListener(e -> Log.e("DB_FRIENDSHIP", "Error getting accepted friendships", e));

        return friendIds;
    }

    /**
     * Elimina las solicitudes de amistad pendientes que llevan más de X días sin respuesta.
     * TODO: Realmente hace falta? Revisar, porque puede ser interesante para limpiar la DB
     */
    public static void deleteExpiredFriendshipRequests() {
        // Obtener la fecha actual
        Date currentDate = new Date();
        int daysToExpire = 30;

        // Obtener las solicitudes de amistad pendientes
        db.collection("friendship")
                .whereEqualTo("status", "pending")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Obtener la fecha de envío de la solicitud de amistad
                        Friendship friendship = document.toObject(Friendship.class);
                        Date sendDate = friendship.getSend_at();

                        // Obtener la diferencia de días entre la fecha actual y la fecha de envío
                        long differenceInMilliseconds = currentDate.getTime() - sendDate.getTime();
                        long differenceInDays = TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);

                        // Verificar si han pasado más de 30 días desde la creación de la solicitud de amistad
                        if (differenceInDays > daysToExpire) {
                            // Eliminar la solicitud de amistad
                            db.collection("friendship").document(document.getId()).delete();
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e("DB_FRIENDSHIP", "Error deleting expired friendship requests", e));
    }


    //FIN FRIENDSHIP

    /**
     * Crea una nueva categoría con el id especificado.
     *
     * @param id          El id de la categoría.
     * @param category    Objecto Category.
     */
    public static void createCategory(int id, Category category) {

        db.collection("categories").document(Integer.toString(id))
                .set(category)
                .addOnSuccessListener(aVoid -> Log.d("DB_CATEGORIES", "Category created successfully with ID: " + id))
                .addOnFailureListener(e -> Log.e("DB_CATEGORIES", "Error creating category", e));
    }

    /**
     * Actualiza la categoría especificada.
     *
     * @param category La categoría a actualizar.
     */
    public static void updateCategory(Category category) {
        db.collection("categories")
                .whereEqualTo("name", category.getName())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String documentId = document.getId();
                        db.collection("categories").document(documentId).set(category);
                    }
                })
                .addOnFailureListener(e -> Log.e("DB_CATEGORIES", "Error updating category", e));
    }

    /**
     * Obtiene todas las categorías que tengan como categoría padre el ID especificado.
     *
     * @param parentId El ID de la categoría padre.
     * @return Una lista de categorías.
     */
    public static List<Category> getCategoriesByParentId(String parentId) {
        List<Category> categories = new ArrayList<>();

        db.collection("categories").whereEqualTo("id_parent_category", parentId)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Category category = document.toObject(Category.class);
                        categories.add(category);
                    }
                }).addOnFailureListener(e -> Log.e("DB_CATEGORIES", "Error getting categories by parent ID", e));

        return categories;
    }



    /**
     * Busca una categoría por su nombre.
     * @param name El nombre de la categoría.
     * @return La categoría con el nombre especificado, o null si no se encuentra.
     */
    public static Category getCategoryByName(String name) {
        Task<QuerySnapshot> task = db.collection("categories")
                .whereEqualTo("name", name)
                .limit(1)
                .get();
        try {
            QuerySnapshot querySnapshot = Tasks.await(task);
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                Category category = documentSnapshot.toObject(Category.class);
                return category;
            } else {
                return null;
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("DB_CATEGORIES", "Error getting category by name from Firestore", e);
            return null;
        }
    }

    /**
     * Retrieves all parent categories from Firestore.
     *
     * @return a list of parent categories
     */
    public static List<Category> getAllParentCategories() {
        List<Category> categories = new ArrayList<>();
        Task<QuerySnapshot> queryTask = db.collection("categories").whereEqualTo("id_parent_category", null).get();
        try {
            QuerySnapshot querySnapshot = Tasks.await(queryTask);
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Category category = document.toObject(Category.class);
                categories.add(category);
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("DB_CATEGORIES", "Error getting all parent categories from Firestore", e);
        }
        return categories;
    }


    /**
      *Returns a list of subcategories for the given parent category name.
     * @param parentCategoryId The name of the parent category.
     * @return A list of subcategories for the given parent category name.
     */
    public static List<Category> getSubcategories(int parentCategoryId) {
        List<Category> subcategories = new ArrayList<>();
        Task<QuerySnapshot> queryTask = db.collection("categories").whereEqualTo("id_parent_category", parentCategoryId).get();
        try {
            QuerySnapshot querySnapshot = Tasks.await(queryTask);
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Category category = document.toObject(Category.class);
                subcategories.add(category);
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("DB_CATEGORIES", "Error getting subcategories from Firestore", e);
        }
        return subcategories;
    }

    /**
     * Elimina una categoría por su nombre de la colección de categorías en Firestore.
     * TODO: Si la categoría tiene subcategorías, también se eliminan (en cascada, peligroso).
     * @param name el nombre de la categoría a eliminar
     * @return true si se eliminó correctamente, false si ocurrió un error o la categoría no existe
     */
    public static void deleteCategoryByName(String name) {
        db.collection("categories").whereEqualTo("name", name).get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        db.collection("categories").document(document.getId()).delete();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("DB_CATEGORIES", "Error deleting category by name from Firestore", e);
                });
    }



}
