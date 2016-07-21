package com.arpaul.customdialog.textSpinner;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arpaul.customdialog.R;

import java.util.List;

/**
 * Created by Aritra on 06-07-2016.
 */
public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.ViewHolder> {

    private Context context;
    private List<String> arrSpinnerList;
    private Typeface typefaceCell;
    private int typefaceStyle = Typeface.NORMAL;

    public SpinnerAdapter(Context context, List<String> arrCallDetails) {
        this.context=context;
        this.arrSpinnerList = arrCallDetails;

    }

    public void refresh(List<String> arrCallDetails) {
        this.arrSpinnerList = arrCallDetails;
        notifyDataSetChanged();
    }

    public void setTypefaceFor(Typeface tfBold, int style){
        this.typefaceCell       = tfBold;
        this.typefaceStyle      = style;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String strListCell = arrSpinnerList.get(position);
        holder.tvSpinnerCell.setText(strListCell);

        if(typefaceCell != null)
            holder.tvSpinnerCell.setTypeface(typefaceCell, typefaceStyle);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrSpinnerList != null)
            return arrSpinnerList.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvSpinnerCell;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvSpinnerCell      = (TextView) view.findViewById(R.id.tvSpinnerCell);
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
