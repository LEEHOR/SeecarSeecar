package com.cyht.wykc.mvp.modles;

import android.accounts.NetworkErrorException;

import com.cyht.wykc.mvp.contract.RegisterContract;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.RegisterBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Leehor
 * on 2018/11/7
 * on 11:08
 */
public class RegisterModel extends BaseModel<RegisterContract.Presenter> implements RegisterContract.model {
    public RegisterModel(RegisterContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void onRegister(Map map) {
        HttpHelper.getInstance().initService().getRegister(map).enqueue(new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                if (response.isSuccessful()) {
                    RegisterBean registerBean = response.body();
                    if (registerBean.getResult() == 1) {
                        if (getPresenter() != null) {
                            getPresenter().onRegisterSuccess(registerBean);
                        }
                    } else {
                        if (getPresenter() != null) {
                            getPresenter().onRegisterFailure(null);
                        }
                    }
                } else {
                    if (getPresenter() != null) {
                        getPresenter().onRegisterFailure(new NetworkErrorException());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onRegisterFailure(t);
                }
            }
        });
    }

    @Override
    public void Login(Map map) {
        HttpHelper.getInstance().initService().phoneLogin(map).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    LoginBean loginBean = response.body();
                    if (loginBean.getResult() == 1) {
                        if (getPresenter() != null) {
                            getPresenter().loginSuccess(loginBean);
                        }
                    } else {
                        if (getPresenter() != null) {
                            getPresenter().loginFailure(null);
                        }
                    }
                } else {
                    if (getPresenter() != null) {
                        getPresenter().loginFailure(new NetworkErrorException());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().loginFailure(t);
                }
            }
        });
    }
}
