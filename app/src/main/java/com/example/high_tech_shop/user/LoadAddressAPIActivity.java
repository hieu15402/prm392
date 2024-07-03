package com.example.high_tech_shop.user;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

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
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.map.LocationHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadAddressAPIActivity extends AppCompatActivity {
    private String[] location;
    private User user;
    private LocationHelper locationHelper;
    private Spinner spinnerProvince, spinnerDistrict, spinnerWard;
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
        String address = null;
        if (intent.hasExtra("location")) {
            location = intent.getStringExtra("location").split(",");
            address = intent.getStringExtra("_location");
        }
        String finalAddress = address;
        btnBackAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadAddressAPIActivity.this, AddAddressActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("addressUpdate", selectedProvince + "," + selectedDistrict + "," + selectedWard);
                intent.putExtra("_addressUpdate", finalAddress);
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
        Intent intent = getIntent();
        int locationId = 0;
        String provinceName = "";
        provinceNames.clear();
        if (intent.hasExtra("location")) {
            provinceName = location[0];
            provinceNames.add(location[0]);
        }
        for (Province province : provinces) {
            if (intent.hasExtra("location")) {
                if (province.getProvinceName().equals(location[0])) {
                    locationId = province.getProvinceID();
                } else {
                    provinceNames.add(province.getProvinceName());
                }
            } else {
                provinceNames.add(province.getProvinceName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinceNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapter);
        String finalProvinceName = provinceName;
        int finalLocationId = locationId;
        String finalProvinceName1 = provinceName;
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (intent.hasExtra("location")) {
                    selectedProvince = finalProvinceName1;
                    loadDistricts(finalLocationId);
                } else {
                    selectedProvince = provinces.get(position).getProvinceName();
                    loadDistricts(provinces.get(position).getProvinceID());
                }

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
        Intent intent = getIntent();
        districtNames.clear();
        int locationId = 0;
        String districtName = "";
        if (intent.hasExtra("location")) {
            districtName = location[1];
            districtNames.add(location[1]);
        }
        for (District district : districts) {
            if (intent.hasExtra("location")) {
                if (district.getDistrictName().equals(location[1])) {
                    locationId = district.getDistrictID();
                } else {
                    districtNames.add(district.getDistrictName());
                }
            } else {
                districtNames.add(district.getDistrictName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter);

        String finalDistrictName = districtName;
        int finalLocationId = locationId;
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                if (intent.hasExtra("location")) {
                    selectedDistrict = finalDistrictName;
                    loadWards(finalLocationId);
                } else {
                    selectedDistrict = districts.get(position).getDistrictName();
                    loadWards(districts.get(position).getDistrictID());
                }
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
        Intent intent = getIntent();
        wardNames.clear();
        String wardName = "";
        if (intent.hasExtra("location")) {
            wardName = location[2];
            wardNames.add(location[2]);
        }
        for (Ward ward : wards) {
            if (intent.hasExtra("location")) {
                if (ward.getWardName().equals(location[2])) {
                } else {
                    wardNames.add(ward.getWardName());
                }
            } else {
                wardNames.add(ward.getWardName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wardNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWard.setAdapter(adapter);
        String finalWardName = wardName;
        spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (intent.hasExtra("location")) {
                    selectedWard = finalWardName;
                } else {
                    selectedWard = wards.get(position).getWardName();
                }
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