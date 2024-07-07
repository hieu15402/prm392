package com.example.high_tech_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.Order;
import com.example.high_tech_shop.repositories.OrderItemRepository;
import com.example.high_tech_shop.repositories.ProductRepository;

import java.util.List;

public class OrderBaseAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;
    private LayoutInflater inflater;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;

    public OrderBaseAdapter(Context context, List<Order> orderList, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.context = context;
        this.orderList = orderList;
        this.inflater = LayoutInflater.from(context);
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item_order, parent, false);
            holder = new ViewHolder();
            holder.tvOrderId = convertView.findViewById(R.id.tv_order_id);
            holder.imgProduct = convertView.findViewById(R.id.img_product);
            holder.tvProductName = convertView.findViewById(R.id.tv_product_name);
            holder.tvOrderDate = convertView.findViewById(R.id.tv_order_date);
            holder.tvOrderStatus = convertView.findViewById(R.id.tv_order_status);
            holder.tvCustomerName = convertView.findViewById(R.id.tv_customer_name);
            holder.tvOrderPrice = convertView.findViewById(R.id.tv_order_price);
            holder.btnExpand = convertView.findViewById(R.id.btn_expand);
            holder.layoutExpanded = convertView.findViewById(R.id.layout_expanded);
            holder.btnMarkAsShipped = convertView.findViewById(R.id.btn_mark_as_shipped);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Order order = orderList.get(position);

        // Tạm thời sử dụng dữ liệu tĩnh để kiểm tra hiển thị
        holder.tvOrderId.setText("ID: " + order.getId());
        holder.tvProductName.setText("Sản phẩm mẫu"); // Dữ liệu tĩnh cho tên sản phẩm
        holder.tvOrderDate.setText("01/01/2024"); // Dữ liệu tĩnh cho ngày đặt hàng
        holder.tvOrderStatus.setText("Pending"); // Dữ liệu tĩnh cho trạng thái đơn hàng
        holder.tvCustomerName.setText("Nguyễn Văn A"); // Dữ liệu tĩnh cho tên khách hàng
        holder.tvOrderPrice.setText("$100.00"); // Dữ liệu tĩnh cho giá đơn hàng

        // Xử lý sự kiện nhấn nút mở rộng
        holder.btnExpand.setOnClickListener(v -> {
            if (holder.layoutExpanded.getVisibility() == View.GONE) {
                holder.layoutExpanded.setVisibility(View.VISIBLE);
                holder.btnExpand.setImageResource(R.drawable.ic_minus_sign); // Đổi icon thành thu gọn
            } else {
                holder.layoutExpanded.setVisibility(View.GONE);
                holder.btnExpand.setImageResource(R.drawable.ic_plus_sign); // Đổi icon thành mở rộng
            }
        });

        // Xử lý sự kiện nhấn nút "Mark as shipped"
        holder.btnMarkAsShipped.setOnClickListener(v -> {
            // Cập nhật trạng thái đơn hàng và các hành động khác nếu cần
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvOrderId, tvProductName, tvOrderDate, tvOrderStatus, tvCustomerName, tvOrderPrice;
        ImageView imgProduct, btnExpand;
        LinearLayout layoutExpanded;
        Button btnMarkAsShipped;
    }
}