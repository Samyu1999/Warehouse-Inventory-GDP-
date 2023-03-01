package com.example.warehouseinventory.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.warehouseinventory.R;

import java.util.ArrayList;
import java.util.List;

public class CheckinAdapter extends BaseAdapter  implements Filterable {
    Context context;
    ArrayList<String> name;
    ArrayList<String> brand;
    ArrayList<String> price;
    ArrayList<String> cid;
    LayoutInflater inflter;
    private List<String> exampleListFull;

    public CheckinAdapter(Context applicationContext, ArrayList<String> name, ArrayList<String> brand, ArrayList<String> price, ArrayList<String> cid ) {
        this.context = applicationContext;
        this.brand = brand;
        this.price = price;
        this.cid = cid;
        this.name = name;
        exampleListFull = new ArrayList<>(name);
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return name.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_item1, null);
        TextView cids =  view.findViewById(R.id.cid);
        TextView names =  view.findViewById(R.id.name);
        TextView brands =  view.findViewById(R.id.brand);
        TextView prices =  view.findViewById(R.id.price);

        cids.setText(cid.get(i));
        names.setText(name.get(i));
        brands.setText(brand.get(i));
        prices.setText(price.get(i));
        return view;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : exampleListFull) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            name.clear();
            name.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}