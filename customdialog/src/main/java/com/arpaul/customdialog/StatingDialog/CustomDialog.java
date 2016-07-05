package com.arpaul.customdialog.StatingDialog;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private  DialogListener listener;

    private String messageTitle, messageBody;
    private String posButton, negButton, reason;

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

        initiatePopupWindow();
    }

    private void initiatePopupWindow() {

        try {
            View layout = null;

            switch (DIALOG_TYPE){
                case DIALOG_SUCCESS:
                    layout = LayoutInflater.from(context).inflate(R.layout.custom_success, null);
                    break;
                case DIALOG_FAILURE:
                    layout = LayoutInflater.from(context).inflate(R.layout.custom_failure, null);
                    break;
                case DIALOG_ALERT:
                    layout = LayoutInflater.from(context).inflate(R.layout.custom_alert, null);
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

            tvContentDecline.setOnClickListener(cancel_button_click_listener);
            tvContentAccept.setOnClickListener(accept_button_click_listener);

            tvContentBody.setText(messageBody);
            tvContentTitle.setText(messageTitle);

            if(!TextUtils.isEmpty(posButton) && !TextUtils.isEmpty(negButton)){
                tvContentAccept.setText(posButton);
                tvContentDecline.setText(negButton);
            } else if(!TextUtils.isEmpty(posButton) && TextUtils.isEmpty(negButton)){
                tvContentAccept.setText(posButton);
                tvContentDecline.setVisibility(View.GONE);
            } else {
                llLowerLayout.setVisibility(View.GONE);
            }

            switch (DIALOG_TYPE){
                case DIALOG_SUCCESS:
                    llLowerLayout.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvContentAccept.performClick();
                        }
                    }, 2500);
                    break;

                case DIALOG_FAILURE:
                    tvContentDecline.setVisibility(View.GONE);
                    break;

                case DIALOG_ALERT:
                    tvContentDecline.setVisibility(View.GONE);
                    break;

                default:
            };

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

}
