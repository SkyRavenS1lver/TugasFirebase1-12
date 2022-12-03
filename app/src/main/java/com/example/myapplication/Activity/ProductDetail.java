package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Firebase.FirebaseController;
import com.example.myapplication.Product.Product;
import com.example.myapplication.Product.ProductModel;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductDetail extends AppCompatActivity {
    FloatingActionButton edit,confirm,cancel,back;
    EditText name,brand,price,description;
    TextView judul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        back = findViewById(R.id.backButton);
        edit = findViewById(R.id.editData);
        confirm = findViewById(R.id.confirmButton);
        cancel = findViewById(R.id.cancelButton);
        name = findViewById(R.id.productName);
        brand = findViewById(R.id.productBrand);
        price = findViewById(R.id.productPrice);
        description = findViewById(R.id.productContent);
        Intent getter = getIntent();
        Intent intent = new Intent(ProductDetail.this, ReadDataProduct.class);
        judul = findViewById(R.id.Judul);
        judul.setText(getter.getStringExtra("judul"));
        String mode = getter.getStringExtra("mode");
        Product product = ProductModel.currentProduct;
        if(mode.equals("detail")) {
            changeState(false);
            String pName = product.getName();
            String pBrand = product.getBrand();
            String pPrice = String.valueOf(product.getPrice());
            String pDesc = product.getDesc();
            name.setText(pName);
            brand.setText(pBrand);
            price.setText(pPrice);
            description.setText(pDesc);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    judul.setText("Edit Data");
                    changeState(true);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            product.setName(name.getText().toString());
                            product.setPrice(Integer.parseInt(price.getText().toString()));
                            product.setBrand(brand.getText().toString());
                            product.setDesc(description.getText().toString());
                            FirebaseController.updateData(product);
                            changeState(false);
                            judul.setText("Detail Data");

                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            changeState(false);
                            name.setText(product.getName());
                            brand.setText(product.getBrand());
                            price.setText(String.valueOf(product.getPrice()));
                            description.setText(product.getDesc());
                            judul.setText("Detail Data");
                        }
                    });
                }
            });
        }
        else if(mode.equals("add")){
            changeState(true);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product newProduct = new Product();
                    newProduct.setName(name.getText().toString());
                    newProduct.setPrice(Integer.parseInt(price.getText().toString()));
                    newProduct.setBrand(brand.getText().toString());
                    newProduct.setDesc(description.getText().toString());
//                    FirebaseController.productReferences.push().setValue(product);
                    FirebaseController.insertData(newProduct);
                    startActivity(intent);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
    }
    public void changeState(Boolean bool){
        if (bool){
            edit.setVisibility(View.INVISIBLE);
            back.setVisibility(View.INVISIBLE);
            confirm.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        }
        else{
            edit.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
        }
        name.setFocusable(bool);
        name.setFocusableInTouchMode(bool);
        brand.setFocusable(bool);
        brand.setFocusableInTouchMode(bool);
        price.setFocusable(bool);
        price.setFocusableInTouchMode(bool);
        description.setFocusable(bool);
        description.setFocusableInTouchMode(bool);
    }
}
