package com.arpaul.customdialog.listDialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.arpaul.customdialog.R;
import com.arpaul.customdialog.statingDialog.CustomDialogTypeFace;
import com.arpaul.customdialog.statingDialog.TypefaceDO;
import com.arpaul.customdialog.textSpinner.RecyclerViewItemClickListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aritra on 13-07-2016.
 */
public class CustomListDialog {

    private Context context;
    protected LayoutInflater inflater;
    private PopupWindow pwindo;
    private LinearLayout llLowerLayout;
    private CollapsingToolbarLayout toolbar_layout;
    private TextView tvContentDecline, tvContentAccept, tvContentTitle;
    private RecyclerView rvContentBody;

    private DialogListListener listener;
    private String messageTitle, reason, posButton, negButton;
    private List<String> listBody;
    private CustomDialogListType LIST_TYPE;
    private Typeface tfNormal, tfBold;
    private HashMap<CustomDialogTypeFace, TypefaceDO> hashTypeface = new HashMap<>();
    private HashMap<CustomDialogTypeFace, Integer> hashTextColor = new HashMap<>();
    private int colorHeader = 1;
    private DialogListAdapter adapter;

    private final int CLICK_DELAY = 250;

    /**
     *
     * @param context
     * @param listener
     * @param messageTitle
     * @param listBody
     * @param reason
     * @param LIST_TYPE
     */
    public CustomListDialog(@NonNull Context context, @NonNull DialogListListener listener, @NonNull String messageTitle, @NonNull List<String> listBody,
                        @NonNull String reason, @NonNull CustomDialogListType LIST_TYPE) {
        this.context        = context;
        this.listener       = listener;
        this.messageTitle   = messageTitle;
        this.listBody       = listBody;
        this.reason         = reason;
        this.LIST_TYPE      = LIST_TYPE;

        inflater = LayoutInflater.from(this.context);
    }

    /**
     *
     * @param context
     * @param listener
     * @param messageTitle
     * @param listBody
     * @param posButton
     * @param negButton
     * @param reason
     * @param LIST_TYPE
     */
    public CustomListDialog(@NonNull Context context, @NonNull DialogListListener listener, @NonNull String messageTitle, @NonNull List<String> listBody,
                        String posButton, String negButton, @NonNull String reason, @NonNull CustomDialogListType LIST_TYPE) {
        this.context        = context;
        this.listener       = listener;
        this.messageTitle   = messageTitle;
        this.listBody       = listBody;
        this.posButton      = posButton;
        this.negButton      = negButton;
        this.reason         = reason;
        this.LIST_TYPE      = LIST_TYPE;

        inflater = LayoutInflater.from(this.context);
    }

    /**
     *
     * @param context
     * @param listener
     * @param messageTitle
     * @param listBody
     * @param posButton
     * @param negButton
     * @param reason
     * @param LIST_TYPE
     * @param typeface
     */
    public CustomListDialog(@NonNull Context context,@NonNull DialogListListener listener,@NonNull String messageTitle,@NonNull List<String> listBody,
                        String posButton, String negButton,@NonNull String reason,@NonNull CustomDialogListType LIST_TYPE, Typeface typeface) {
        this.context        = context;
        this.listener       = listener;
        this.listBody       = listBody;
        this.messageTitle   = messageTitle;
        this.posButton      = posButton;
        this.negButton      = negButton;
        this.reason         = reason;
        this.LIST_TYPE      = LIST_TYPE;
        this.tfNormal       = typeface;

        inflater = LayoutInflater.from(this.context);
    }

    /**
     * Set Typeface normal and bold.
     * @param tfNormal
     * @param tfBold
     */
    public void setTypeface(Typeface tfNormal, Typeface tfBold){
        this.tfNormal   = tfNormal;
        this.tfBold     = tfBold;
    }

    /**
     * Sets Typeface for the specfied View.
     * @param typefaceInterface
     * @param tfBold
     * @param style
     */
    public void setTypefaceFor(CustomDialogTypeFace typefaceInterface, Typeface tfBold, int style){
        TypefaceDO objTypefaceDO = new TypefaceDO();
        objTypefaceDO.tfFont     = tfBold;
        objTypefaceDO.style      = style;
        hashTypeface.put(typefaceInterface,objTypefaceDO);
    }

    /**
     * Sets Text Color for the specfied View.
     * @param textColorInterface
     * @param textColor
     */
    public void setTextColorFor(CustomDialogTypeFace textColorInterface, int textColor){
        hashTextColor.put(textColorInterface,textColor);
    }

    /**
     * Sets Header color.
     * @param color
     */
    public void setHeaderColor(int color){
        this.colorHeader = color;
    }

    /**
     * Shows the List Dialog
     */
    public void show(){
        initiatePopupWindow();
    }

