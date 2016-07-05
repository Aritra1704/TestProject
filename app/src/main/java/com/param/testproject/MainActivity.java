package com.param.testproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arpaul.customdialog.StatingDialog.CustomDialog;
import com.arpaul.customdialog.StatingDialog.CustomDialogType;
import com.arpaul.customdialog.StatingDialog.DialogListener;

public class MainActivity extends AppCompatActivity implements DialogListener {

    private Button btnDialog,btnProgressBar, btnCustomDialogAccept, btnCustomDialogDecline, btnCustomDialogAlert;
    private Context mContext;
    public MaterialDialog materialDialog,materialPB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MainActivity.this;
        setContentView(R.layout.activity_main);

        btnDialog = (Button) findViewById(R.id.btnDialog);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(MainActivity.this,"Title","Content body","Accept","Discard","test",false);
            }
        });

        btnProgressBar = (Button) findViewById(R.id.btnProgressBar);
        btnProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(MainActivity.this)
                        .title("Progress Dialog")
                        .content("Please wait..")
                        .progress(true, 0)
                        .progressIndeterminateStyle(true)
                        .show();

                showProgressDialog(MainActivity.this, "Progress Dialog", "Please wait..", 80, false);
            }
        });

        btnCustomDialogAccept = (Button) findViewById(R.id.btnCustomDialogAccept);
        btnCustomDialogAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog(MainActivity.this, MainActivity.this, "Success","Message success",
                        null, null, "Success", CustomDialogType.DIALOG_SUCCESS);
            }
        });

        btnCustomDialogDecline = (Button) findViewById(R.id.btnCustomDialogDecline);
        btnCustomDialogDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog(MainActivity.this, MainActivity.this,"Failure","Message failure",
                        getString(R.string.ok), null, "Failure", CustomDialogType.DIALOG_FAILURE);
            }
        });

        btnCustomDialogAlert = (Button) findViewById(R.id.btnCustomDialogAlert);
        btnCustomDialogAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog(MainActivity.this, MainActivity.this,"Alert","Message alert",
                        getString(R.string.ok), getString(R.string.disagree), "Alert", CustomDialogType.DIALOG_ALERT);
            }
        });
    }

    @Override
    public void OnButtonYesClick(String from) {

    }

    @Override
    public void OnButtonNoClick(String from) {

    }

    /**
     * Shows Indefinite Progress Dialog.
     * @param context
     * @param title
     * @param message
     * @param progress
     * @param isCancelable
     */
    public void showProgressDialog(final Context context,final String title,final String message, final int progress, boolean isCancelable){
        runOnUiThread(new RunProgressDialog(context, title, message, progress, isCancelable));
    }
    class RunProgressDialog implements Runnable {
        private Context context;
        private String strTitle;// Title of the materialDialog
        private String strMessage;// Message to be shown in materialDialog
        private boolean isCancelable=false;
        private int progress;

        public RunProgressDialog(Context context, String strTitle,String strMessage, int progress, boolean isCancelable)
        {
            this.context 		= context;
            this.strTitle 		= strTitle;
            this.strMessage 	= strMessage;
            this.progress 	    = progress;
            this.isCancelable 	= isCancelable;
        }

        @Override
        public void run() {
            if (materialPB != null && materialPB.isShowing())
                materialPB.dismiss();

            MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                    .title(strTitle)
                    .content(strMessage)
                    .cancelable(isCancelable)
                    .typeface("Roboto-Bold.ttf", "Roboto-Light.ttf");

            builder
                    .progress(true, progress)
                    .progressIndeterminateStyle(true);

            try{
                if (materialPB == null || !materialPB.isShowing()){
                    materialPB = builder.build();
                    materialPB.show();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows Dialog with user defined buttons.
     * @param context
     * @param title
     * @param message
     * @param okButton
     * @param noButton
     * @param from
     * @param isCancelable
     */
    public void showDialog(final Context context,final String title,final String message,final String okButton,final String noButton,final String from, boolean isCancelable){
        runOnUiThread(new RunShowDialog(context,title,message,okButton,noButton,from, isCancelable));
    }

    public void hideLoader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (materialPB != null && materialPB.isShowing())
                    materialPB.dismiss();
            }
        });
    }

    class RunShowDialog implements Runnable {
        private Context context;
        private String strTitle;// Title of the materialDialog
        private String strMessage;// Message to be shown in materialDialog
        private String firstBtnName;
        private String secondBtnName;
        private String from;
        private boolean isCancelable=false;
        public RunShowDialog(Context context, String strTitle,String strMessage, String firstBtnName, String secondBtnName,	String from, boolean isCancelable)
        {
            this.context 		= context;
            this.strTitle 		= strTitle;
            this.strMessage 	= strMessage;
            this.firstBtnName 	= firstBtnName;
            this.secondBtnName	= secondBtnName;
            this.isCancelable 	= isCancelable;
            if (from != null)
                this.from = from;
            else
                this.from = "";
        }

        @Override
        public void run() {
            if (materialDialog != null && materialDialog.isShowing())
                materialDialog.dismiss();

            MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                    .title("")
                    .content(strMessage)
                    .cancelable(isCancelable)
                    .iconRes(R.drawable.farm_icon)
                    .titleGravity(GravityEnum.CENTER)
//                    .btnStackedGravity(GravityEnum.CENTER)
//                    .itemsGravity(GravityEnum.CENTER)
                    .maxIconSizeRes(R.dimen.margin_200)
                    .typeface("Roboto-Bold.ttf", "Roboto-Light.ttf");


            builder.positiveText(firstBtnName)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            onButtonYesClick(from);
                            dialog.dismiss();
                        }
                    });

            if(!TextUtils.isEmpty(secondBtnName)) {
                builder.negativeText(secondBtnName)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                onButtonNoClick(from);
                                dialog.dismiss();
                            }
                        });
            }

            try{
                if (materialDialog == null || !materialDialog.isShowing()){
                    materialDialog = builder.build();
                    materialDialog.show();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void onButtonYesClick(String from) {
        Toast.makeText(MainActivity.this,from+" Yes",Toast.LENGTH_SHORT).show();
    }

    public void onButtonNoClick(String from) {
        Toast.makeText(MainActivity.this,from+" No",Toast.LENGTH_SHORT).show();
    }
}
