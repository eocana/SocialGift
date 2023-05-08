
# Índice de Colecciones de Firestore

- [Collection Users](#collection-users)
- [Collection Products](#collection-products)
- [Collection Wishlists](#collection-wishlists)
- [Collection Gifts](#collection-gifts)
- [Collection Friendship](#collection-friendship)
- [Collection Categories](#collection-categories)

# Collection Users

### addUser

Añade un nuevo usuario a Firestore.

```java
public static void addUser(String uuid, String email, String image, String lastName, String name) 
```

### updateUser

Actualiza un usuario en Firestore.

```java
public static void updateUser(User user) 
```


### deleteUser

Elimina un usuario de Firestore.

```java
public static void deleteUser(String documentId)
```

**Parámetros:**
- `documentId` (String): El ID (UUID) del documento del usuario a eliminar.

### getUserByEmail

Obtiene un usuario de Firestore según su correo electrónico.

```java
public static <Task>User getUserByEmail(String email)
```
Un ejemplo de como luego podeis recoger <Task>User
  
```java
 DataManagerDB.getUserByEmail(email)
                .addOnSuccessListener(user -> { /* Codigo */}})
                .addOnFailureListener(e -> { /* Codigo */  });
```

**Parámetros:**
- `email` (String): El correo electrónico del usuario.

**Retorna:**
- `User`: Un objeto User si se encuentra el usuario en Firestore, o null si no se encuentra. (Es <Task>User pero sigue siendo User)

# Collection Products

### addProduct

Añade un nuevo producto a Firestore.

```java
public static void addProduct(Product product)
```

**Parámetros:**
- `product` (Product): El objeto Product a agregar en Firestore.

### updateProduct

Actualiza un producto en Firestore.

```java
public static void updateProduct(Product product)
```

**Parámetros:**
- `product` (Product): El objeto Product actualizado.

### deleteProduct

Elimina un producto de Firestore.

```java
public static void deleteProduct(String uuid)
```

**Parámetros:**
- `uuid` (String): El UUID del documento del producto a eliminar.

### getAllProducts

Obtiene todos los productos de Firestore.

```java
public static List<Product> getAllProducts()
```

**Retorna:**
- `List<Product>`: Una lista con todos los objetos Product en Firestore.

### searchProducts

Busca productos en Firestore según un término de búsqueda.

```java
public static List<Product> searchProducts(String query)
```

**Parámetros:**
- `query` (String): El término de búsqueda a utilizar.

**Retorna:**
- `List<Product>`: Una lista con los 5 (como máximo) objetos Product que más se asemejan al término de búsqueda.

# Collection Wishlist

### createWishlist

Crea una nueva wishlist en la base de datos.

```java
public static void createWishlist(String userId, String name, Date finishedAt)
```

**Parámetros:**
- `userId` (String): El UUID del usuario que crea la wishlist.
- `name` (String): El nombre de la wishlist.
- `finishedAt` (Date): La fecha límite para completar la wishlist. Puede ser null si no se especifica.

### getWishlist

Obtiene una wishlist por su id.

```java
public static Wishlist getWishlist(String wishlistId)
```

**Parámetros:**
- `wishlistId` (String): El id de la wishlist a buscar.

**Retorna:**
- `Wishlist`: La wishlist si se encuentra, o null si no existe.

### updateWishlist

Actualiza una wishlist en Firestore.

```java
public static void updateWishlist(Wishlist wishlist)
```

**Parámetros:**
- `wishlist` (Wishlist): La wishlist actualizada.

### deleteWishlist

Elimina una wishlist de la base de datos.

```java
public static void deleteWishlist(String wishlistId)
```

**Parámetros:**
- `wishlistId` (String): El ID de la wishlist a eliminar.

### getUserWishlists

Obtiene todas las wishlists de un usuario en Firestore.

```java
public static List<Wishlist> getUserWishlists(String userId)
```

**Parámetros:**
- `userId` (String): El UUID del usuario.

**Retorna:**
- `List<Wishlist>`: Lista de todas las wishlists del usuario especificado.

# Collection Gifts

### createGift

Crea un nuevo regalo asociado a una wishlist y a un producto.

```java
public static void createGift(String productId, String wishlistId, int priority, String userIdBooked)
```

**Parámetros:**
- `productId` (String): El UUID del producto que se regala.
- `wishlistId` (String): El UUID de la wishlist a la que se asocia el regalo.
- `priority` (int): La prioridad del regalo en la wishlist.
- `userIdBooked` (String): (Opcional) El UUID del usuario que ha reservado el regalo.

### getGift

Obtiene un regalo de la base de datos.

```java
public static Gift getGift(String id)
```

**Parámetros:**
- `id` (String): El UUID del regalo a buscar.

**Retorna:**
- `Gift`: El regalo correspondiente al UUID especificado, o null si no existe.

### updateGift

Actualiza los datos de un regalo en la base de datos.

```java
public static void updateGift(String id, String idProduct, String idWishlist, int priority, String userIdBooked)
```

**Parámetros:**
- `id` (String): El UUID del regalo a actualizar.
- `idProduct` (String): El UUID del producto del regalo.
- `idWishlist` (String): El UUID de la wishlist a la que pertenece el regalo.
- `priority` (int): La prioridad del regalo dentro de la wishlist.
- `userIdBooked` (String): (Opcional) El UUID del usuario que ha reservado el regalo.

### deleteGift

Elimina un regalo de la colección "gifts" de Firestore, según su UUID.

```java
public static void deleteGift(String id)
```

**Parámetros:**
- `id` (String): El UUID del regalo a eliminar.

### getGiftsByUser

Obtiene los regalos que han sido reservados por un usuario.

```java
public static List<Gift> getGiftsByUser(String userId)
```

**Parámetros:**
- `userId` (String): El UUID del usuario que ha reservado los regalos.

**Retorna:**
- `List<Gift>`: Una lista de los regalos reservados por el usuario.

# Collection Friendship

### createFriendshipRequest

Crea una nueva petición de amistad desde un usuario a otro.

```java
public static void createFriendshipRequest(String idUserFrom, String idUserTo)
```

**Parámetros:**
- `idUserFrom` (String): El UUID del usuario que realiza la petición de amistad.
- `idUserTo` (String): El UUID del usuario al que se dirige la petición de amistad.

### getPendingFriendRequests

Obtiene las solicitudes de amistad pendientes para un usuario.

```java
public static List<Friendship> getPendingFriendRequests(String userId)
```

**Parámetros:**
- `userId` (String): El UUID del usuario al que se le buscarán las solicitudes de amistad pendientes.

**Retorna:**
- `List<Friendship>`: Una lista de objetos Friendship con el campo "status" en "pending" y donde el "id_user_to" coincide con el "userId".

### getAcceptedFriendships

Obtiene una lista de los UUIDs de los usuarios amigos de un usuario dado.

```java
public static List<String> getAcceptedFriendships(String userId)
```

**Parámetros:**
- `userId` (String): El UUID del usuario cuyos amigos se desean obtener.

**Retorna:**
- `List<String>`: Una lista con los UUIDs de los usuarios amigos del usuario dado.

### deleteExpiredFriendshipRequests

Elimina las solicitudes de amistad pendientes que llevan más de X días sin respuesta.

```java
public static void deleteExpiredFriendshipRequests()
```

**Nota:**
Este método puede ser interesante para limpiar la base de datos, pero actualmente está incompleto y requiere de una revisión más detallada antes de ser utilizado.

# Collection Categories

### createCategory

Crea una nueva categoría con el id especificado.

```java
public static void createCategory(int id, Category category)
```

**Parámetros:**
- `id` (int): El id de la categoría.
- `category` (Category): Objecto Category.

### updateCategory

Actualiza la categoría especificada.

```java
public static void updateCategory(Category category)
```

**Parámetros:**
- `category` (Category): La categoría a actualizar.

### getCategoriesByParentId

Obtiene todas las categorías que tengan como categoría padre el ID especificado.

```java
public static List<Category> getCategoriesByParentId(String parentId)
```

**Parámetros:**
- `parentId` (String): El ID de la categoría padre.

**Retorna:**
- `List<Category>`: Una lista de categorías.

### getCategoryByName

Busca una categoría por su nombre.

```java
public static Category getCategoryByName(String name)
```

**Parámetros:**
- `name` (String): El nombre de la categoría.

**Retorna:**
- `Category`: La categoría con el nombre especificado, o null si no se encuentra.

### getAllParentCategories

Retrieves all parent categories from Firestore.

```java
public static List<Category> getAllParentCategories()
```

**Retorna:**
- `List<Category>`: a list of parent categories.

### getSubcategories

Returns a list of subcategories for the given parent category name.

```java
public static List<Category> getSubcategories(int parentCategoryId)
```

**Parámetros:**
- `parentCategoryId` (int): The name of the parent category.

**Retorna:**
- `List<Category>`: A list of subcategories for the given parent category name.

### deleteCategoryByName

Elimina una categoría por su nombre de la colección de categorías en Firestore.

```java
public static void deleteCategoryByName(String name)
```

**Parámetros:**
- `name` (String): el nombre de la categoría a eliminar.
