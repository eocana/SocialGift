# DataManagerAPI.java

Esta clase actúa como un "motor" para la gestión de datos de la aplicación y se utiliza para realizar solicitudes a la API REST del servidor. Contiene métodos estáticos para realizar diversas operaciones, como crear un usuario, iniciar sesión, obtener información del usuario, editar el perfil del usuario y eliminar un usuario. También incluye interfaces de callback para manejar respuestas exitosas o errores.
Aqui teneis todo lo necesario para poder desarrollar la aplicacion, en el caso de que necesiteis algo mas, no dudeis en preguntar.

Tabla de contenidos:
- [Atributos](#atributos)
- [Métodos internos](#métodos-internos)
- [Funciones de la API](#funciones-de-la-api)
  - [Bloque usuarios](#bloque-usuarios)
    - [createUser](#createuser)
    - [loginUser](#loginuser)
    - [getMyUser](#getmyuser)
    - [getUser](#getuser)
    - [updateUser](#updateuser)
    - [searchUser](#searchuser)
    - [deleteMyUser](#deletemyuser)
    - [wishlistsOfUser](#wishlistsofuser)
  - [Bloque_wishlists](#bloque-wishlists)
    - [createWishlist](#createwishlist) 
    - [getWishlist](#getwishlist)
    - [editWishlist](#editwishlist) 
    - [deleteWishlist](#deletewishlist) [TODO]
  - [Bloque_Gifts](#bloque-gifts)
    - [createGift](#creategift)
    - [getGift](#getgift)
    - [editGift](#editgift)
    - [bookGift](#bookgift)
    - [unbookGift](#unbookgift)
  - [Bloque Products](#bloque-products)
    - [createProduct](#createproduct)
    - [getProduct](#getproduct)
    - [editProduct](#editproduct)
    - [searchProduct](#searchproduct)
    - [deleteProduct](#deleteproduct) [TODO]
  - [Bloque_Categories](#bloque-categories)
    - [getCategories](#getcategories)
    - [getCategory](#getcategory)
    - [createCategory](#createcategory)
    - [editCategory](#editcategory)
    - [deleteCategory](#deletecategory)
  - Next blocks...
- [Interfaces_Callback](#interfaces-de-callback)

## Atributos

- `accessToken`: Almacena el token de acceso utilizado para autenticar las solicitudes a la API.
- `mailSession`: Almacena el correo electrónico del usuario actualmente autenticado.
- `passwordSession`: Almacena la contraseña del usuario actualmente autenticado.
- `userSession`: Almacena la sesión del usuario actual, incluyendo su información y token de acceso.
- `MyIdSession`: Almacena el ID del usuario actualmente autenticado.
- `url`: Almacena la URL base de la API.

## Métodos internos
- `parseDateFromString(String stringDate)`: Devuelve un objeto Date a partir de una cadena de fecha.
- `levensteinDistance(String s1, String s2)`: Devuelve la distancia de Levenstein entre dos cadenas.
  ¿Que es el metodo? *La distancia de Levenshtein, distancia entre palabras es el número mínimo de operaciones requeridas para transformar una cadena de caracteres en otra*
- `setMailSession(String mailSession)`: Establece el valor del correo electrónico de la sesión actual.
- `setMyIdSession(int myIdSession)`: Establece el valor del ID del usuario actualmente autenticado.
- `setAccessToken(String token)`: Establece el token de acceso.
- `getAccessToken()`: Devuelve el token de acceso almacenado.
- `logOut()`: Restablece las variables relacionadas con la sesión.
- `getObjectUser()`: Devuelve el objeto User de la sesión actual.

## Funciones de la API
Indice por bloques, que estan divididos por los endpoints de la API.
- [Bloque usuarios](#bloque-usuarios)
  - [createUser](#createuser)
  - [loginUser](#loginuser)
  - [getMyUser](#getmyuser)
  - [getUser](#getuser)
  - [updateUser](#updateuser)
  - [searchUser](#searchuser)
  - [deleteMyUser](#deletemyuser)
  - [wishlistsOfUser](#wishlistsofuser)
- [Bloque_wishlists](#bloque-wishlists)
  - [createWishlist](#createwishlist) [TODO]
  - [getWishlist](#getwishlist) 
  - [editWishlist](#editwishlist)
  - [deleteWishlist](#deletewishlist) [TODO]
- [Bloque_Gifts](#bloque-gifts)
  - [createGift](#creategift)
  - [getGift](#getgift)
  - [editGift](#editgift) 
  - [bookGift](#bookgift)
  - [unbookGift](#unbookgift)
- [Bloque Products](#bloque-products)
  - [createProduct](#createproduct)
  - [getProduct](#getproduct)
  - [editProduct](#editproduct)
  - [searchProduct](#searchproduct)
  - [deleteProduct](#deleteproduct) [TODO]
- [Bloque_Categories](#bloque-categories)
  - [getCategories](#getcategories)
  - [getCategory](#getcategory)
  - [createCategory](#createcategory)
  - [editCategory](#editcategory)
  - [deleteCategory](#deletecategory)
- Next blocks...
- [Interfaces_Callback](#interfaces-de-callback) 
  -[Ejemplo de como usar los callbacks](#ejemplo-de-como-usar-los-callbacks)


### Bloque usuarios

#### createUser
Crea un nuevo usuario con los datos proporcionados.

```java
public static void createUser(String name, String lastName, String email, String password, String imageUrl, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `name` (String): El nombre del usuario.
  - `lastName` (String): El apellido del usuario.
  - `email` (String): El correo electrónico del usuario.
  - `password` (String): La contraseña del usuario.
  - `imageUrl` (String): La URL de la imagen de perfil del usuario.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta Mirar en [Interfaces de Callback](#interfaces-de-callback).

#### loginUser
Inicia sesión con el correo electrónico y la contraseña proporcionados. Y nos guarda el acces token en la variable `accessToken`.

```java
public static void loginUser(String email, String password, Context context, DataManagerCallbackLogin callback)
```
##### Parámetros:
  - `email` (String): El correo electrónico del usuario.
  - `password` (String): La contraseña del usuario.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackLogin): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
#### getMyUser
Obtiene la información del usuario actualmente autenticado y lo guarda en el objecto UserSession.

```java
public static void getMyUser(Context context, DataManagerCallbackUser<User> callback)
```
##### Parámetros:
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackUser<User>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `User`: El objeto User con la información del usuario actualmente autenticado EN FORMA DE CALLBACK.

#### getUser
Obtiene la información del usuario con el ID proporcionado.

```java
public static void getUser(int userId, Context context, DataManagerCallbackUser<User> callback)
```

#### updateUser
Actualiza el perfil del usuario con los datos proporcionados.

```java
public static void updateUser(String firstName, String lastName, String imageUrl, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `firstName` (String): El nombre del usuario.
  - `lastName` (String): El apellido del usuario.
  - `imageUrl` (String): La URL de la imagen de perfil del usuario.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).

#### searchUser
Busca usuarios por nombre y/o apellido y/o email. Se implementa el metodo leveinshtein para ordenar la lista de usuarios por cercania a la busqueda.

```java
public static void searchUser(String seachTerm, Context context, DataManagerCallbackUser<User> callback)
```
#### Parámetros:
  - `seachTerm` (String): Criterio de busqueda.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackUser<User>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
#### Devuelve:
  - `<List>User`: Lista ordenada de posibles resultados.
#### deleteMyUser
Elimina el usuario actualmente autenticado. NO USAR. SE IMPLEMENTA PARA LA PRACTICA

```java
public static void deleteMyUser(Context context)
```

#### wishlistsOfUser
Obtiene las listas de deseos del usuario actualmente autenticado.

```java
public static void wishlistsOfUser(Context context, DataManagerCallbackWishlist<Wishlist> callback)
```
##### Parámetros:
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackWishlist<Wishlist>>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `<List>Wishlist`: Lista de listas de deseos del usuario actualmente autenticado.


### Bloque wishlists

#### createWishlist
Crea una nueva lista de deseos con los datos proporcionados.

```java
public static void createWishlist(Wishlist wishlist, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `wishlist` (Wishlist): La lista de deseos a crear. (No todos los atributos deben estar rellenos).
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).

#### getWishlist
Obtiene la información de la lista de deseos con el id proporcionado.

```java
public static void getWishlist(String wishlistId, Context context, DataManagerCallbackWishlist<Wishlist> callback)
```
##### Parámetros:
  - `wishlistId` (String): El id de la lista de deseos.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackWishlist<Wishlist>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `Wishlist`: La lista de deseos con el id proporcionado.

### Bloque gifts


#### createGift
Crea un nuevo regalo con los datos proporcionados.

```java
public static void createGift(Gift gift, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `gift` (Gift): El regalo a crear. (No todos los atributos deben estar rellenos).
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).

#### getGift
Obtiene la información del regalo con el id proporcionado.

```java
public static void getGift(String giftId, Context context, DataManagerCallbackGift<Gift> callback)
```
##### Parámetros:
  - `giftId` (String): El id del regalo.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackGift<Gift>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `Gift`: El regalo con el id proporcionado.

#### editGift
Edita el regalo con los datos proporcionados.

```java
public static void editGift(Gift gift, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `gift` (Gift): El regalo a editar.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).


#### bookGift
Reserva el regalo con el id proporcionado. El usuario que lo reserva lo coge en base al acces token asociado

```java
public static void bookGift(String giftId, Context context, DataManagerCallback callback)
```

##### Parámetros:
  - `giftId` (String): El id del regalo.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).

#### unbookGift
Libera el regalo con el id proporcionado. El usuario que lo libera lo hace en base al acces token asociado

```java
public static void unbookGift(int giftId, Context context, DataManagerCallback callback)
```

##### Parámetros:
  - `giftId` (int): El id del regalo.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).


#### getGiftUser
Obtiene el usuario que ha reservado el regalo con el id proporcionado.

```java
public static void getGiftUser(int giftId, Context context, DataManagerCallbackUser<User> callback)
```

##### Parámetros:
  - `giftId` (int): El id del regalo.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackUser<User>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `User`: El usuario que ha reservado el regalo con el id proporcionado.


### Bloque products
Este bloque esta en otra clase que extiende DataManagerAPI para que así no quede tan cargada la clase DataManagerAPI.
También como se usa otra url para las peticiones, se ha decidido separar en otra clase.
Se llama MercadoExpressAPI. Logicamente lo pongo el mismo md para que quede todo junto.
#### createProduct
Crea un nuevo producto con los datos proporcionados.

```java
public static void createProduct(Product product, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `product` (Product): El producto a crear. (No todos los atributos deben estar rellenos).
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).

#### getAProduct

Obtiene la información del producto con el id proporcionado.

```java
public static void getAProduct(String productId, Context context, DataManagerCallbackProduct<Product> callback)
```
##### Parámetros:
  - `productId` (String): El id del producto.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackProduct<Product>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `Product`: El producto con el id proporcionado.

#### editProduct
Edita el producto con los datos proporcionados.

```java
public static void editProduct(Product product, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `product` (Product): El producto a editar.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).


#### searchProduct
Busca productos con los datos proporcionados.

```java
public static void searchProduct(String query, Context context, DataManagerCallbackProductList<Product> callback)
```
##### Parámetros:
  - `query` (String): La query de busqueda.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackProductList<Product>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `List<Product>`: La lista de productos que coinciden con la query de busqueda.


### Bloque categories

#### getCategories
Obtiene la lista de categorias.

```java
public static void getCategories(Context context, DataManagerCallbackCategoryList<Category> callback)
```
##### Parámetros:
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackCategoryList<Category>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `List<Category>`: La lista de categorias.

#### getCategory
Obtiene la categoria con el id proporcionado.

```java
public static void getCategory(String categoryId, Context context, DataManagerCallbackCategory<Category> callback)
```
##### Parámetros:
  - `categoryId` (String): El id de la categoria.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallbackCategory<Category>): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).
##### Devuelve:
  - `Category`: La categoria con el id proporcionado.

#### editCategory
Edita la categoria con los datos proporcionados.

```java
public static void editCategory(Category category, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `category` (Category): La categoria a editar.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).

#### createCategory
Crea una nueva categoria con los datos proporcionados.

```java
public static void createCategory(Category category, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `category` (Category): La categoria a crear. (No todos los atributos deben estar rellenos).
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).

#### deleteCategory
Elimina la categoria con el id proporcionado.

```java
public static void deleteCategory(String categoryId, Context context, DataManagerCallback callback)
```
##### Parámetros:
  - `categoryId` (String): El id de la categoria.
  - `context` (Context): El contexto de la aplicación (Activity/Fragment).
  - `callback` (DataManagerCallback): El callback para gestionar la respuesta. Mirar en [Interfaces de Callback](#interfaces-de-callback).






## Interfaces de Callback
No pondré todas la interfaces de callback, ya que son repteitivas y solo cambia el tipo de dato que se devuelve.
#### DataManagerCallback
Se utiliza para manejar respuestas exitosas o errores en las operaciones que no devuelven ningún dato específico.

```java
public interface DataManagerCallback {
    void onSuccess();
    void onError(String errorMessage);
}
```
##### Métodos:
  - `onSuccess()`: Se ejecuta cuando la operación se ha realizado correctamente. Aqui es cuando no necesitamos devolver ningun dato.
  - `onError(String errorMessage)`: Se ejecuta cuando la operación falla y devuelve un mensaje de error.

#### DataManagerCallbackUser<User>
Se utiliza para manejar respuestas exitosas o errores en las operaciones que devuelven información del usuario.

```java
public interface DataManagerCallbackUser<User> {
    void onSuccess(User user);
    void onError(String errorMessage);
}
```
##### Métodos:
  - `onSuccess(User user)`: Se ejecuta cuando la operación se ha realizado correctamente. Aqui es cuando necesitamos devolver un objeto User.
  - `onError(String errorMessage)`: Se ejecuta cuando la operación falla y devuelve un mensaje de error.

#### DataManagerCallbackLogin
Se utiliza para manejar respuestas exitosas o errores en las operaciones de inicio de sesión.

```java
public interface DataManagerCallbackLogin {
    void onSuccess(String token);
    void onError(String errorMessage);
}
```
##### Métodos:
  - `onSuccess(String token)`: Se ejecuta cuando la operación se ha realizado correctamente. Aqui es cuando necesitamos devolver el token de acceso.
  - `onError(String errorMessage)`: Se ejecuta cuando la operación falla y devuelve un mensaje de error.


#### DataManagerCallbackList<User>
Se utiliza para manejar respuestas exitosas o errores en las operaciones que devuelven una lista de usuarios.

```java
public interface DataManagerCallbackList<User> {
    void onSuccess(List<User> users);
    void onError(String errorMessage);
}
```
##### Métodos:
  - `onSuccess(List<User> users)`: Se ejecuta cuando la operación se ha realizado correctamente. Aqui es cuando necesitamos devolver una lista de usuarios.
  - `onError(String errorMessage)`: Se ejecuta cuando la operación falla y devuelve un mensaje de error.

#### DataManagerCallbackWishlist<Wishlist>
Se utiliza para manejar respuestas exitosas o errores en las operaciones que devuelven una lista de wishlists.

```java
public interface DataManagerCallbackWishlist<Wishlist> {
    void onSuccess(List<Wishlist> wishlists);
    void onError(String errorMessage);
}
```
##### Métodos:
  - `onSuccess(List<Wishlist> wishlists)`: Se ejecuta cuando la operación se ha realizado correctamente. Aqui es cuando necesitamos devolver una lista de wishlists.
  - `onError(String errorMessage)`: Se ejecuta cuando la operación falla y devuelve un mensaje de error.


 ###### Ejemplo de como usar los callbacks:

```java
DataManagerAPI.getMyUser(context, new DataManagerAPI.DataManagerCallbackUser<User>() {
            @Override
            public void onSuccess(User user) {
                showMyUserFragment.showUserData(user);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_MY_USER", errorMessage);
            }
        });
```

Como podeis ver en el ejemplo, se usa el método `getMyUser` de la clase `DataManagerAPI` y se le pasa el contexto de la aplicación y un callback. 

El callback es una interfaz que implementa dos métodos, `onSuccess` y `onError`. El método `onSuccess` se ejecuta cuando la operación se ha realizado correctamente y devuelve el objeto User con la información del usuario. El método `onError` se ejecuta cuando la operación falla y devuelve un mensaje de error.
Este ejemplo es la que sirve para mostrar el perfil de usuario.
