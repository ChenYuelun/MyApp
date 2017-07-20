package com.example.chenyuelun.myapp.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;

import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.UserInterruptException;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.CountryPage;
import cn.smssdk.gui.IdentifyNumPage;
import cn.smssdk.gui.RegisterPage;
import cn.smssdk.gui.SmartVerifyPage;
import cn.smssdk.gui.layout.RegisterPageLayout;
import cn.smssdk.gui.layout.SendMsgDialogLayout;
import cn.smssdk.utils.SMSLog;

/**
 * Created by chenyuelun on 2017/7/20.
 */

public class LoginActivity2 extends FakeActivity implements View.OnClickListener, TextWatcher {

    private static final String DEFAULT_COUNTRY_ID = "42";
    private EventHandler callback;
    private TextView tvCountry;
    private EditText etPhoneNum;
    private TextView tvCountryNum;
    private ImageView ivClear;
    private Button btnNext;
    private String currentId;
    private String currentCode;
    private EventHandler handler;
    private Dialog pd;
    private OnSendMessageHandler osmHandler;

    public LoginActivity2() {
    }

    public void setRegisterCallback(EventHandler callback) {
        this.callback = callback;
    }

    public void setOnSendMessageHandler(OnSendMessageHandler h) {
        this.osmHandler = h;
    }

    public void show(Context context) {
        super.show(context, (Intent)null);
    }

