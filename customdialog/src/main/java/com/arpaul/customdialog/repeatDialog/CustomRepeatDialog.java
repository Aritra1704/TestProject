package com.arpaul.customdialog.repeatDialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.arpaul.customdialog.R;
import com.arpaul.customdialog.common.CustomDialogTypeFace;
import com.arpaul.customdialog.common.TypefaceDO;
import com.arpaul.utilitieslib.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aritra on 20-07-2016.
 */
public class CustomRepeatDialog {

    private Context context;
    private PopupWindow pwindo;
    private TextView tvContentDecline, tvContentAccept, tvContentTitle;
    private TextView tvSunday, tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday;
    private EditText edtRepeatnumber;
    private CollapsingToolbarLayout toolbar_layout;
    private DialogRepeatListener listener;
    private String messageTitle;
    private String posButton, negButton, reason;

    protected LayoutInflater inflater;
    private Typeface tfNormal, tfBold;

    private HashMap<CustomDialogTypeFace, TypefaceDO> hashTypeface = new HashMap<>();
    private HashMap<CustomDialogTypeFace, Integer> hashTextColor = new HashMap<>();
    private int colorHeader = 1;
    private final int CLICK_DELAY = 200;
    private ArrayList<CustomRepeatDays> arrDays = new ArrayList<>();

