package com.example.zhaziraun.cleanit;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhaziraun on 4/20/18.
 */

public class CompanyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<CompanyDataHelper> companyDataHelpers;

    public final String TAG = "Clean";

    public CompanyListAdapter(Context context, ArrayList<CompanyDataHelper> companyDataHelpers){
        this.context = context;
        this.companyDataHelpers = companyDataHelpers;
    }

    @Override
    public int getCount() {
        return companyDataHelpers.size();
    }

    @Override
    public Object getItem(int position) {
        return companyDataHelpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.company_row, viewGroup, false);

        TextView companyName = v.findViewById(R.id.companyName);
        TextView description = v.findViewById(R.id.description);

        companyName.setText(companyDataHelpers.get(position).companyName);
        description.setText(companyDataHelpers.get(position).description);

        Log.d(TAG, "getView: " + companyDataHelpers.get(position).cleanTypes.get(1).name);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CompanyActivity.class);
                intent.putExtra("company", companyDataHelpers.get(position));
                context.startActivity(intent);
            }
        });

        return v;
    }
}
