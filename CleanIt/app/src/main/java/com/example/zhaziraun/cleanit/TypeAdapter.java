package com.example.zhaziraun.cleanit;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zhaziraun on 4/21/18.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    ArrayList<CleanType> cleanTypes;
    Context context;
    int totalCost = 0;
    HashMap<Integer, Double> selectedList = new HashMap<>();
    public TypeListener mCallback;

    private final String TAG = "Clean";

    public TypeAdapter(ArrayList<CleanType> cleanTypes, Context context, TypeListener listener) {
        this.cleanTypes = cleanTypes;
        this.context = context;
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public TypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.type_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TypeAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: " + (holder.typeName == null));
        holder.typeName.setText(cleanTypes.get(position).name);
        holder.typeCost.setText(cleanTypes.get(position).cost + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedList.containsKey(position)){
                    selectedList.remove(position);
                    view.setBackgroundColor(Color.WHITE);
                }else{
                    selectedList.put(position, cleanTypes.get(position).cost);
                    view.setBackgroundColor(Color.argb(255,111,186,169));
                }
                totalCost = 0;
                mCallback.onClick(getTotalCost(cleanTypes.size()));
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return cleanTypes.size();
    }

    public double getTotalCost(int size){
        for (int i = 0; i < size; i++){
            if (selectedList.containsKey(i))
                totalCost += selectedList.get(i);
        }
        return totalCost;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView typeName;
        TextView typeCost;

        public ViewHolder(View itemView) {
            super(itemView);
            typeName = itemView.findViewById(R.id.typeName);
            typeCost = itemView.findViewById(R.id.typeCost);
        }
    }
}
