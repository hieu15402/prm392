package com.example.high_tech_shop.map;

import android.content.Context;
import android.location.*;
import com.google.android.gms.location.*;

import java.io.IOException;
import java.util.*;

public class LocationHelper {
    private final Context context;
    private final FusedLocationProviderClient fusedLocationClient;

    public LocationHelper(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public interface LocationCallback {
        void onLocationResult(String province, String district, String ward);
        void onError(String error);
    }

    public void getCurrentLocation(LocationCallback callback) {
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            getAddressFromLocation(location, callback);
                        } else {
                            callback.onError("Không thể lấy vị trí");
                        }
                    });
        } catch (SecurityException e) {
            callback.onError("Không có quyền truy cập vị trí");
        }
    }

    private void getAddressFromLocation(Location location, LocationCallback callback) {
        Geocoder geocoder = new Geocoder(context, new Locale("vi", "VN"));
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String province = address.getAdminArea();
                String district = address.getSubAdminArea();
                String ward = address.getLocality();
                callback.onLocationResult(province, district, ward);
            } else {
                callback.onError("Không tìm thấy địa chỉ");
            }
        } catch (IOException e) {
            callback.onError("Lỗi khi lấy địa chỉ: " + e.getMessage());
        }
    }
}
