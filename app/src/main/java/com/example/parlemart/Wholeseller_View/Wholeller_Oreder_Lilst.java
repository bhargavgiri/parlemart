package com.example.parlemart.Wholeseller_View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parlemart.Distributor_View.Distributor_View_Oreder_module;
import com.example.parlemart.R;

import java.util.ArrayList;
import java.util.List;

public class Wholeller_Oreder_Lilst extends RecyclerView.Adapter<Wholeller_Oreder_Lilst.GithubViewHolder> implements Filterable {






    private Context context;
    public List<WholesellerData> list;
    ArrayList<WholesellerData> data1;
    String s_name,so_name,contact;
    public static String W_token;


    public Wholeller_Oreder_Lilst(Context context, List<WholesellerData> list)
    {
        this.context=context;
        this.list=list;
        data1=new ArrayList<>(list);
    }

    @NonNull
    @Override
    public GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.oreder_list,parent,false);
        return new GithubViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder holder, int position) {
        holder.S_name1.setText(list.get(position).getShope_name());
        holder.O_name1.setText(list.get(position).getShope_Ownar_name());
        holder.contact1.setText(list.get(position).getShope_Contact());
        holder.ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_name=list.get(position).getShope_name();
                so_name=list.get(position).getShope_Ownar_name();
                contact=list.get(position).getShope_Contact();
                W_token=list.get(position).getToken();
               // Toast.makeText(context, "Your Order id Ready", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context, Distributor_View_Oreder_module.class);
                i.putExtra("sname",s_name);
                i.putExtra("soname",so_name);
                i.putExtra("contact",contact);
                i.putExtra("w_token",W_token);
                context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {

        return list.size();
    }

    @Override
    public Filter getFilter() {

        return filter;
    }
    Filter filter=new Filter() {
        // background Tread work
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {

            List<WholesellerData> filter_data=new ArrayList<>();

            if (keyword.toString().isEmpty())
            {
                filter_data.addAll(data1);
            }else
            {
                for (WholesellerData W : data1)
                {
                    if (W.Shope_name.toString().toLowerCase().contains(keyword.toString().toLowerCase().trim()))
                    {
                        filter_data.add(W);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filter_data;
            return filterResults;
        }

        @Override // main Ui Tread
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList<WholesellerData>)results.values);
            notifyDataSetChanged();

        }
    };

    public void setFilter(ArrayList<WholesellerData> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }


    public class GithubViewHolder extends RecyclerView.ViewHolder{
        List<WholesellerData> list;
        TextView S_name1,O_name1,contact1;

        Button ready;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            S_name1=itemView.findViewById(R.id.shope_name1);
            O_name1=itemView.findViewById(R.id.owner_name1);
            contact1=itemView.findViewById(R.id.Contact1);
            ready=itemView.findViewById(R.id.Ready);
        }
    }
}
