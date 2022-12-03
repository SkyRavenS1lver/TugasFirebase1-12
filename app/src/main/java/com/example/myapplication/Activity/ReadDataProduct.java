package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Product.ProductAdapter;
import com.example.myapplication.Product.ProductModel;
import com.example.myapplication.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReadDataProduct extends AppCompatActivity {
    public static TextView message;
    public static RecyclerView rvProduct;
    FloatingActionButton addData;
    ProductAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        message = findViewById(R.id.messages);
        rvProduct = findViewById(R.id.rvProduct);
        addData = findViewById(R.id.addData);
        dataChange();
        adapter = new ProductAdapter(this, ProductModel.productArrayList);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.setAdapter(adapter);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadDataProduct.this, ProductDetail.class);
                intent.putExtra("mode", "add");
                intent.putExtra("judul", "Add Data");
                startActivity(intent);
            }
        });
    }
   public static void dataChange() {
       if (ProductModel.productArrayList.size() > 0){
           message.setVisibility(View.INVISIBLE);
       }
       else {message.setVisibility(View.VISIBLE);}
    }
    public static ProductAdapter getRvAdaper(){
        return (ProductAdapter) rvProduct.getAdapter();
    }
    public static void refreshList(){
        getRvAdaper().notifyDataSetChanged();
        dataChange();
    }

}
