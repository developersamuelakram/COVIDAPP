package com.example.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CoronaAdapter extends RecyclerView.Adapter<CoronaAdapter.MyViewHolder> implements Filterable {


    Context context;
    List<Model> corornalist;
    List<Model> coronaListFiltered;

    public CoronaAdapter(Context context, List<Model> corornalist) {
        this.context = context;
        this.corornalist = corornalist;
        this.coronaListFiltered = corornalist;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String character  = constraint.toString();

                if (character.isEmpty()) {
                    coronaListFiltered = corornalist;
                } else {
                    List<Model> filterList = new ArrayList<>();
                    for (Model row: corornalist) {

                        if (row.getCountryname().toLowerCase().contains(character.toLowerCase())) {
                            filterList.add(row);
                        }
                    }

                    coronaListFiltered = filterList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = coronaListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                coronaListFiltered = (ArrayList<Model>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.coronadata, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String todaycasess = coronaListFiltered.get(position).getTodaycases();
        String totalcasess = coronaListFiltered.get(position).getTotalcases();
        String recovereds = coronaListFiltered.get(position).getRecovered();
        String images = coronaListFiltered.get(position).getFlag();
        String death = coronaListFiltered.get(position).getDeaths();
        String countrynames = coronaListFiltered.get(position).getCountryname();


        holder.todaycases.setText(todaycasess);
        holder.totalcases.setText(totalcasess);
        holder.recovered.setText(recovereds);
        holder.countryname.setText(countrynames);
        holder.deaths.setText(death);

        Glide.with(context).load(images).override(1500, 300).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return coronaListFiltered.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView todaycases, totalcases, recovered, countryname, deaths;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            todaycases = itemView.findViewById(R.id.caseNumber);
            totalcases = itemView.findViewById(R.id.totalcaseNumber);
            recovered = itemView.findViewById(R.id.totalrecovered);
            countryname = itemView.findViewById(R.id.countryName);
            imageView = itemView.findViewById(R.id.imageView);
            deaths = itemView.findViewById(R.id.deathNumber);

        }
    }
}
