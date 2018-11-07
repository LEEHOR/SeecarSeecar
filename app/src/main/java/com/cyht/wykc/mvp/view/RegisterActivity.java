package com.cyht.wykc.mvp.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.RegisterContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.RegisterBean;
import com.cyht.wykc.mvp.presenter.RegisterPresenter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.CodeUtil.CodeUtilListener;
import com.cyht.wykc.utils.CodeUtil.CodeUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.utils.SharedPreferencesUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/7
 * on 10:59
 * 注册页面
 */
public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.view {
    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.register_account)
    EditText register_account;
    @BindView(R.id.register_pass)
    EditText register_pass;
    @BindView(R.id.register_pass_sure)
    EditText register_pass_sure;
    @BindView(R.id.register_identifyingCode)
    EditText register_identifyingCode;
    @BindView(R.id.iv_code)
    ImageView iv_code;
    @BindView(R.id.register_massage)
    TextView register_massage;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.have_account)
    TextView have_account;
    private String codes;

    @Override
    public void showLoading() {

    }

    @Override
    public RegisterContract.Presenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        have_account.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getTvTittle().setVisibility(View.GONE);
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
        //验证码回调
        CodeUtils.getInstance().setListener(new CodeUtilListener() {
            @Override
            public void getCode(String code) {
                codes = code;
                KLog.d("验证码", codes);
            }
        });
        //刷新验证码
        iv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_massage.setText("");
                Bitmap bitmap = CodeUtils.getInstance().createBitmap();
                iv_code.setImageBitmap(bitmap);

            }
        });
        //账户
        register_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                register_massage.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable != null && !editable.toString().equals(""))
                        && (register_pass.getText() != null && !register_pass.getText().toString().equals(""))
                        && (register_pass_sure.getText() != null && !register_pass_sure.getText().toString().equals(""))
                        && (register_identifyingCode.getText() !=null && !register_identifyingCode.getText().toString().equals(""))) {
                    btn_register.setClickable(true);
                    btn_register.setBackgroundColor(Color.parseColor("#2fc1ff"));
                } else {
                    btn_register.setClickable(false);
                    btn_register.setBackgroundColor(Color.parseColor("#969798"));
                }
            }
        });
        //设置密码
        register_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                register_massage.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable != null && !editable.toString().equals(""))
                        && (register_account.getText() != null && !register_account.getText().toString().equals(""))
                        && (register_pass_sure.getText() != null && !register_pass_sure.getText().toString().equals(""))
                        && (register_identifyingCode.getText() !=null && !register_identifyingCode.getText().toString().equals(""))) {
                    btn_register.setClickable(true);
                    btn_register.setBackgroundColor(Color.parseColor("#2fc1ff"));
                } else {
                    btn_register.setClickable(false);
                    btn_register.setBackgroundColor(Color.parseColor("#969798"));
                }
            }
        });
        //确认密码
        register_pass_sure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                register_massage.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable != null && !editable.toString().equals(""))
                        && (register_account.getText() != null && !register_account.getText().toString().equals(""))
                        && (register_pass.getText() != null && !register_pass.getText().toString().equals(""))
                        && (register_identifyingCode.getText() !=null && !register_identifyingCode.getText().toString().equals(""))) {
                    btn_register.setClickable(true);
                    btn_register.setBackgroundColor(Color.parseColor("#2fc1ff"));
                } else {
                    btn_register.setClickable(false);
                    btn_register.setBackgroundColor(Color.parseColor("#969798"));
                }
            }
        });

        //本地验证码
        register_identifyingCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                register_massage.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable != null && !editable.toString().equals(""))
                        && (register_account.getText() != null && !register_account.getText().toString().equals(""))
                        && (register_pass.getText() != null && !register_pass.getText().toString().equals(""))
                        && (register_pass_sure.getText() !=null && !register_pass_sure.getText().toString().equals(""))) {
                    btn_register.setClickable(true);
                    btn_register.setBackgroundColor(Color.parseColor("#2fc1ff"));
                } else {
                    btn_register.setClickable(false);
                    btn_register.setBackgroundColor(Color.parseColor("#969798"));
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (register_account.getText().toString().length()<4 || register_account.getText().toString().length()>12){
                    register_massage.setText("请输入4～12位(字母、数字)!");
                    return;
                }
                if (register_pass.getText().toString().length()<6 || register_pass.getText().toString().length()>18){
                    register_massage.setText("请输入6～18位密码!");
                    return;
                }
                if (register_pass_sure.getText().toString().length()<6 || register_pass_sure.getText().toString().length()>18){
                    register_massage.setText("请输入6～18位密码!");
                    return;
                }
                if (!TextUtils.equals(register_pass.getText(), register_pass_sure.getText())) {
                    register_massage.setText("两次输入的密码不一致!");
                    return;
                }
                if (!TextUtils.equals(register_identifyingCode.getText().toString().toUpperCase(),codes)){
                    register_massage.setText("验证码错误!");
                    Bitmap bitmap = CodeUtils.getInstance().createBitmap();
                    iv_code.setImageBitmap(bitmap);
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("account", register_account.getText().toString());
                params.put(Constants.DEVICESTOKEN, Constants.devicestoken != null && Constants.devicestoken != "" ? Constants.devicestoken : (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.DEVICESTOKEN, ""));
                params.put("password", register_pass_sure.getText().toString());
                params.put(Constants.SYSTEM, Constants.ANDROID);
                mPresenter.onRegister(params);
            }
        });
    }

    @Override
    public void initData() {
        Bitmap bitmap = CodeUtils.getInstance().createBitmap();
        iv_code.setImageBitmap(bitmap);
    }

    @Override
    public void onRegisterSuccess(RegisterBean bean) {
        Toast.makeText(RegisterActivity.this,bean.getMsg(),Toast.LENGTH_LONG).show();
        Map<String,String> map=new HashMap<>();
        map.put("account",register_account.getText().toString());
        map.put("password",register_pass_sure.getText().toString());
        mPresenter.Login(map);
    }

    @Override
    public void onRegisterFailure(Throwable t) {
        Bitmap bitmap = CodeUtils.getInstance().createBitmap();
        iv_code.setImageBitmap(bitmap);
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        String username = bean.getUsername();
        String touxiang = bean.getTouxiang();
        String sessionid = bean.getSessionid();
        Constants.sessionid = sessionid;
        Constants.touxiang = touxiang;
        Constants.username = username;
        KLog.e("username:" + touxiang);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.SESSION_ID, sessionid);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.USERNAME, username);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.TOUXIANG, touxiang);
        setResult(1000);
        finish();
    }

    @Override
    public void loginFailure(Throwable t) {

    }
}