    private void initiatePopupWindow(){

        try {
            View layout = null;

            switch (LIST_TYPE){
                case LIST_SINGLESELECT:
                    layout = inflater.inflate(R.layout.custom_single_list, null);
                    break;
                case LIST_MULTISELECT:
                    layout = inflater.inflate(R.layout.custom_single_list, null);
                    break;
            }

            pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            pwindo.setAnimationStyle(R.style.AnimationPopUp);
//            pwindo.setAnimationStyle(R.style.AnimationSlideVertical);

            tvContentDecline    = (TextView) layout.findViewById(R.id.tvContentDecline);
            tvContentAccept     = (TextView) layout.findViewById(R.id.tvContentAccept);
            tvContentTitle      = (TextView) layout.findViewById(R.id.tvContentTitle);

            rvContentBody       = (RecyclerView) layout.findViewById(R.id.rvContentBody);
            rvContentBody.setLayoutManager(new LinearLayoutManager(context));

            llLowerLayout       = (LinearLayout) layout.findViewById(R.id.llLowerLayout);

            toolbar_layout      = (CollapsingToolbarLayout) layout.findViewById(R.id.toolbar_layout);

            if(tfNormal == null)
                createTypeFace();
            applyTypeface(getParentView(layout), tfNormal, Typeface.NORMAL);

            tvContentDecline.setOnClickListener(cancel_button_click_listener);
            tvContentAccept.setOnClickListener(accept_button_click_listener);

            adapter = new DialogListAdapter(context,listBody);
            rvContentBody.setAdapter(adapter);

            rvContentBody.addOnItemTouchListener(new RecyclerViewItemClickListener(context, new RecyclerViewItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, final int position) {
                    if(listener != null)
                        listener.SelectedListClick(view, position, listBody.get(position));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setSelected(listBody.get(position));
                        }
                    },200);

                    if(TextUtils.isEmpty(posButton) && TextUtils.isEmpty(negButton)){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pwindo.dismiss();
                            }
                        },CLICK_DELAY);
                    }
                }
            }));

            tvContentTitle.setText(messageTitle);

            if(!TextUtils.isEmpty(posButton))
                tvContentAccept.setText(posButton);
            else
                tvContentAccept.setVisibility(View.GONE);

            if(!TextUtils.isEmpty(negButton))
                tvContentDecline.setText(negButton);
            else
                tvContentDecline.setVisibility(View.GONE);

            if(TextUtils.isEmpty(posButton) && TextUtils.isEmpty(negButton)){
                llLowerLayout.setVisibility(View.GONE);
            }

            if(colorHeader != 1)
                toolbar_layout.setBackgroundColor(colorHeader);

            if(hashTypeface.containsKey(CustomDialogTypeFace.DIALOG_TITLE)){
                tvContentTitle.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_TITLE).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_TITLE).style);
            }
            if(hashTypeface.containsKey(CustomDialogTypeFace.DIALOG_BODY)){
                adapter.setTypefaceFor(hashTypeface.get(CustomDialogTypeFace.DIALOG_BODY).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_BODY).style);
//                adapter.notifyDataSetChanged();
            }
            if(hashTypeface.containsKey(CustomDialogTypeFace.DIALOG_BUTTON)){
                tvContentAccept.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_BUTTON).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_BUTTON).style);

                tvContentDecline.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_BUTTON).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_BUTTON).style);
            }

            if(hashTextColor.containsKey(CustomDialogTypeFace.DIALOG_TITLE)){
                tvContentTitle.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_TITLE));
            }
            if(hashTextColor.containsKey(CustomDialogTypeFace.DIALOG_BODY)){
                adapter.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_BODY));
            }
            if(hashTextColor.containsKey(CustomDialogTypeFace.DIALOG_BUTTON)){
                tvContentAccept.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_BUTTON));
                tvContentDecline.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_BUTTON));
            }

            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener accept_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pwindo.dismiss();
                    listener.OnListYesClick(reason);
                }
            },CLICK_DELAY);
        }
    };

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pwindo.dismiss();
                    listener.OnListNoClick(reason);
                }
            },CLICK_DELAY);
        }
    };

    private void createTypeFace(){
        tfBold = Typeface.createFromAsset(context.getAssets(),"fonts/Muli-Bold.ttf");
        tfNormal = Typeface.createFromAsset(context.getAssets(),"fonts/Muli-Light.ttf");
    }

    public static ViewGroup getParentView(View v) {
        ViewGroup vg = null;

        if(v != null)
            vg = (ViewGroup) v.getRootView();

        return vg;
    }

    public static void applyTypeface(ViewGroup v, Typeface f, int style) {
        if(v != null) {
            int vgCount = v.getChildCount();
            for(int i=0;i<vgCount;i++) {
                if(v.getChildAt(i) == null) continue;
                if(v.getChildAt(i) instanceof ViewGroup)
                    applyTypeface((ViewGroup)v.getChildAt(i), f, style);
                else {
                    View view = v.getChildAt(i);
                    if(view instanceof TextView)
                        ((TextView)(view)).setTypeface(f, style);
                    else if(view instanceof EditText)
                        ((EditText)(view)).setTypeface(f, style);
                    else if(view instanceof Button)
                        ((Button)(view)).setTypeface(f, style);
                }
            }
        }
    }
}
