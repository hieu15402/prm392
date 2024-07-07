package com.example.high_tech_shop.user.user;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.addressAPI.District;
import com.example.high_tech_shop.addressAPI.Province;
import com.example.high_tech_shop.addressAPI.Ward;
import com.example.high_tech_shop.api.ApiClient;
import com.example.high_tech_shop.api.ApiResponse;
import com.example.high_tech_shop.api.GHNApi;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.map.LocationHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadAddressAPIActivity extends AppCompatActivity {
    private  User user;
    private LocationHelper locationHelper;
    private Spinner spinnerProvince, spinnerDistrict, spinnerWard;
    private EditText etAddress;
    private Button btnSave;
    private GHNApi ghnApi;
    private List<String> provinceNames = new ArrayList<>();
    private List<String> districtNames = new ArrayList<>();
    private List<String> wardNames = new ArrayList<>();
    private String selectedProvince, selectedDistrict, selectedWard;
    private final String token = "c4cb7ff5-36d5-11ef-8e53-0a00184fe694";
    private LinearLayout btnLocation;
    private ImageView location_icon;
    private TextView location_text;
    private ImageView btnBackAddNewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_address_api_activity);

        spinnerProvince = findViewById(R.id.spinnerProvince);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerWard = findViewById(R.id.spinnerWard);
        btnSave = findViewById(R.id.btnSave);
        btnLocation = findViewById(R.id.btnLocation);
        location_icon = findViewById(R.id.location_icon);
        location_text = findViewById(R.id.location_text);
        btnBackAddNewAddress = findViewById(R.id.btnBackAddNewAddress);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        btnBackAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadAddressAPIActivity.this, AddAddressActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        ghnApi = ApiClient.getClient().create(GHNApi.class);
        loadProvinces();
        locationHelper = new LocationHelper(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });


        location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoadAddressAPIActivity.this, ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoadAddressAPIActivity.this,
                            new String[]{ACCESS_FINE_LOCATION},
                            1);
                } else {
                    getLocation();

                }
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoadAddressAPIActivity.this, ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoadAddressAPIActivity.this,
                            new String[]{ACCESS_FINE_LOCATION},
                            1);
                } else {
                    getLocation();
                }
            }
        });
        location_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoadAddressAPIActivity.this, ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoadAddressAPIActivity.this,
                            new String[]{ACCESS_FINE_LOCATION},
                            1);
                } else {
                    getLocation();
                }
            }
        });
    }

    private void loadProvinces() {
        Call<ApiResponse<List<Province>>> call = ghnApi.getProvinces(token);
        call.enqueue(new Callback<ApiResponse<List<Province>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Province>>> call, Response<ApiResponse<List<Province>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Province>> apiResponse = response.body();
                    if (apiResponse.getCode() == 200 && apiResponse.getData() != null) {
                        updateProvinceSpinner(apiResponse.getData());
                    } else {
                        Toast.makeText(LoadAddressAPIActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("API_ERROR", "Error: " + apiResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Province>>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
            }
        });
    }

    private void updateProvinceSpinner(List<Province> provinces) {
        provinceNames.clear();
        for (Province province : provinces) {
            provinceNames.add(province.getProvinceName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinceNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProvince = provinces.get(position).getProvinceName();
                loadDistricts(provinces.get(position).getProvinceID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadDistricts(Integer provinceId) {
        ghnApi.getDistricts(token, provinceId).enqueue(new Callback<ApiResponse<List<District>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<District>>> call, Response<ApiResponse<List<District>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse<List<District>> apiResponse = response.body();
                        if (apiResponse.getCode() == 200 && apiResponse.getData() != null) {
                            updateDistrictSpinner(apiResponse.getData());
                        } else {
                            Toast.makeText(LoadAddressAPIActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("API_ERROR", "Error: " + apiResponse.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<District>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void updateDistrictSpinner(List<District> districts) {
        districtNames.clear();
        for (District district : districts) {
            districtNames.add(district.getDistrictName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter);
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districts.get(position).getDistrictName();
                loadWards(districts.get(position).getDistrictID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadWards(Integer districtId) {
        ghnApi.getWards(token, districtId).enqueue(new Callback<ApiResponse<List<Ward>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Ward>>> call, Response<ApiResponse<List<Ward>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Ward>> apiResponse = response.body();
                    if (apiResponse.getCode() == 200 && apiResponse.getData() != null) {
                        updateWardSpinner(apiResponse.getData());
                    } else {
                        Toast.makeText(LoadAddressAPIActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("API_ERROR", "Error: " + apiResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Ward>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void updateWardSpinner(List<Ward> wards) {
        wardNames.clear();
        for (Ward ward : wards) {
            wardNames.add(ward.getWardName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wardNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWard.setAdapter(adapter);
        spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWard = wards.get(position).getWardName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void saveAddress() {
        if (selectedProvince != null && selectedDistrict != null && selectedWard != null) {
            Intent intent = new Intent(LoadAddressAPIActivity.this, AddAddressActivity.class);
            intent.putExtra("address", selectedProvince + "," + selectedDistrict + "," + selectedWard);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Vui lòng chọn đầy đủ thông tin và nhập địa chỉ chi tiết", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLocation() {
        locationHelper.getCurrentLocation(new LocationHelper.LocationCallback() {
            @Override
            public void onLocationResult(String province, String district, String ward) {
                // Xử lý kết quả ở đây
                Intent intent = new Intent(LoadAddressAPIActivity.this, AddAddressActivity.class);
                if (district == null && ward == null) {
                    intent.putExtra("address", province);
                }
                if (district != null && ward == null) {
                    intent.putExtra("address", province + "," + district);
                }
                if (district != null && ward != null) {
                    intent.putExtra("address", province + "," + district + "," + ward);
                }
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String error) {
                Log.e("Location", "Lỗi: " + error);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Cần quyền truy cập vị trí để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
}