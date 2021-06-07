package com.example.parlemart.Product_View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parlemart.R;
import com.example.parlemart.Room.OrderSumary;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {





    Context context;
    List<OrderSumary> orderSumaryList;

    public OrderSummaryAdapter(Context context, List<OrderSumary> orderSumaryList) {
        this.context = context;
        this.orderSumaryList = orderSumaryList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView no, box, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagelist);
            no = itemView.findViewById(R.id.textno);
            box = itemView.findViewById(R.id.box);
            price = itemView.findViewById(R.id.price1);
        }
    }
    @NonNull
    @Override
    public OrderSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.ViewHolder holder, int position) {
        OrderSumary orderSumary = orderSumaryList.get(position);
        Glide.with(context).load(orderSumary.getGetImage()).fitCenter().into(holder.imageView);
        holder.no.setText(orderSumary.getGetQuantity());
        holder.box.setText(orderSumary.getGetItemType());
        holder.price.setText(orderSumary.getGettotal());
    }

    @Override
    public int getItemCount() {
        return orderSumaryList.size();
    }
}
