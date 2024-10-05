package com.example.mysql_demo_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.widget.ListView;

import com.example.mysql_demo_project.interfaces.IProductRepository;
import com.example.mysql_demo_project.repos.ProductRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ListView lvProducts;
    private ProductsAdapter productsAdapter;
    private IProductRepository productRepository;

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

        productRepository = new ProductRepository();

        loadProducts();

    }

    public void loadProducts(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->{
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