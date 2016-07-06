package com.arpaul.customdialog.statingDialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.arpaul.customdialog.R;

/**
 * Created by Aritra on 05-07-2016.
 */
public class CustomDialog {

    private Context context;
    private PopupWindow pwindo;
    private TextView tvContentDecline, tvContentAccept, tvContentBody, tvContentTitle;
    private LinearLayout llLowerLayout;
    private CollapsingToolbarLayout toolbar_layout;
    private FloatingActionButton fabDayNewSchedule;
    private CustomDialogType DIALOG_TYPE;
    private DialogListener listener;
    private Typeface tfNormal, tfBold, tfSeparate;
    private int typefaceStyle = Typeface.BOLD;
    private CustomDialogTypeFace typefaceInterface;

    private String messageTitle, messageBody;
    private String posButton, negButton, reason;

    protected Builder mBuilder;
    protected Handler mHandler;
    protected LayoutInflater inflater;

    /**
     *
     * @param builder
     */
    public CustomDialog(Builder builder){
        mHandler = new Handler();
        mBuilder = builder;
        inflater = LayoutInflater.from(builder.context);
    }

    /**
     *
     * @param context
     * @param listener
     * @param messageTitle
     * @param messageBody
     * @param reason
     * @param DIALOG_TYPE
     */
    public CustomDialog(@NonNull Context context,@NonNull DialogListener listener,@NonNull String messageTitle,@NonNull String messageBody,
                        @NonNull String reason,@NonNull CustomDialogType DIALOG_TYPE) {
        this.context        = context;
        this.listener       = listener;
        this.messageBody    = messageBody;
        this.messageTitle   = messageTitle;
        this.reason         = reason;
        this.DIALOG_TYPE    = DIALOG_TYPE;

        inflater = LayoutInflater.from(this.context);
    }

    /**
     * Contructor for Custom Dialog to show Success, Failure or Alert popups.
     * @param context
     * @param listener
     * @param messageTitle
     * @param messageBody
     * @param posButton
     * @param negButton
     * @param reason
     * @param DIALOG_TYPE
     */
    public CustomDialog(@NonNull Context context,@NonNull DialogListener listener,@NonNull String messageTitle,@NonNull String messageBody,
                        String posButton, String negButton,@NonNull String reason,@NonNull CustomDialogType DIALOG_TYPE) {
        this.context        = context;
        this.listener       = listener;
        this.messageBody    = messageBody;
        this.messageTitle   = messageTitle;
        this.posButton      = posButton;
        this.negButton      = negButton;
        this.reason         = reason;
        this.DIALOG_TYPE    = DIALOG_TYPE;

        inflater = LayoutInflater.from(this.context);
    }

    /**
     *
     * @param context
     * @param listener
     * @param messageTitle
     * @param messageBody
     * @param posButton
     * @param negButton
     * @param reason
     * @param DIALOG_TYPE
     * @param typeface
     */
    public CustomDialog(@NonNull Context context,@NonNull DialogListener listener,@NonNull String messageTitle,@NonNull String messageBody,
                        String posButton, String negButton,@NonNull String reason,@NonNull CustomDialogType DIALOG_TYPE,Typeface typeface) {
        this.context        = context;
        this.listener       = listener;
        this.messageBody    = messageBody;
        this.messageTitle   = messageTitle;
        this.posButton      = posButton;
        this.negButton      = negButton;
        this.reason         = reason;
        this.DIALOG_TYPE    = DIALOG_TYPE;
        this.tfNormal       = typeface;

        inflater = LayoutInflater.from(this.context);
    }

    /**
     *
     * @param tfNormal
     * @param tfBold
     */
    public void setTypeface(Typeface tfNormal, Typeface tfBold){
        this.tfNormal   = tfNormal;
        this.tfBold     = tfBold;
    }

    public void setTypefaceFor(CustomDialogTypeFace typefaceInterface, Typeface tfBold, int style){
        this.typefaceInterface  = typefaceInterface;
        this.tfSeparate         = tfBold;
        this.typefaceStyle      = style;
    }

    public void show(){
        initiatePopupWindow();
    }

    private void initiatePopupWindow() {

        try {
            View layout = null;

            switch (DIALOG_TYPE){
                case DIALOG_SUCCESS:
                    layout = inflater.inflate(R.layout.custom_success, null);
                    break;
                case DIALOG_FAILURE:
                    layout = inflater.inflate(R.layout.custom_failure, null);
                    break;
                case DIALOG_ALERT:
                    layout = inflater.inflate(R.layout.custom_alert, null);
                    break;
            }

            pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            tvContentDecline    = (TextView) layout.findViewById(R.id.tvContentDecline);
            tvContentAccept     = (TextView) layout.findViewById(R.id.tvContentAccept);
            tvContentBody       = (TextView) layout.findViewById(R.id.tvContentBody);
            tvContentTitle      = (TextView) layout.findViewById(R.id.tvContentTitle);

            llLowerLayout       = (LinearLayout) layout.findViewById(R.id.llLowerLayout);

            toolbar_layout      = (CollapsingToolbarLayout) layout.findViewById(R.id.toolbar_layout);

            fabDayNewSchedule   = (FloatingActionButton) layout.findViewById(R.id.fabDayNewSchedule);

            if(tfNormal == null)
                createTypeFace();
            applyTypeface(getParentView(layout), tfNormal, Typeface.NORMAL);

            tvContentDecline.setOnClickListener(cancel_button_click_listener);
            tvContentAccept.setOnClickListener(accept_button_click_listener);

            tvContentBody.setText(messageBody);
            tvContentTitle.setText(messageTitle);

            if(!TextUtils.isEmpty(posButton) && !TextUtils.isEmpty(negButton)){
                tvContentAccept.setText(posButton);
                tvContentDecline.setText(negButton);
            } else if(!TextUtils.isEmpty(posButton) && TextUtils.isEmpty(negButton)){
                tvContentAccept.setText(posButton);
//                tvContentDecline.setVisibility(View.GONE);
            } else {
//                llLowerLayout.setVisibility(View.GONE);
            }

            switch (DIALOG_TYPE){
                case DIALOG_SUCCESS:
                    if(TextUtils.isEmpty(posButton)){
                        llLowerLayout.setVisibility(View.GONE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvContentAccept.performClick();
                            }
                        }, 2500);
                    } else if(TextUtils.isEmpty(negButton)){
                        tvContentDecline.setVisibility(View.GONE);
                    }
                    break;

                case DIALOG_FAILURE:
                    if(TextUtils.isEmpty(negButton))
                        tvContentDecline.setVisibility(View.GONE);
                    break;

                case DIALOG_ALERT:
                    if(TextUtils.isEmpty(negButton))
                        tvContentDecline.setVisibility(View.GONE);
                    break;

                default:
            };

            switch (typefaceInterface){
                case DIALOG_TITLE:
                    tvContentTitle.setTypeface(tfSeparate,typefaceStyle);
                    break;
                case DIALOG_BODY:
                    tvContentBody.setTypeface(tfSeparate,typefaceStyle);
                    break;
                case DIALOG_BUTTON:
                    tvContentAccept.setTypeface(tfSeparate,typefaceStyle);
                    tvContentDecline.setTypeface(tfSeparate,typefaceStyle);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener accept_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();
            listener.OnButtonYesClick(reason);
        }
    };

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();
            listener.OnButtonNoClick(reason);
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

    public static class Builder {
        protected final Context context;


        public final Context getContext() {
            return context;
        }

        public Builder(@NonNull Context context) {
            this.context = context;
        }
    }
}
