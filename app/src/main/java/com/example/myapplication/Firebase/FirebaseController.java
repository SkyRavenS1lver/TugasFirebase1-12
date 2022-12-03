package com.example.myapplication.Firebase;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myapplication.Activity.ReadDataProduct;

import com.example.myapplication.Product.Product;
import com.example.myapplication.Product.ProductModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseController extends Application {
    private FirebaseDatabase database;
    public static DatabaseReference productReferences;


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        productReferences = database.getReference(Product.class.getSimpleName());
        getAllProduct();
    }

    public static void insertData(Product product){
        productReferences.push().setValue(product);
    }
    public static void deleteData(Product product){
        productReferences.child(product.getKey()).removeValue();
    }
    public static void updateData(Product product){
        productReferences.child(product.getKey()).setValue(Product.convertProduct(product));
    }



    public void getAllProduct() {
        productReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductModel.productArrayList.clear();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot currentData : snapshot.getChildren()) {
                        Product product = currentData.getValue(Product.class);
                        product.setKey(currentData.getKey());
                        ProductModel.productArrayList.add(product);
                    }
                }
                ReadDataProduct.refreshList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
