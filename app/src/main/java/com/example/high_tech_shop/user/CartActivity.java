package com.example.high_tech_shop.user;


import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static java.lang.Math.round;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.CartAdapter;
import com.example.high_tech_shop.entity.Cart;
import com.example.high_tech_shop.entity.CartItem;
import com.example.high_tech_shop.entity.Order;
import com.example.high_tech_shop.entity.OrderItem;
import com.example.high_tech_shop.entity.Payment;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.helper.NotificationPermissionHelper;
import com.example.high_tech_shop.repositories.CartItemRepository;
import com.example.high_tech_shop.repositories.CartRepository;
import com.example.high_tech_shop.repositories.OrderItemRepository;
import com.example.high_tech_shop.repositories.OrderRepository;
import com.example.high_tech_shop.repositories.PaymentRepository;
import com.example.high_tech_shop.repositories.ProductRepository;
import com.example.high_tech_shop.repositories.UserAddressRepository;
import com.google.android.gms.maps.model.LatLng;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import vn.momo.momo_partner.AppMoMoLib;


public class CartActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LatLng locationShop;
    private LatLng locationUser;
    private static final String API_KEY = "AIzaSyDoQl-bgPLcPh7cus33QksS4iffjQ2xXg4";
    private User user;
    private RecyclerView.Adapter adapter;
    private TextView subTotalTxt, deliveryTxt, taxTxt, totalTxt,tvAddress,tvPaymentType;
    private RecyclerView recyclerView;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private UserAddressRepository userAddressRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private PaymentRepository paymentRepository;
    private ProductRepository productRepository;
    private ImageView ivViewAddress,ivSelectPayment;
    private List<CartItem> tmp;
    ImageView backBtn;
    private Button orderButton;
    String province, district, ward, address;
    private double shipPrice = 0;
    private int paymentId = 0;


    //Momo
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Demo SDK";
    private String merchantCode = "MOMO5RGX20191128";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán hóa đơn High Tech Shop";
    //------------------------------------------------
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        deliveryTxt = findViewById(R.id.tvShip);


        orderRepository = new OrderRepository(this);
        orderItemRepository = new OrderItemRepository(this);
        paymentRepository = new PaymentRepository(this);
        productRepository = new ProductRepository(this);


        user = (User) getIntent().getSerializableExtra("user");
        if(user == null) finish();
        cartRepository = new CartRepository(this);
        cartItemRepository = new CartItemRepository(this);
        calculateShip();
        Cart cart = cartRepository.getCartByUserIdTrue(user.getId());
        if(cart == null ) {
            Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("returnFromCart", true);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            List<CartItem> cartItemList = cartItemRepository.getCartItemByCartId(cart.getId());
            tmp = cartItemList;
            subTotalTxt = findViewById(R.id.textView21);
            taxTxt = findViewById(R.id.textView23);
            totalTxt = findViewById(R.id.textView25);
            backBtn = findViewById(R.id.backBtn);
            recyclerView = findViewById(R.id.viewMyCart);
            calculateShip();
            adapter = new CartAdapter(cartItemList,this,cart,shipPrice);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("returnFromCart", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            subTotalTxt.setText("$0");
            totalTxt.setText("$0");
            taxTxt.setText("$0");
        }


        ivViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, SelectAddressActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        ivSelectPayment = findViewById(R.id.ivSelectPayment);
        tvPaymentType = findViewById(R.id.tvPaymentType);
        ivSelectPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentOptions();
            }
        });
        orderButton = findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPaymentType.getText().toString() == "Cash") {
                    Toast.makeText(CartActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                }else{
                    switch (tvPaymentType.getText().toString()) {
                        case "COD":
                            handleCODOrder();
                            break;
                        case "Momo":
                            handleMomoOrder();
                    }
                }


            }
        });


        //-----------MOMO----------------
    }
    public void update(String _subTotalTxt,String _total, String _tax){
        subTotalTxt.setText(_subTotalTxt);
        totalTxt.setText(_total);
        taxTxt.setText(_tax);
    }
    private void showPaymentOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn phương thức thanh toán");


        String[] options = {"COD", "Momo"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Handle COD option
                        handleCODOption();
                        break;
                    case 1:
                        // Handle Momo option
                        handleMomoOption();
                        break;
                }
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void handleCODOption() {
        tvPaymentType.setText("COD");
    }


    private void handleMomoOption() {
        tvPaymentType.setText("Momo");
    }
    private LatLng getLocationFromName(String locationName) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        LatLng location = null;
        try {
            addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                // Sử dụng tọa độ
                location = new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // Radius of the Earth in meters
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in meters
    }
    private void calculateShip() {
        String shopAddress = "Lô E2a-7, Đường D1, Đ. D1, Long Thạnh Mỹ, Thành Phố Thủ Đức, Hồ Chí Minh";
        locationShop = getLocationFromName(shopAddress);
        ivViewAddress = findViewById(R.id.ivViewAddress);
        tvAddress = findViewById(R.id.tvAddress);
        Intent _intent = getIntent();
        if (_intent.hasExtra("address")) {
            tvAddress.setText(_intent.getStringExtra("address"));
            String location = _intent.getStringExtra("address");
            String[] addressParts = location.split(",");
            if (addressParts.length > 0) {
                province = addressParts[0];
            }else{
                province = null;
            }
            if (addressParts.length > 1) {
                district = addressParts[1];
            }else{
                district = null;
            }
            if (addressParts.length > 2) {
                ward = addressParts[2];
            }else{
                ward = null;
            }
            locationUser = getLocationFromName(_intent.getStringExtra("address"));
            double distance = calculateDistance(locationShop.latitude, locationShop.longitude, locationUser.latitude, locationUser.longitude);
            shipPrice = round(distance*0.01);
            deliveryTxt.setText("$" + shipPrice);
        }else{
            userAddressRepository = new UserAddressRepository(this);
            UserAddress userAddress = userAddressRepository.getUserAddressByUserIdDefault(user.getId());
            province = userAddress.getProvince();
            district = userAddress.getDistrict();
            ward = userAddress.getWard();
            address = userAddress.getAddress();
            if (userAddress.getDistrict() == null && userAddress.getWard() == null) {
                tvAddress.setText(userAddress.getProvince());
                locationUser = getLocationFromName(userAddress.getProvince());
                double distance = calculateDistance(locationShop.latitude, locationShop.longitude, locationUser.latitude, locationUser.longitude);
                shipPrice = round(distance*0.01);
                deliveryTxt.setText("$"+ shipPrice);
            } else if (userAddress.getDistrict() != null && userAddress.getWard() == null) {
                tvAddress.setText(userAddress.getProvince() + "," + userAddress.getDistrict());
                locationUser = getLocationFromName(userAddress.getProvince() + "," + userAddress.getDistrict());
                double distance = calculateDistance(locationShop.latitude, locationShop.longitude, locationUser.latitude, locationUser.longitude);
                shipPrice = round(distance*0.01);
                deliveryTxt.setText("$"+ shipPrice);
            } else if (userAddress.getDistrict() != null && userAddress.getWard() != null) {
                tvAddress.setText(userAddress.getProvince() + "," + userAddress.getDistrict() + "," + userAddress.getWard());
                locationUser = getLocationFromName(userAddress.getProvince() + "," + userAddress.getDistrict() + "," + userAddress.getWard());
                double distance = calculateDistance(locationShop.latitude, locationShop.longitude, locationUser.latitude, locationUser.longitude);
                shipPrice = round(distance*0.01);
                deliveryTxt.setText("$"+ shipPrice);
            }
        }
    }
    private class LatLng {
        double latitude;
        double longitude;


        LatLng(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
    private void handleCODOrder() {
        String total = totalTxt.getText().toString().replace("$", "");
        Order order = new Order(
                orderRepository.getAll().size() + 1,
                Double.parseDouble(total),
                "Order Success",
                null,
                null,
                province,
                district,
                ward,
                address,
                null,
                user.getId(),
                0
        );
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orderRepository.insert(orders);
        List<OrderItem> orderItems = new ArrayList<>();
        int idIndex = 1;
        for (CartItem cartItem : tmp) {
            OrderItem orderItem = new OrderItem(
                    orderItemRepository.getAll().size() + idIndex,
                    cartItem.getQuantity(),
                    cartItem.getPrice(),
                    order.getId(),
                    cartItem.getProductId()
            );
            idIndex++;
            orderItems.add(orderItem);
        }
        cartItemRepository.delete(tmp);
        orderItemRepository.insert(orderItems);
        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment(
                paymentRepository.getAllPayments().size() + 1,
                "COD",
                Double.parseDouble(total),
                order.getId(),
                "Pending"
        );
        payments.add(payment);
        paymentRepository.insertPayment(payments);
        List<Product> products = new ArrayList<>();
        for (CartItem productBuy : tmp) {
            Product product = productRepository.getProductById(productBuy.getProductId());
            product.setUnitInStock(product.getUnitInStock() - productBuy.getQuantity());
            products.add(product);
        }
        productRepository.update(products);
        Log.d("Thanh toán", "Đặt hàng thành công");
        sendMessage("Đặt hàng thành công");
        Intent intent = new Intent(CartActivity.this, HomePageActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    private void handleMomoOrder() {
        String total = totalTxt.getText().toString().replace("$", "");
        Order order = new Order(
                orderRepository.getAll().size() + 1,
                Double.parseDouble(total),
                "Order Success",
                null,
                null,
                province,
                district,
                ward,
                address,
                null,
                user.getId(),
                0
        );
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orderRepository.insert(orders);
        List<OrderItem> orderItems = new ArrayList<>();
        int idIndex = 1;
        for (CartItem cartItem : tmp) {
            OrderItem orderItem = new OrderItem(
                    orderItemRepository.getAll().size() + idIndex,
                    cartItem.getQuantity(),
                    cartItem.getPrice(),
                    order.getId(),
                    cartItem.getProductId()
            );
            idIndex++;
            orderItems.add(orderItem);
        }
        cartItemRepository.delete(tmp);
        orderItemRepository.insert(orderItems);
        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment(
                paymentRepository.getAllPayments().size() + 1,
                "Momo",
                Double.parseDouble(total),
                order.getId(),
                "Pending"
        );
        payments.add(payment);
        paymentId = payment.getId();
        paymentRepository.insertPayment(payments);
        List<Product> products = new ArrayList<>();
        for (CartItem productBuy : tmp) {
            Product product = productRepository.getProductById(productBuy.getProductId());
            product.setUnitInStock(product.getUnitInStock() - productBuy.getQuantity());
            products.add(product);
        }
        productRepository.update(products);
        Log.d("Thanh toán", "Đặt hàng thành công");
        sendMessage("Đặt hàng thành công");
        requestPayment(order.getId());
    }
    //Get token through MoMo app
    private void requestPayment(int orderId) {
        Log.d("requestPayment", "Đã vào requestPayment");
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
        if (totalTxt.getText().toString() != null && totalTxt.getText().toString().trim().length() != 0)
            amount = totalTxt.getText().toString().replace("$", "");


        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", Double.parseDouble(amount) * 25000); //Kiểu integer
        eventValue.put("orderId", "HTS#" + orderId); //uniqueue id cho BillId, giá trị duy nhất cho mỗi BILL
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn
        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", fee); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description


        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());


        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("requestPayment","message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }
                    if(token != null && !token.equals("")) {
                        recyclerView.removeAllViews();
                        Payment payment = paymentRepository.getPaymentById(paymentId);
                        payment.setStatus("Success");
                        paymentRepository.updatePayment(payment);
                        Log.d("requestPayment","message: " + "Thanh toán thành công");
                        sendMessage("Thanh toán thành công");
                        Intent intent = new Intent(CartActivity.this, HomePageActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                        Toast.makeText(CartActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("requestPayment","message: not_receive_info" );
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("requestPayment","message: " + message);
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("requestPayment","message: not_receive_info" );
                } else {
                    //TOKEN FAIL
                    Log.d("requestPayment","message: not_receive_info" );
                }
            } else {
                Log.d("requestPayment","message: not_receive_info" );
            }
        } else {
            Log.d("requestPayment","message: not_receive_info_err" );
        }
    }
    private void sendMessage(String message) {


        String channelId = "your_channel_id";
        CharSequence channelName = "Channel Name";
        String channelDescription = "Channel Description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setDescription(channelDescription);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


        Notification notification = new Notification.Builder(this, "your_channel_id")
                .setContentTitle("Notification")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification_custom)
                .setLargeIcon(bitmap)
                .setColor(ContextCompat.getColor(this, R.color.red))
                .build();


        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PERMISSION_GRANTED) {
            return;
        }
        compat.notify(getNotificationId(), notification);


    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        NotificationPermissionHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}

