package com.arpaul.customdialog.listDialog;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arpaul.customdialog.R;
import com.arpaul.utilitieslib.ColorUtils;

import java.util.List;

/**
 * Created by Aritra on 13-07-2016.
 */
public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.ViewHolder> {

    private Context context;
    private List<String> arrSpinnerList;
    private Typeface typefaceCell;
    private int typefaceStyle = Typeface.NORMAL;
    private int textColor = 1;
    private String selectedCell = "";
    private Drawable holo_circle, solid_circle;

    public DialogListAdapter(Context context, List<String> arrCallDetails) {
        this.context=context;
        this.arrSpinnerList = arrCallDetails;
        this.textColor = ColorUtils.getColor(context,R.color.colorMediumGrey);

        holo_circle = context.getResources().getDrawable(R.drawable.holo_circle);
        holo_circle.setColorFilter(new PorterDuffColorFilter(ColorUtils.getColor(context,R.color.colorBluishGreen), PorterDuff.Mode.MULTIPLY));

        solid_circle = context.getResources().getDrawable(R.drawable.solid_circle);
        solid_circle.setColorFilter(new PorterDuffColorFilter(ColorUtils.getColor(context,R.color.colorBluishGreen), PorterDuff.Mode.MULTIPLY));
    }

    public void refresh(List<String> arrCallDetails) {
        this.arrSpinnerList = arrCallDetails;
        notifyDataSetChanged();
    }

    /**
     * Sets Typeface for adapter.
     * @param tfBold
     * @param style
     */
    public void setTypefaceFor(Typeface tfBold, int style){
        this.typefaceCell       = tfBold;
        this.typefaceStyle      = style;
    }

    /**
     * Sets text Color in Adapter.
     * @param style
     */
    public void setTextColor(int style){
        this.textColor      = style;
    }

    /**
     * Set the Rounded circle color.
     * @param color
     */
    public void setCircleColor(int color){
        holo_circle = context.getResources().getDrawable(R.drawable.holo_circle);
        holo_circle.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));

        solid_circle = context.getResources().getDrawable(R.drawable.solid_circle);
        solid_circle.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String strListCell = arrSpinnerList.get(position);
        holder.tvListCell.setText(strListCell);

        if(typefaceCell != null)
            holder.tvListCell.setTypeface(typefaceCell, typefaceStyle);

        holder.tvListCell.setTextColor(textColor);

        if(!TextUtils.isEmpty(selectedCell) && selectedCell.equalsIgnoreCase(strListCell)){
            holder.ivList.setImageDrawable(solid_circle);
        } else {
            holder.ivList.setImageDrawable(holo_circle);
        }
    }

    public void setSelected(String selected){
        selectedCell = selected;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(arrSpinnerList != null)
            return arrSpinnerList.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvListCell;
        public final ImageView ivList;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvListCell      = (TextView) view.findViewById(R.id.tvListCell);
            ivList          = (ImageView) view.findViewById(R.id.ivList);
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
