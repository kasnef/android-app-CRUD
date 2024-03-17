package com.example.hello_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hello_android.entities.Product;
import com.example.hello_android.service.ProductService;
import com.example.hello_android.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ProductService productService;

    private EditText inputName, inputPrice, inputImagePath;
    private EditText updateName, updatePrice, updateImagePath;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitInstance.getInstance(); // call the server
        productService = retrofit.create(ProductService.class);

        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonDelete = findViewById(R.id.button_delete);
        Button buttonUpdate = findViewById(R.id.button_edit);
        ListView listView = findViewById(R.id.productListView);

        // Set an OnClickListener for the Add button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Dialog instance
                final Dialog dialog = new Dialog(MainActivity.this);
                // Set the content view of the dialog to the layout defined in dialog_add_product.xml
                dialog.setContentView(R.layout.dialog_add_product);
                // Find the Add Product button in the dialog layout
                Button buttonAddProduct = dialog.findViewById(R.id.button_add_product);
                // Find the Exit button in the dialog layout
                Button buttonExit = dialog.findViewById(R.id.button_exit);
                // Set an OnClickListener for the Add Product button
                buttonAddProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputName = dialog.findViewById(R.id.input_name);
                        inputPrice = dialog.findViewById(R.id.input_price);
                        inputImagePath = dialog.findViewById(R.id.input_image_path);

                        String productName = inputName.getText().toString();
                        double productPrice = Double.parseDouble(inputPrice.getText().toString());
                        String productImagePath = inputImagePath.getText().toString();

                        Product product = new Product(productName, productPrice, productImagePath);
                        addProduct(product, listView);
                        dialog.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                                loadProducts(listView, productService);
                            }
                        });
                    }
                });
                // Set an OnClickListener for the Exit button
                buttonExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // Show the dialog
                dialog.show();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Dialog instance
                final Dialog dialog = new Dialog(MainActivity.this);
                // Set the content view of the dialog to the layout defined in dialog_add_product.xml
                dialog.setContentView(R.layout.dialog_update_product);
                // Find the Add Product button in the dialog layout
                Button buttonUpdateProduct = dialog.findViewById(R.id.button_update_product);
                // Find the Exit button in the dialog layout
                Button buttonExitUpdate = dialog.findViewById(R.id.button_exit_update);
                // Set an OnClickListener for the Add Product button
                buttonUpdateProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateName = dialog.findViewById(R.id.update_name);
                        updatePrice = dialog.findViewById(R.id.update_price);
                        updateImagePath = dialog.findViewById(R.id.update_image_path);

                        String updateProductName = updateName.getText().toString();
                        double updateProductPrice = Double.parseDouble(updatePrice.getText().toString());
                        String updateroductImagePath = updateImagePath.getText().toString();

                        Product product = new Product(updateProductName, updateProductPrice, updateroductImagePath);
                        updateProduct(product, listView);
                        dialog.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                                loadProducts(listView, productService);
                            }
                        });
                    }
                });
                // Set an OnClickListener for the Exit button
                buttonExitUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // Show the dialog
                dialog.show();
            }
        });



        loadProducts(listView, productService);
    }

    private void loadProducts(ListView listView, ProductService productService) {

        Call<List<Product>> call = productService.getAllProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    ArrayAdapter<Product> adapter =
                            new ArrayAdapter<Product>(MainActivity.this,
                                    android.R.layout.simple_list_item_1, productList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("error: " + t.getMessage());
            }
        });
    }

    private void addProduct(Product product, ListView listView){
        Call<Product> call = productService.addProducts(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    loadProducts(listView, productService);
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                System.out.println("error: " + t.getMessage());
            }
        });
    }

    private void updateProduct(Product product, ListView listView){
        Call<Product> call = productService.updateProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    loadProducts(listView, productService);
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                System.out.println("error: " + t.getMessage());
            }
        });
    }

}