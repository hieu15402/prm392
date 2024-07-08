package com.example.high_tech_shop.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.ProductAdAdapter;
import com.example.high_tech_shop.dao.ProductDAO;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductManageActivity extends AppCompatActivity {

    private EditText etName, etDescription, etPrice, etUnitInStock, etCategoryId;
    private ImageView ivCoverImage;
    private Button btnSave, btnCancel, btnSelectImage, btnAddProduct, btnPreviousPage, btnNextPage;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ProductAdAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private ProductDAO productDAO;
    private Product selectedProduct;
    private Bitmap selectedImageBitmap;

    private static final int REQUEST_SELECT_IMAGE = 1;
    private static final int ITEMS_PER_PAGE = 5;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etUnitInStock = findViewById(R.id.etUnitInStock);
        etCategoryId = findViewById(R.id.etCategoryId);
        ivCoverImage = findViewById(R.id.ivCoverImage);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnPreviousPage = findViewById(R.id.btnPreviousPage);
        btnNextPage = findViewById(R.id.btnNextPage);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        productDAO = HighTechShopRoomDatabase.getInstance(this).productDAO();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdAdapter(productList, this, null);
        recyclerView.setAdapter(productAdapter);

        loadProducts(currentPage);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddProductView(true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddProductView(false);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });

        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0) {
                    currentPage--;
                    loadProducts(currentPage);
                }
            }
        });

        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((currentPage + 1) * ITEMS_PER_PAGE < productDAO.getAll().size()) {
                    currentPage++;
                    loadProducts(currentPage);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProducts(newText);
                return false;
            }
        });

        productAdapter.setOnItemClickListener(new ProductAdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                selectedProduct = product;
                populateFields(product);
                toggleAddProductView(true); // Show fields for update/delete
            }
        });

        //delete
        productAdapter.setOnItemLongClickListener(new ProductAdAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Product product) {
                showDeleteConfirmationDialog(product);
            }
        });
    }

    private void showDeleteConfirmationDialog(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct(product);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteProduct(Product product) {
        productDAO.delete(product);
        Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show();
        loadProducts(currentPage);
    }

    private void toggleAddProductView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            findViewById(R.id.scrollView).setVisibility(View.VISIBLE);
            btnAddProduct.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.scrollView).setVisibility(View.GONE);
            clearFields();
            selectedProduct = null;
            btnAddProduct.setVisibility(View.VISIBLE);
        }
    }

    private void loadProducts(int page) {
        productList.clear();
        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, productDAO.getAll().size());
        productList.addAll(productDAO.getAll().subList(start, end));
        productAdapter.notifyDataSetChanged();
    }

    private void saveProduct() {
        if (validateFields()) {
            Product product = new Product(0,
                    etName.getText().toString(),
                    etDescription.getText().toString(),
                    Double.parseDouble(etPrice.getText().toString()),
                    Long.parseLong(etUnitInStock.getText().toString()),
                    selectedImageBitmap != null ? convertBitmapToString(selectedImageBitmap) : "", // Convert Bitmap to String
                    true,
                    Integer.parseInt(etCategoryId.getText().toString())
            );

            if (selectedProduct == null) {
                productDAO.insert(product);
                Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
            } else {
                product.setId(selectedProduct.getId());
                productDAO.update(product);
                Toast.makeText(this, "Product updated", Toast.LENGTH_SHORT).show();
            }

            loadProducts(currentPage);
            toggleAddProductView(false);
        }
    }

    private void searchProducts(String query) {
        productList.clear();
        productList.addAll(productDAO.getProductsByCategoryId(Integer.parseInt(query)));  // Assuming searching by categoryId
        productAdapter.notifyDataSetChanged();
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(etName.getText()) ||
                TextUtils.isEmpty(etDescription.getText()) ||
                TextUtils.isEmpty(etPrice.getText()) ||
                TextUtils.isEmpty(etUnitInStock.getText()) ||
                TextUtils.isEmpty(etCategoryId.getText())) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        etName.setText("");
        etDescription.setText("");
        etPrice.setText("");
        etUnitInStock.setText("");
        etCategoryId.setText("");
        selectedImageBitmap = null;
        ivCoverImage.setImageResource(R.drawable.ic_addimg); // Reset to placeholder image
    }

    private void populateFields(Product product) {
        etName.setText(product.getName());
        etDescription.setText(product.getDescription());
        etPrice.setText(String.valueOf(product.getPrice()));
        etUnitInStock.setText(String.valueOf(product.getUnitInStock()));
        etCategoryId.setText(String.valueOf(product.getCategoryId()));

        selectedImageBitmap = convertStringToBitmap(product.getCoverImage()); // Convert String to Bitmap
        ivCoverImage.setImageBitmap(selectedImageBitmap);
    }

    private void selectImageFromGallery() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_SELECT_IMAGE);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                selectedImageBitmap = bitmap; // Save selected image Bitmap
                ivCoverImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SELECT_IMAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permission denied to access gallery", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap convertStringToBitmap(String str) {
        try {
            byte[] byteArray = Base64.decode(str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
