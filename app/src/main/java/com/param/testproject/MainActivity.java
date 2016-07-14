package com.param.testproject;

import android.content.Context;
import android.graphics.Typeface;
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
import com.arpaul.customdialog.listDialog.CustomDialogListType;
import com.arpaul.customdialog.listDialog.CustomListDialog;
import com.arpaul.customdialog.listDialog.DialogListListener;
import com.arpaul.customdialog.statingDialog.CustomDialog;
import com.arpaul.customdialog.statingDialog.CustomDialogType;
import com.arpaul.customdialog.statingDialog.CustomDialogTypeFace;
import com.arpaul.customdialog.statingDialog.DialogListener;
import com.arpaul.customdialog.textSpinner.CustomSpinner;
import com.arpaul.customdialog.textSpinner.SpinnerCellListener;
import com.arpaul.utilitieslib.ColorUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogListener {

    private Button btnDialog,btnProgressBar, btnCustomDialogAccept, btnCustomDialogDecline, btnCustomDialogAlert,
            btnCustomDialogNormal, btnCustomDialogWait;
    private Button btnTwoway, btnMaterial, btnDeterminateTwoway, btnCustomList;
    private Context mContext;
    public MaterialDialog materialDialog,materialPB;
    private CustomDialog cDialog;
    private CustomListDialog cListDialog;
    private CustomSpinner csTest;

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
                cDialog = new CustomDialog(MainActivity.this, MainActivity.this, "Success","Message success",
                        null, null, "Success", CustomDialogType.DIALOG_SUCCESS);
//                cDialog.setHeaderColor(ColorUtils.getColor(MainActivity.this,R.color.colorBrightRed));
                cDialog.show();
            }
        });

        btnCustomDialogDecline = (Button) findViewById(R.id.btnCustomDialogDecline);
        btnCustomDialogDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDialog = new CustomDialog(MainActivity.this, MainActivity.this,"Failure","Message failure.",
                        getString(R.string.ok), null, "Failure", CustomDialogType.DIALOG_FAILURE);
                Typeface tfBold = Typeface.createFromAsset(getAssets(),"fonts/Muli-Bold.ttf");
//                cDialog.setTypefaceFor(CustomDialogTypeFace.DIALOG_BODY, tfBold, Typeface.BOLD);
//                cDialog.setTypefaceFor(CustomDialogTypeFace.DIALOG_TITLE, tfBold, Typeface.BOLD);
                cDialog.show();
            }
        });

        btnCustomDialogAlert = (Button) findViewById(R.id.btnCustomDialogAlert);
        btnCustomDialogAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog(MainActivity.this, MainActivity.this,"Alert","Message alert",
                        getString(R.string.ok), null, "Alert", CustomDialogType.DIALOG_ALERT).show();
            }
        });

        btnCustomDialogNormal = (Button) findViewById(R.id.btnCustomDialogNormal);
        btnCustomDialogNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDialog = new CustomDialog(MainActivity.this, MainActivity.this,"Normal","Message normal",
                        getString(R.string.ok), null, "Normal", CustomDialogType.DIALOG_NORMAL);
                cDialog.setTextColorFor(CustomDialogTypeFace.DIALOG_TITLE, ColorUtils.getColor(MainActivity.this, R.color.colorMediumGrey));
                cDialog.show();
            }
        });

        btnCustomDialogWait = (Button) findViewById(R.id.btnCustomDialogWait);
        btnCustomDialogWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDialog = new CustomDialog(MainActivity.this, MainActivity.this,"Wait","Please wait..",
                        getString(R.string.ok), null, "Wait", CustomDialogType.DIALOG_WAIT);
//                cDialog.setTextColorFor(CustomDialogTypeFace.DIALOG_TITLE, ColorUtils.getColor(MainActivity.this, R.color.bpLight_gray));
                cDialog.show();
            }
        });

        btnCustomList = (Button) findViewById(R.id.btnCustomList);
        btnCustomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrList = new ArrayList<String>();
                arrList.add("Text1");
                arrList.add("Text2");
                arrList.add("Text3");
                arrList.add("Text4");

                cListDialog = new CustomListDialog(MainActivity.this, new DialogListListener() {
                    @Override
                    public void SelectedListClick(String from) {

                    }

                    @Override
                    public void OnListYesClick(String from) {

                    }

                    @Override
                    public void OnListNoClick(String from) {

                    }
                }, "List", arrList, "List", CustomDialogListType.LIST_SINGLESELECT);
//                cListDialog.setTextColorFor(CustomDialogTypeFace.DIALOG_TITLE, ColorUtils.getColor(MainActivity.this, R.color.bpLight_gray));
                cListDialog.show();
            }
        });

        btnTwoway = (Button) findViewById(R.id.btnTwoway);
        btnTwoway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnMaterial = (Button) findViewById(R.id.btnMaterial);
        btnMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnDeterminateTwoway = (Button) findViewById(R.id.btnDeterminateTwoway);
        btnDeterminateTwoway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        csTest = (CustomSpinner) findViewById(R.id.csTest);
        csTest.setTitleText("Title");
        csTest.setHint("Tap here");
        ArrayList<String> arrSpin = new ArrayList<>();
        arrSpin.add("Test 1");
        arrSpin.add("Test 2");
        arrSpin.add("Test 3");
        arrSpin.add("Test 4");
        arrSpin.add("Test 5");
        csTest.setAdapter(arrSpin);
        csTest.setItemlistener(new SpinnerCellListener() {
            @Override
            public void onItemClick(String selectedCell){
                csTest.setText(selectedCell);
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
                    .typeface("fonts/Muli-Bold.ttf", "fonts/Muli-Light.ttf");

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
