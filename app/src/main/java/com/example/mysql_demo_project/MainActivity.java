package com.example.mysql_demo_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mysql_demo_project.interfaces.IProductRepository;
import com.example.mysql_demo_project.repos.ProductRepository;
import com.example.mysql_demo_project.utils.DialogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ListView lvProducts;
    private ProductsAdapter productsAdapter;
    private IProductRepository productRepository;
    private Button btnDelete, btnAdd, btnEdit, btnSearch;
    private EditText etName, etPrice;

    private void performDeleteProduct(int productId) {

        if (productId <= 0) {
            return;
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            try {
                productRepository.deleteProduct(productsAdapter.selectingId);
                ArrayList<Product> products = productRepository.getProducts();

                runOnUiThread(() -> {
                    DialogUtils.toastMessage(MainActivity.this, "Deleted product!");

                    productsAdapter.setProducts(products);
                    productsAdapter.selectingId = 0;
                    productsAdapter.notifyDataSetChanged();
                });

            } catch (Exception ex) {
                Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));
            }
        });

    }

    private void performCreateProduct() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                productRepository.createProduct(etName.getText().toString(), Double.parseDouble(etPrice.getText().toString()));
                ArrayList<Product> products = productRepository.getProducts();

                runOnUiThread(() -> {
                    DialogUtils.toastMessage(MainActivity.this, "Created product!");

                    productsAdapter.setProducts(products);
                    productsAdapter.selectingId = 0;
                    productsAdapter.notifyDataSetChanged();
                });

            } catch (Exception ex) {
                Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));
            }
        });

    }

    private void performUpdateProduct(int productId) {
        if (productId <= 0) {
            return;
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            try {
                String updatedName = etName.getText().toString();
                double updatedPrice = Double.parseDouble(etPrice.getText().toString());
                productRepository.updateProduct(productId, updatedName, updatedPrice);
                ArrayList<Product> products = productRepository.getProducts();

                runOnUiThread(() -> {
                    DialogUtils.toastMessage(MainActivity.this, "Updated product!");

                    productsAdapter.setProducts(products);
                    productsAdapter.notifyDataSetChanged();
                });
            } catch (Exception ex) {
                Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));
            }
        });
    }

    private void performSearchProduct() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                // Pass the search text from EditText to searchProduct
                String searchText = etName.getText().toString();
                ArrayList<Product> products = productRepository.searchProduct(searchText);

                runOnUiThread(() -> {
                    DialogUtils.toastMessage(MainActivity.this, "Search complete!");

                    productsAdapter.setProducts(products);
                    productsAdapter.selectingId = 0;
                    productsAdapter.notifyDataSetChanged();
                });

            } catch (Exception ex) {
                Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));
            } finally {
                // Shut down the ExecutorService after the task is complete
                executorService.shutdown();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Binding
        lvProducts = findViewById(R.id.lvProducts);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnAdd = findViewById(R.id.btnAdd);
        btnSearch = findViewById(R.id.btnSearch);
        etPrice = findViewById(R.id.etPrice);
        etName = findViewById(R.id.etName);

        productRepository = new ProductRepository();

        loadProducts();

        lvProducts.setOnItemClickListener((adapterView, view, i, l) -> {
            productsAdapter.selectingId = (int) productsAdapter.getItemId(i);
            productsAdapter.notifyDataSetChanged();
        });

        btnDelete.setOnClickListener(view -> {
            DialogUtils.showConfirmationDialog(MainActivity.this, "Confirm", "Delete this product?",
                    () -> performDeleteProduct(productsAdapter.selectingId));
        });
        btnAdd.setOnClickListener(view -> {
            performCreateProduct();
        });
        btnSearch.setOnClickListener(view -> {
            performSearchProduct();
        });

        btnEdit.setOnClickListener(view -> {
            DialogUtils.showConfirmationDialog(MainActivity.this, "Confirm", "Update this product?",
                    () -> performUpdateProduct(productsAdapter.selectingId));
        });

    }

    public void loadProducts() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {

                ArrayList<Product> products = productRepository.getProducts();

                runOnUiThread(() -> {
                    productsAdapter = new ProductsAdapter(MainActivity.this, products);

                    lvProducts.setAdapter(productsAdapter);
                });

            } catch (Exception ex) {
                Log.e("error", Arrays.toString(ex.getStackTrace()));
            }

//            runOnUiThread(() -> {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e){
//                    Log.e("error", Arrays.toString(e.getStackTrace()));
//                }
//            });
        });
    }
}