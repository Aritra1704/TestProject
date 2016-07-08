package com.arpaul.customdialog.textSpinner;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.arpaul.customdialog.R;

import java.util.List;

/**
 * Created by Aritra on 06-07-2016.
 */
public class CustomSpinner extends LinearLayout {

    private Context context;
    private AttributeSet attr;
    private View view;
    private TextView tvSpinner;
    private PopupWindow pwindo;
    private List<String> spinnerList;
    private String titleText, hint;
    private SpinnerAdapter adapter;
    private SpinnerCellListener itemListener;
    private TextView tvSpinnerTitle;
    private LinearLayout llPopup;
    private RecyclerView rvSpinnerList;
    private ImageView ivLowerBorder;
    private int popupBgColor = 0;

    /**
     * Contructor for spinner
     * @param context
     */
    public CustomSpinner(Context context){
        super(context);
        this.context = context;

        onCreateView();
    }

    /**
     * Contructor for spinner
     * @param context
     * @param attr
     */
    public CustomSpinner(Context context, AttributeSet attr){
        super(context,attr);
        this.context = context;
        this.attr = attr;

        onCreateView();
    }

    public View onCreateView() {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflate.inflate(R.layout.custom_spinner, this, true);

        initialiseControls(view);

        bindControls();
        return view;
    }

    public void setText(String message){
        tvSpinner.setText(message);
    }

    public void setHint(String hint){
        this.hint = hint;
        tvSpinner.setHint(hint);
    }

    public void setTitleText(String titleText){
        this.titleText = titleText;
    }

    public void setAdapter(List<String> spinnerList){
        this.spinnerList = spinnerList;

        createListPopup();
    }

    /**
     * Set up listener for receiving item click.
     * @param itemListener
     */
    public void setItemlistener(SpinnerCellListener itemListener){
        this.itemListener = itemListener;
    }

    public void setPopupBgColor(int color){
        this.popupBgColor = popupBgColor;
        llPopup.setBackgroundColor(popupBgColor);
    }

    private void bindControls(){
        tvSpinner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pwindo != null)
                    pwindo.showAsDropDown(view);

                if(spinnerList != null && rvSpinnerList != null){
                    adapter = new SpinnerAdapter(context,spinnerList);
                    rvSpinnerList.setAdapter(adapter);
                }

            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSpinner.performClick();
            }
        });
    }

    private void createListPopup(){
        View layout = LayoutInflater.from(context).inflate(R.layout.content_spinner_list, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        pwindo = new PopupWindow(layout, view.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

        llPopup             = (LinearLayout) layout.findViewById(R.id.llPopup);
        tvSpinnerTitle      = (TextView) layout.findViewById(R.id.tvSpinnerTitle);
        rvSpinnerList       = (RecyclerView) layout.findViewById(R.id.rvSpinnerList);
        rvSpinnerList.setLayoutManager(new LinearLayoutManager(context));

        if(spinnerList != null){
            adapter = new SpinnerAdapter(context,spinnerList);
            rvSpinnerList.setAdapter(adapter);
        }

        tvSpinnerTitle.setText(titleText);
        rvSpinnerList.addOnItemTouchListener(new RecyclerViewItemClickListener(context, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(itemListener != null)
                    itemListener.onItemClick(spinnerList.get(position));
                pwindo.dismiss();
            }
        }));
    }

    private void initialiseControls(View view){
        tvSpinner       = (TextView) view.findViewById(R.id.tvSpinner);
        ivLowerBorder   = (ImageView) view.findViewById(R.id.ivLowerBorder);

        if(attr != null){
            LayoutParams params=new LayoutParams(context, attr);
            tvSpinner.setLayoutParams(params);
        }

        if(!TextUtils.isEmpty(hint))
            tvSpinner.setHint(hint);

        createListPopup();
    }
}
