package com.example.high_tech_shop.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.CategoryAdapter;
import com.example.high_tech_shop.entity.Category;
import com.example.high_tech_shop.repositories.CategoryRepository;
import java.util.Collections;
import java.util.List;

public class CategoryManageActivity extends AppCompatActivity {

    private EditText edtCategoryId;
    private EditText edtCategoryName;
    private Button btnSaveCategory;
    private Button btnUpdateCategory;
    private Button btnDeleteCategory;
    private RecyclerView recyclerViewCategories;
    private CategoryRepository categoryRepository;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_manage);

        edtCategoryId = findViewById(R.id.edtCategoryId);
        edtCategoryName = findViewById(R.id.edtCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
        btnUpdateCategory = findViewById(R.id.btnUpdateCategory);
        btnDeleteCategory = findViewById(R.id.btnDeleteCategory);
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);

        categoryRepository = new CategoryRepository(this);
        categoryAdapter = new CategoryAdapter(Collections.emptyList());

        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategories.setAdapter(categoryAdapter);

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategory();
            }
        });

        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCategory();
            }
        });

        btnDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteCategory();
            }
        });

        loadCategories();
    }

    private void loadCategories() {
        List<Category> categories = categoryRepository.findAll();
        categoryAdapter.setCategories(categories);
    }

    private void saveCategory() {
        String name = edtCategoryName.getText().toString().trim();

        if (!name.isEmpty()) {
            Category category = new Category(0, name);
            categoryRepository.insert(Collections.singletonList(category));
            Toast.makeText(this, "Category saved successfully", Toast.LENGTH_SHORT).show();
            loadCategories(); // Reload categories to reflect changes
        } else {
            Toast.makeText(this, "Please enter category name", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCategory() {
        String idStr = edtCategoryId.getText().toString().trim();
        String name = edtCategoryName.getText().toString().trim();

        if (!idStr.isEmpty() && !name.isEmpty()) {
            int id = Integer.parseInt(idStr);
            Category category = categoryRepository.findById(id);
            if (category != null) {
                category.setName(name);
                categoryRepository.update(category);
                Toast.makeText(this, "Category updated successfully", Toast.LENGTH_SHORT).show();
                loadCategories(); // Reload categories to reflect changes
            } else {
                Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter category id and name", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeleteCategory() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this category?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCategory();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteCategory() {
        String idStr = edtCategoryId.getText().toString().trim();

        if (!idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            Category category = categoryRepository.findById(id);
            if (category != null) {
                categoryRepository.delete(category);
                Toast.makeText(this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                loadCategories(); // Reload categories to reflect changes
            } else {
                Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter category id", Toast.LENGTH_SHORT).show();
        }
    }
}
