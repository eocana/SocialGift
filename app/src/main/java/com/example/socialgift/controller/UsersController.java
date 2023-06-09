package com.example.socialgift.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.datamanager.DataManagerSocial;
import com.example.socialgift.model.Gift;
import com.example.socialgift.model.User;
import com.example.socialgift.model.Wishlist;
import com.example.socialgift.view.myuser.fragments.EditUserFragment;
import com.example.socialgift.view.LoginActivity;
import com.example.socialgift.view.RegisterActivity;
import com.example.socialgift.view.myuser.fragments.ShowMyUserFragment;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UsersController {

    private ShowMyUserFragment showMyUserFragment;
    private RegisterActivity registerActivity;

    private EditUserFragment editUserFragment;
    private LoginActivity loginActivity;
    private Context context;

    private List<Wishlist> myWishlists = new ArrayList<>();

    public interface DataManagerCallback<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }

    /**
     * Constructor de la clase UsersController cuando quiero registrarme
     * @param registerActivity Fragmento que implementa la interfaz ShowMyUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(RegisterActivity registerActivity, Context context) {
        this.registerActivity = registerActivity;
        this.context = context;
    }

    /**
     * Constructor de la clase UsersController cuando quiero loggearme
     * @param loginActivity Fragmento que implementa la interfaz ShowMyUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(LoginActivity loginActivity, Context context) {
        this.loginActivity = loginActivity;
        this.context = context;
    }


    /**
     * Constructor de la clase UsersController cuando quiero mostrar mi usuario
     * @param showMyUserFragment Fragmento que implementa la interfaz ShowMyUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(ShowMyUserFragment showMyUserFragment, Context context) {
        this.showMyUserFragment = showMyUserFragment;
        this.context = context;

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


    }

    /**
     * Constructor de la clase UsersController cuando quiero editar mi usuario
     * @param editUserFragment Fragmento que implementa la interfaz EditUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(EditUserFragment editUserFragment, Context context){
        this.context = context;
        this.editUserFragment = editUserFragment;
        editUserFragment.updateUserData(DataManagerAPI.getObjectUser());
    }

    /**
     * Método para cerrar sesión
     */
    public void signOut(ShowMyUserFragment showMyUserFragment) {
        DataManagerAPI.logOut();
        showMyUserFragment.goToLoginActivity();
    }

    /**
     * Método para crear un usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @param firstName Nombre del usuario
     * @param lastName Apellido del usuario
     * @param preUrl Url de la imagen del usuario
     */
    public void createUser(String email, String password, String firstName, String lastName, Uri preUrl) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new UploadImageTask(context, preUrl));
        try {
            String imageUrl = future.get();
            if (imageUrl != null) {
                DataManagerAPI.createUser(firstName, lastName, email, password, imageUrl, context, new DataManagerAPI.DataManagerCallback() {
                    @Override
                    public void onSuccess() {
                        registerActivity.navigateToLoginActivity();
                        Log.d("API_CREATE_USER", "Usuario creado correctamente");
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API_CREATE_USER", errorMessage);
                    }
                });
            } else {
                // Manejar el error de la carga de la imagen
                Log.d("API_UPLOAD_IMAGE(C)", "Error en udpateUser el path= "+imageUrl);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }
    /**
     * Método para actualizar un usuario
     * @param firstName Nombre del usuario
     * @param lastName Apellido del usuario
     * @param imageUri Url de la imagen del usuario
     */
    public void updateUser(String firstName, String lastName, Uri imageUri) {
        if (imageUri != null){
            // Llamada al método updateUser del DataManagerAPI
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(new UploadImageTask(context, imageUri));

            try {
                String imageUrl = future.get();
                if (imageUrl != null) {
                    DataManagerAPI.updateUser(firstName, lastName, imageUrl, context, new DataManagerAPI.DataManagerCallback() {
                        @Override
                        public void onSuccess() {
                            editUserFragment.showSuccessMessage();
                            Log.d("API_UPDATE_USER", "Usuario actualizado correctamente");
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("API_UPDATE_USER", errorMessage);
                        }
                    });
                } else {
                    // Manejar el error de la carga de la imagen
                    Log.d("API_UPLOAD_IMAGE(C)", "Error en udpateUser el path= "+imageUrl);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        }else{
            //El usuario no ha subido ninguna imagen
            DataManagerAPI.updateUser(firstName, lastName, DataManagerAPI.getObjectUser().getImage(), context, new DataManagerAPI.DataManagerCallback() {
                @Override
                public void onSuccess() {
                    editUserFragment.showSuccessMessage();
                    Log.d("API_SUCCESS_UPDATE_USER", "Usuario actualizado correctamente");
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("API_ERROR_UPDATE_USER", errorMessage);
                }
            });
        }


    }
    /**
     * Método para loggearse
     * @param email Email del usuario
     * @param password Contraseña del usuario
     */
    public void loginUser(String email, String password) {
        DataManagerAPI.loginUser(email, password, context, new DataManagerAPI.DataManagerCallbackLogin() {
            @Override
            public void onSuccess(String accessToken) {
                Log.d("API_LOGIN_USER", "Mi token de acceso es: " + accessToken);
                //saveAccessToken(accessToken);
                loginActivity.onLoginSuccess();
            }

            @Override
            public void onError(String errorMessage) {
                loginActivity.onLoginError(errorMessage);
            }
        });
    }



    public void getWishlistsCount(DataManagerCallback<Integer> callback) {
        DataManagerAPI.wishlistsMyUser(context, new DataManagerCallbacks.DataManagerCallbackWishlists<Wishlist>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                int count = wishlists.size();
                callback.onSuccess(count);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
    public void getReservedGiftsCount(DataManagerCallback<Integer> callback) {
        DataManagerAPI.getGiftsReserved(DataManagerAPI.getObjectUser().getId(), context, new DataManagerCallbacks.DataManagerCallbackListGift<Gift>() {
            @Override
            public void onSuccess(List<Gift> gifts) {
                int count = gifts.size();
                callback.onSuccess(count);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
    public void getFriendsCount(DataManagerCallback<Integer> callback) {
        DataManagerSocial.getUserFriends(DataManagerAPI.getObjectUser().getId(), context, new DataManagerCallbacks.DataManagerCallbackUserList<User>() {
            @Override
            public void onSuccess(List<User> users) {
                int count = users.size();
                callback.onSuccess(count);
            }
            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    public void getClosestWishlists(DataManagerCallback<List<Wishlist>> callback) {

        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        DataManagerAPI.wishlistsMyUser(context, new DataManagerCallbacks.DataManagerCallbackWishlists<Wishlist>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                // Ordenar las wishlists por fecha de finalización (de la más cercana a la más lejana)
                wishlists.sort(new Comparator<Wishlist>() {
                    @Override
                    public int compare(Wishlist wishlist1, Wishlist wishlist2) {
                        Date endDate1 = wishlist1.getEndDate(); // Reemplaza esto con el método adecuado para obtener la fecha de finalización de la wishlist
                        Date endDate2 = wishlist2.getEndDate(); // Reemplaza esto con el método adecuado para obtener la fecha de finalización de la wishlist

                        if (endDate1 != null && endDate2 != null) {
                            return endDate1.compareTo(endDate2);
                        }

                        // Si alguna de las fechas de finalización es nula, coloca la wishlist sin fecha al final
                        if (endDate1 == null && endDate2 == null) {
                            return 0;
                        } else if (endDate1 == null) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                // Obtener las primeras 3 wishlists (o menos si hay menos de 3)
                List<Wishlist> closestWishlists = new ArrayList<>();
                int maxWishlists = Math.min(wishlists.size(), 3);
                // Filtrar las wishlists con fechas pasadas
                for (Wishlist wishlist : wishlists) {
                    Date endDate = wishlist.getEndDate();


                    // Verificar si la fecha de finalización es posterior a la fecha actual
                    if (endDate != null && endDate.after(currentDate)) {
                        closestWishlists.add(wishlist);
                    }
                }

                // Llamar al callback con las wishlists más cercanas
                callback.onSuccess(closestWishlists);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }



    //TODO: Mover a una clase aparte
    private class UploadImageTask implements Callable<String> {
        private Uri imageUri;
        private Context context;

        public UploadImageTask(Context context, Uri imageUri) {
            this.context = context;
            this.imageUri = imageUri;
        }

        @Override
        public String call() throws Exception {
            try {
                String mimeType = context.getContentResolver().getType(imageUri);
                if (mimeType == null) {
                    Log.e("UploadImageTask", "MIME type is null. Uri might be invalid.");
                    return null;
                }
                MediaType mediaType = MediaType.parse(mimeType);
                if (mediaType == null) {
                    Log.e("UploadImageTask", "Cannot parse MIME type: " + mimeType);
                    return null;
                }

                InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
                if (inputStream == null) {
                    Log.e("UploadImageTask", "Unable to obtain input stream from URI");
                    return null;
                }

                File imageFile = getFileFromContentUri(imageUri, context);
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("myFile", imageFile.getName(),RequestBody.create(mediaType, imageFile)).build();

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://balandrau.salle.url.edu/i3/repositoryimages/uploadfile")
                        .method("POST", requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Log.e("UploadImageTask", "Upload image request not successful. Code: " + response.code());
                    Log.e("UploadImageTask", "Response (error) from server: " + response.body().string());
                } else {
                    String responseString = response.body().string();  // Guarda el cuerpo de la respuesta en una variable.
                    JSONObject responseJson = new JSONObject(responseString);
                    String url = responseJson.getJSONObject("data").getString("url");
                    return url;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("UploadImageTask", "IOException occurred", e);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("UploadImageTask", "Exception occurred", e);
            }
            return null;
        }
    }

    //TODO: Mover a una clase aparte (UploadImageTask)
    public File getFileFromContentUri(Uri contentUri, Context context) throws IOException {
        // Abrir el flujo de entrada de la URI de contenido
        InputStream inputStream = context.getContentResolver().openInputStream(contentUri);

        // Crear un archivo temporal en el directorio de caché de la aplicación
        String filename = "tempfile.jpg";
        File tempFile = new File(context.getCacheDir(), filename);
        tempFile.createNewFile();

        // Copiar los datos al archivo temporal
        OutputStream outputStream = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            outputStream = Files.newOutputStream(tempFile.toPath());
        }
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();

        return tempFile;
    }

}
