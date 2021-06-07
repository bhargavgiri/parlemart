 package com.example.parlemart.Product_View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parlemart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.GithubViewHolder> {
    private Context context;
    public static List<Product_Data> list12;
 public String setpath1,P_productname,price;




    public Product_Adapter(Context context,List<Product_Data> list12)
    {
        this.context=context;
        this.list12=list12;

    }

    public Product_Adapter() {
    }

    @NonNull
    @Override
    public GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.product_view_items,parent,false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder holder, int position) {

        Glide.with(context).load(list12.get(position).getP_path()).into(holder.Product_image);
        holder.Product_text.setText(list12.get(position).getP_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setpath1=list12.get(position).getP_path();
                P_productname=list12.get(position).getP_name();
                price=list12.get(position).getPrice();
                Toast.makeText(context, "txt", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context,Product_Detail_View.class);
                i.putExtra("Imagewpath",setpath1);
                i.putExtra("Pname",P_productname);
                i.putExtra("Price",price);
                context.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list12.size();
    }

    public static class GithubViewHolder extends RecyclerView.ViewHolder{
       ImageView Product_image;
       //ImageView setpath;

       TextView  Product_text;
        LinearLayout linearLayout;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            Product_image=itemView.findViewById(R.id.image12);
            Product_text=itemView.findViewById(R.id.product_name);
            linearLayout=itemView.findViewById(R.id.L_layout);
            //setpath=Product_image;

        }
    }
}