    public CustomRepeatDialog(@NonNull Context context, @NonNull DialogRepeatListener listener, @NonNull String messageTitle,
                        String posButton, String negButton, @NonNull String reason) {
        this.context        = context;
        this.listener       = listener;
        this.messageTitle   = messageTitle;
        this.posButton      = posButton;
        this.negButton      = negButton;
        this.reason         = reason;

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
     * Return whether popup window is showing or not.
     * @return
     */
    public boolean isShowing(){
        if(pwindo != null && pwindo.isShowing())
            return true;

        return false;
    }

    /**
     * Dismisses popup window.
     */
    public void dismiss(){
        if(pwindo != null && pwindo.isShowing())
            pwindo.dismiss();
    }

    public void show(){
        initiatePopupWindow();
    }

    private void initiatePopupWindow() {

        try {
            View layout = inflater.inflate(R.layout.custom_repeatweek, null);

            pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            pwindo.setAnimationStyle(R.style.AnimationPopUp);
//            pwindo.setAnimationStyle(R.style.AnimationSlideVertical);

            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);


            tvContentTitle      = (TextView) layout.findViewById(R.id.tvContentTitle);
            toolbar_layout      = (CollapsingToolbarLayout) layout.findViewById(R.id.toolbar_layout);

            tvSunday            = (TextView) layout.findViewById(R.id.tvSunday);
            tvMonday            = (TextView) layout.findViewById(R.id.tvMonday);
            tvTuesday           = (TextView) layout.findViewById(R.id.tvTuesday);
            tvWednesday         = (TextView) layout.findViewById(R.id.tvWednesday);
            tvThursday          = (TextView) layout.findViewById(R.id.tvThursday);
            tvFriday            = (TextView) layout.findViewById(R.id.tvFriday);
            tvSaturday          = (TextView) layout.findViewById(R.id.tvSaturday);

            edtRepeatnumber     = (EditText) layout.findViewById(R.id.edtRepeatnumber);

            tvContentDecline    = (TextView) layout.findViewById(R.id.tvContentDecline);
            tvContentAccept     = (TextView) layout.findViewById(R.id.tvContentAccept);

            if(tfNormal == null)
                createTypeFace();
            applyTypeface(getParentView(layout), tfNormal, Typeface.NORMAL);

            tvContentDecline.setOnClickListener(cancel_button_click_listener);
            tvContentAccept.setOnClickListener(accept_button_click_listener);

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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvContentAccept.performClick();
                    }
                }, 2500);
            }

            if(colorHeader != 1)
                toolbar_layout.setBackgroundColor(colorHeader);

            if(hashTypeface.containsKey(CustomDialogTypeFace.DIALOG_TITLE)){
                tvContentTitle.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_TITLE).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_TITLE).style);
            }
            if(hashTypeface.containsKey(CustomDialogTypeFace.DIALOG_BUTTON)){
                tvContentAccept.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_BUTTON).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_BUTTON).style);

                tvContentDecline.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_BUTTON).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_BUTTON).style);
            }

            if(hashTypeface.containsKey(CustomDialogTypeFace.DIALOG_WEEK)){
                tvSunday.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).style);

                tvMonday.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).style);

                tvTuesday.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).style);

                tvWednesday.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).style);

                tvThursday.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).style);

                tvFriday.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).style);

                tvSaturday.setTypeface(hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).tfFont,
                        hashTypeface.get(CustomDialogTypeFace.DIALOG_WEEK).style);
            }

            if(hashTextColor.containsKey(CustomDialogTypeFace.DIALOG_TITLE)){
                tvContentTitle.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_TITLE));
            }
            if(hashTextColor.containsKey(CustomDialogTypeFace.DIALOG_BUTTON)){
                tvContentAccept.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_BUTTON));
                tvContentDecline.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_BUTTON));
            }
            if(hashTextColor.containsKey(CustomDialogTypeFace.DIALOG_WEEK)){
                tvSunday.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_WEEK));
                tvMonday.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_WEEK));
                tvTuesday.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_WEEK));
                tvWednesday.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_WEEK));
                tvThursday.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_WEEK));
                tvFriday.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_WEEK));
                tvSaturday.setTextColor(hashTextColor.get(CustomDialogTypeFace.DIALOG_WEEK));
            }

            tvSunday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrDays.contains(CustomRepeatDays.SUNDAY)){
                        tvSunday.setBackgroundResource(R.drawable.holo_circle);
                        arrDays.remove(CustomRepeatDays.SUNDAY);
                    } else {
                        tvSunday.setBackgroundResource(R.drawable.solid_circle);
                        arrDays.add(CustomRepeatDays.SUNDAY);
                    }
                }
            });
            tvMonday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrDays.contains(CustomRepeatDays.MONDAY)){
                        tvMonday.setBackgroundResource(R.drawable.holo_circle);
                        arrDays.remove(CustomRepeatDays.MONDAY);
                    } else {
                        tvMonday.setBackgroundResource(R.drawable.solid_circle);
                        arrDays.add(CustomRepeatDays.MONDAY);
                    }
                }
            });
            tvTuesday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrDays.contains(CustomRepeatDays.TUESDAY)){
                        tvTuesday.setBackgroundResource(R.drawable.holo_circle);
                        arrDays.remove(CustomRepeatDays.TUESDAY);
                    } else {
                        tvTuesday.setBackgroundResource(R.drawable.solid_circle);
                        arrDays.add(CustomRepeatDays.TUESDAY);
                    }
                }
            });
            tvWednesday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrDays.contains(CustomRepeatDays.WEDNESDAY)){
                        tvWednesday.setBackgroundResource(R.drawable.holo_circle);
                        arrDays.remove(CustomRepeatDays.WEDNESDAY);
                    } else {
                        tvWednesday.setBackgroundResource(R.drawable.solid_circle);
                        arrDays.add(CustomRepeatDays.WEDNESDAY);
                    }
                }
            });
            tvThursday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrDays.contains(CustomRepeatDays.THURSDAY)){
                        tvThursday.setBackgroundResource(R.drawable.holo_circle);
                        arrDays.remove(CustomRepeatDays.THURSDAY);
                    } else {
                        tvThursday.setBackgroundResource(R.drawable.solid_circle);
                        arrDays.add(CustomRepeatDays.THURSDAY);
                    }
                }
            });
            tvFriday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrDays.contains(CustomRepeatDays.FRIDAY)){
                        tvFriday.setBackgroundResource(R.drawable.holo_circle);
                        arrDays.remove(CustomRepeatDays.FRIDAY);
                    } else {
                        tvFriday.setBackgroundResource(R.drawable.solid_circle);
                        arrDays.add(CustomRepeatDays.FRIDAY);
                    }
                }
            });
            tvSaturday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrDays.contains(CustomRepeatDays.SATURDAY)){
                        tvSaturday.setBackgroundResource(R.drawable.holo_circle);
                        arrDays.remove(CustomRepeatDays.SATURDAY);
                    } else {
                        tvSaturday.setBackgroundResource(R.drawable.solid_circle);
                        arrDays.add(CustomRepeatDays.SATURDAY);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String repeatText = "";
    private View.OnClickListener accept_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            if(edtRepeatnumber != null){
                int interval = StringUtils.getInt(edtRepeatnumber.getText().toString());
                    repeatText = "INTERVAL="+interval;

                if(arrDays != null && arrDays.size() > 0){
                    repeatText = repeatText + ";BYDAY=";
                    String days = "";
                    for(int i = 0; i < arrDays.size(); i++){
                        if(!TextUtils.isEmpty(days))
                            days = days+",";
                        switch(arrDays.get(i)){
                            case SUNDAY:
                                days = days + "SU";
                                break;
                            case MONDAY:
                                days = days + "MO";
                                break;
                            case TUESDAY:
                                days = days + "TU";
                                break;
                            case WEDNESDAY:
                                days = days + "WE";
                                break;
                            case THURSDAY:
                                days = days + "TH";
                                break;
                            case FRIDAY:
                                days = days + "FR";
                                break;
                            case SATURDAY:
                                days = days + "SA";
                                break;
                        }
                    }
                    repeatText = repeatText + days;
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pwindo.dismiss();
                    listener.RepeatDialogYesClick(reason, repeatText);
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
                    listener.RepeatDialogNoClick(reason);
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
