package com.example.high_tech_shop.admin;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.AdminNotification;

public class NotificationManageActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "admin_notifications";
    private AdminNotificationManager notificationManager;
    private TextView notificationsTextView;
    private Button showNotificationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manage);

        notificationManager = new AdminNotificationManager();
        notificationsTextView = findViewById(R.id.notificationsTextView);
        showNotificationsButton = findViewById(R.id.showNotificationsButton);

        // Thêm vài thông báo vào AdminNotificationManager
        notificationManager.addNotification("Hệ thống", "Lỗi hệ thống xảy ra lúc 10:00 AM");
        notificationManager.addNotification("Người dùng", "Người dùng mới đã đăng ký");
        notificationManager.addNotification("Hoạt động", "Đăng nhập thất bại nhiều lần");

        showNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotifications();
                showSystemNotification("Thông báo từ Admin", "Xác nhận số lượng sản phẩm còn trong kho trước khi bán.");
            }
        });

        // Tạo kênh thông báo (chỉ cần thiết cho Android O trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    private void showNotifications() {
        StringBuilder allNotifications = new StringBuilder();
        for (AdminNotification notification : notificationManager.getNotifications()) {
            allNotifications.append("[").append(notification.getType()).append("] ")
                    .append(notification.getMessage()).append("\n");
        }
        notificationsTextView.setText(allNotifications.toString());
    }

    private void showSystemNotification(String title, String message) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_location) // biểu tượng thông báo
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(soundUri)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = "Admin Notifications";
        String description = "Channel for admin notifications";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
