package com.cyht.wykc.mvp.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.LoginContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;
import com.cyht.wykc.mvp.presenter.LoginPresenter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.setting.PersonalCenterFragment;
import com.cyht.wykc.mvp.view.setting.SettingActivity;
import com.cyht.wykc.utils.KeyBoardUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.utils.SharedPreferencesUtils;
import com.cyht.wykc.widget.BlockTextView;
import com.cyht.wykc.widget.LoadingDialog;
import com.cyht.wykc.widget.MobilePhoneEditText;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import anet.channel.strategy.dispatch.DispatchEvent;
import butterknife.BindView;

/**
 * Author： hengzwd on 2017/12/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.view {
    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.et_mobilephone)
    EditText etMobilephone;
    @BindView(R.id.btv_verification)
    BlockTextView btvVerification;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.login_btn_wx)
    ImageView loginBtnWx;
    @BindView(R.id.login_btn_qq)
    ImageView loginBtnQq;
    @BindView(R.id.login_btn_wb)
    ImageView loginBtnWb;
    @BindView(R.id.ll_login)
    LinearLayout lllogin;
    @BindView(R.id.login_message)
    TextView login_message;
    @BindView(R.id.register_account)
    TextView register_account;
    private LoadingDialog alertDialog;

    private int action = 0;//1:QQ,2:微信,3:新浪

    @Override
    public void showLoading() {

    }
    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        register_account.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getTvTittle().setVisibility(View.GONE);
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressedSupport();
            }
        });
        loginBtnWx.setOnClickListener(wxClick);
        loginBtnQq.setOnClickListener(qqClick);
        loginBtnWb.setOnClickListener(wbClick);
        register_account.setOnClickListener(registerListener);
        /**
         * 此方法不用
         */
        btvVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMobilephone.getText().length() == 11) {
                    btvVerification.startGetCount();
                    mPresenter.getVerificationCode(etMobilephone.getText().toString());
//                    Toast.makeText(LoginActivity.this,etMobilephone.getPhoneNumber()+"",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this,"请输入正确手机号码",Toast.LENGTH_LONG).show();
                }
            }
        });

        etMobilephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                login_message.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((etVerificationCode.getText() != null && !etVerificationCode.getText().equals("")) && (editable != null && !editable.toString().equals(""))) {
                    tvLogin.setClickable(true);
                    tvLogin.setBackgroundColor(Color.parseColor("#2fc1ff"));
                } else {
                    tvLogin.setClickable(false);
                    tvLogin.setBackgroundColor(Color.parseColor("#969798"));
                }
            }
        });
        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                login_message.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((s != null && !s.toString().equals("")) && (etMobilephone.getText() != null && !etMobilephone.getText().equals(""))) {
                    tvLogin.setClickable(true);
                    tvLogin.setBackgroundColor(Color.parseColor("#2fc1ff"));
                }else {
                    tvLogin.setClickable(false);
                    tvLogin.setBackgroundColor(Color.parseColor("#969798"));
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMobilephone.getText().length() < 4 || etMobilephone.getText().length() > 12) {
                    Toast.makeText(LoginActivity.this, "请输入4～12字符(字母、数字)", Toast.LENGTH_LONG).show();
                    login_message.setText("账户：请输入4～12位(字母、数字)!");
                    return;
                }
                if (etVerificationCode.getText().length() < 6 || etVerificationCode.getText().length() > 18) {
                    Toast.makeText(LoginActivity.this, "请输入6～18位密码", Toast.LENGTH_LONG).show();
                    login_message.setText("密码：请输入6～18位密码!");
                    return;
                }
                Map map = new HashMap();
              //  map.put("usercode", etMobilephone.getText());
              //  map.put("xingming", etMobilephone.getText());
               // map.put("typevalue","0");
               // map.put("mobilecode",etVerificationCode.getText().toString());
               // map.put(Constants.DEVICESTOKEN, Constants.devicestoken != null && Constants.devicestoken != "" ? Constants.devicestoken : (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.DEVICESTOKEN, ""));
               // map.put(Constants.SYSTEM, Constants.ANDROID);
                map.put("account",etMobilephone.getText().toString());
                map.put("password",etVerificationCode.getText().toString());
                alertDialog.show();
                mPresenter.phoneLogin(map);

            }
        });

//        alertDialog= new AlertDialog.Builder(this).setView(R.layout.dialog_loging).create();
        alertDialog = new LoadingDialog(this);
    }

    @Override
    public void initData() {
    }


    @Override
    public void onLoginFailue(Throwable throwable) {
        alertDialog.dismiss();
        Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_toast_failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        alertDialog.dismiss();
        Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_toast_success), Toast.LENGTH_SHORT).show();
        Constants.HAS_LOGIN_OR_NOT = 1;
        String username = loginBean.getUsername();
        String touxiang = loginBean.getTouxiang();
        String sessionid = loginBean.getSessionid();
        Constants.sessionid = sessionid;
        Constants.touxiang = touxiang;
        Constants.username = username;
        KLog.e("username:" + touxiang);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.SESSION_ID, sessionid);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.USERNAME, username);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.TOUXIANG, touxiang);
        setResult(1);
        finish();
    }

    @Override
    public void onPhoneLoginFailure(LoginBean resultBean) {
        alertDialog.dismiss();
        Toast.makeText(LoginActivity.this, resultBean.getMsg(), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onPhoneLoginSuccess(LoginBean loginBean) {
        alertDialog.dismiss();
        Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_toast_success), Toast.LENGTH_SHORT).show();
        Constants.HAS_LOGIN_OR_NOT = 1;
        String username = loginBean.getUsername();
        String touxiang = loginBean.getTouxiang();
        String sessionid = loginBean.getSessionid();
        Constants.sessionid = sessionid;
        Constants.touxiang = touxiang;
        Constants.username = username;
        KLog.e("username:" + touxiang);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.SESSION_ID, sessionid);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.USERNAME, username);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.TOUXIANG, touxiang);
        setResult(1);
        finish();
    }

    @Override
    public void onVerificationResult(String result) {
        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();

    }

    private void trilateralLogin() {
        UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);
        switch (action) {
            case 1:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                KLog.e("share");
                break;
            case 2:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case 3:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
        }
    }

    View.OnClickListener qqClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            action = 1;
            trilateralLogin();
        }
    };

    View.OnClickListener wxClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            action = 2;
            trilateralLogin();
        }
    };
    View.OnClickListener registerListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivityForResult(intent,1000);
        }
    };
    View.OnClickListener wbClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            action = 3;
            trilateralLogin();
        }
    };
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            KLog.e("onComplete");
            if (SHARE_MEDIA.SINA.equals(platform)) {
                otherlogin(data.get("uid"), data.get("screen_name"), data.get("profile_image_url"));
            } else {
                otherlogin(data.get("openid"), data.get("screen_name"), data.get("profile_image_url"));
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            KLog.e("shareonError:" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            KLog.e("shareononCancel");

        }
    };

    private void otherlogin(String usercode, String xingming, String photo) {
        alertDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put(Constants.USERCODE, usercode);
//        params.put(Constants.XINGMING, xingming);
        KLog.e("xingming:"+xingming);
        params.put(Constants.PHOTO, photo);
        params.put(Constants.DEVICESTOKEN, Constants.devicestoken != null && Constants.devicestoken != "" ? Constants.devicestoken : (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.DEVICESTOKEN, ""));
        params.put(Constants.TYPEVALUE, action + "");
        params.put(Constants.SYSTEM, Constants.ANDROID);
        mPresenter.otherLogin(params,xingming);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1000){
            setResult(1);
            finish();
        } else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
    }

}