    public void onCreate() {
        RegisterPageLayout page = new RegisterPageLayout(this.activity);
        LinearLayout layout = page.getLayout();
        if(layout != null) {
            this.activity.setContentView(layout);
            this.currentId = "42";
            View llBack = this.activity.findViewById(ResHelper.getIdRes(this.activity, "ll_back"));
            TextView tv = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_title"));
            int resId = ResHelper.getStringRes(this.activity, "smssdk_regist");
            if(resId > 0) {
                tv.setText(resId);
            }

            View viewCountry = this.activity.findViewById(ResHelper.getIdRes(this.activity, "rl_country"));
            this.btnNext = (Button)this.activity.findViewById(ResHelper.getIdRes(this.activity, "btn_next"));
            this.tvCountry = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_country"));
            String[] country = this.getCurrentCountry();
            if(country != null) {
                this.currentCode = country[1];
                this.tvCountry.setText(country[0]);
            }

            this.tvCountryNum = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_country_num"));
            this.tvCountryNum.setText("+" + this.currentCode);
            this.etPhoneNum = (EditText)this.activity.findViewById(ResHelper.getIdRes(this.activity, "et_write_phone"));
            this.etPhoneNum.setText("");
            this.etPhoneNum.addTextChangedListener(this);
            this.etPhoneNum.requestFocus();
            if(this.etPhoneNum.getText().length() > 0) {
                this.btnNext.setEnabled(true);
                this.ivClear = (ImageView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "iv_clear"));
                this.ivClear.setVisibility(0);
                resId = ResHelper.getBitmapRes(this.activity, "smssdk_btn_enable");
                if(resId > 0) {
                    this.btnNext.setBackgroundResource(resId);
                }
            }

            this.ivClear = (ImageView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "iv_clear"));
            llBack.setOnClickListener(this);
            this.btnNext.setOnClickListener(this);
            this.ivClear.setOnClickListener(this);
            viewCountry.setOnClickListener(this);
            this.handler = new EventHandler() {
                public void afterEvent(final int event, final int result, final Object data) {
                    LoginActivity2.this.runOnUIThread(new Runnable() {
                        public void run() {
                            if(LoginActivity2.this.pd != null && LoginActivity2.this.pd.isShowing()) {
                                LoginActivity2.this.pd.dismiss();
                            }

                            if(result == -1) {
                                if(event == 2) {
                                    boolean status = ((Boolean)data).booleanValue();
                                    LoginActivity2.this.afterVerificationCodeRequested(status);
                                }
                            } else {
                                if(event == 2 && data != null && data instanceof UserInterruptException) {
                                    return;
                                }

                                int status1 = 0;

                                try {
                                    ((Throwable)data).printStackTrace();
                                    Throwable resId = (Throwable)data;
                                    JSONObject object = new JSONObject(resId.getMessage());
                                    String des = object.optString("detail");
                                    status1 = object.optInt("status");
                                    if(!TextUtils.isEmpty(des)) {
                                        Toast.makeText(LoginActivity2.this.activity, des, 0).show();
                                        return;
                                    }
                                } catch (Exception var5) {
                                    SMSLog.getInstance().w(var5);
                                }

                                boolean resId1 = false;
                                int resId2;
                                if(status1 >= 400) {
                                    resId2 = ResHelper.getStringRes(LoginActivity2.this.activity, "smssdk_error_desc_" + status1);
                                } else {
                                    resId2 = ResHelper.getStringRes(LoginActivity2.this.activity, "smssdk_network_error");
                                }

                                if(resId2 > 0) {
                                    Toast.makeText(LoginActivity2.this.activity, resId2, 0).show();
                                }
                            }

                        }
                    });
                }
            };
        }

    }

    private String[] getCurrentCountry() {
        String mcc = this.getMCC();
        String[] country = null;
        if(!TextUtils.isEmpty(mcc)) {
            country = SMSSDK.getCountryByMCC(mcc);
        }

        if(country == null) {
            SMSLog.getInstance().d("no country found by MCC: " + mcc, new Object[0]);
            country = SMSSDK.getCountry("42");
        }

        return country;
    }

    private String getMCC() {
        TelephonyManager tm = (TelephonyManager)this.activity.getSystemService("phone");
        String networkOperator = tm.getNetworkOperator();
        return !TextUtils.isEmpty(networkOperator)?networkOperator:tm.getSimOperator();
    }

    public void onResume() {
        SMSSDK.registerEventHandler(this.handler);
    }

    public void onDestroy() {
        SMSSDK.unregisterEventHandler(this.handler);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int resId;
        if(s.length() > 0) {
            this.btnNext.setEnabled(true);
            this.ivClear.setVisibility(0);
            resId = ResHelper.getBitmapRes(this.activity, "smssdk_btn_enable");
            if(resId > 0) {
                this.btnNext.setBackgroundResource(resId);
            }
        } else {
            this.btnNext.setEnabled(false);
            this.ivClear.setVisibility(8);
            resId = ResHelper.getBitmapRes(this.activity, "smssdk_btn_disenable");
            if(resId > 0) {
                this.btnNext.setBackgroundResource(resId);
            }
        }

    }

    public void afterTextChanged(Editable s) {
    }

    public void onClick(View v) {
        int id = v.getId();
        int idLlBack = ResHelper.getIdRes(this.activity, "ll_back");
        int idRlCountry = ResHelper.getIdRes(this.activity, "rl_country");
        int idBtnNext = ResHelper.getIdRes(this.activity, "btn_next");
        int idIvClear = ResHelper.getIdRes(this.activity, "iv_clear");
        if(id == idLlBack) {
            this.finish();
        } else if(id == idRlCountry) {
            CountryPage phone = new CountryPage();
            phone.setCountryId(this.currentId);
            phone.showForResult(this.activity, (Intent)null, this);
        } else if(id == idBtnNext) {
            String phone1 = this.etPhoneNum.getText().toString().trim().replaceAll("\\s*", "");
            String code = this.tvCountryNum.getText().toString().trim();
            this.showDialog(phone1, code);
        } else if(id == idIvClear) {
            this.etPhoneNum.getText().clear();
        }

    }

    public void onResult(HashMap<String, Object> data) {
        if(data != null) {
            int page = ((Integer)data.get("page")).intValue();
            if(page == 1) {
                this.currentId = (String)data.get("id");
                String[] res = SMSSDK.getCountry(this.currentId);
                if(res != null) {
                    this.currentCode = res[1];
                    this.tvCountryNum.setText("+" + this.currentCode);
                    this.tvCountry.setText(res[0]);
                }
            } else if(page == 2) {
                Object res1 = data.get("res");
                HashMap phoneMap = (HashMap)data.get("phone");
                if(res1 != null && phoneMap != null) {
                    int resId = ResHelper.getStringRes(this.activity, "smssdk_your_ccount_is_verified");
                    if(resId > 0) {
                        Toast.makeText(this.activity, resId, 0).show();
                    }

                    if(this.callback != null) {
                        this.callback.afterEvent(3, -1, phoneMap);
                    }

                    this.finish();
                }
            }
        }

    }

    private String splitPhoneNum(String phone) {
        StringBuilder builder = new StringBuilder(phone);
        builder.reverse();
        int i = 4;

        for(int len = builder.length(); i < len; i += 5) {
            builder.insert(i, ' ');
        }

        builder.reverse();
        return builder.toString();
    }

    public void showDialog(final String phone, final String code) {
        int resId = ResHelper.getStyleRes(this.activity, "CommonDialog");
        if(resId > 0) {
            String phoneNum = code + " " + this.splitPhoneNum(phone);
            final Dialog dialog = new Dialog(this.getContext(), resId);
            LinearLayout layout = SendMsgDialogLayout.create(this.getContext());
            if(layout != null) {
                dialog.setContentView(layout);
                ((TextView)dialog.findViewById(ResHelper.getIdRes(this.activity, "tv_phone"))).setText(phoneNum);
                TextView tv = (TextView)dialog.findViewById(ResHelper.getIdRes(this.activity, "tv_dialog_hint"));
                resId = ResHelper.getStringRes(this.activity, "smssdk_make_sure_mobile_detail");
                if(resId > 0) {
                    String text = this.getContext().getString(resId);
                    tv.setText(Html.fromHtml(text));
                }

                ((Button)dialog.findViewById(ResHelper.getIdRes(this.activity, "btn_dialog_ok"))).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        if(LoginActivity2.this.pd != null && LoginActivity2.this.pd.isShowing()) {
                            LoginActivity2.this.pd.dismiss();
                        }

                        LoginActivity2.this.pd = CommonDialog.ProgressDialog(LoginActivity2.this.activity);
                        if(LoginActivity2.this.pd != null) {
                            LoginActivity2.this.pd.show();
                        }

                        SMSLog.getInstance().i("verification phone ==>>" + phone, new Object[0]);
                        SMSSDK.getVerificationCode(code, phone.trim(), LoginActivity2.this.osmHandler);
                    }
                });
                ((Button)dialog.findViewById(ResHelper.getIdRes(this.activity, "btn_dialog_cancel"))).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        }

    }

    private void afterVerificationCodeRequested(boolean smart) {
        String phone = this.etPhoneNum.getText().toString().trim().replaceAll("\\s*", "");
        String code = this.tvCountryNum.getText().toString().trim();
        if(code.startsWith("+")) {
            code = code.substring(1);
        }

        String formatedPhone = "+" + code + " " + this.splitPhoneNum(phone);
        if(smart) {
            SmartVerifyPage page = new SmartVerifyPage();
            page.setPhone(phone, code, formatedPhone);
            page.showForResult(this.activity, (Intent)null, this);
        } else {
            IdentifyNumPage page1 = new IdentifyNumPage();
            page1.setPhone(phone, code, formatedPhone);
            page1.showForResult(this.activity, (Intent)null, this);
        }

    }

}
