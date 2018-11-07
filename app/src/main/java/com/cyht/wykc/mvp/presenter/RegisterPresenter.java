package com.cyht.wykc.mvp.presenter;

import com.cyht.wykc.mvp.contract.RegisterContract;
import com.cyht.wykc.mvp.modles.RegisterModel;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.RegisterBean;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/7
 * on 11:03
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.view,RegisterContract.model> implements RegisterContract.Presenter
{
    public RegisterPresenter(RegisterContract.view mview) {
        super(mview);
    }

    @Override
    public void start() {

    }

    @Override
    public RegisterContract.model createModle() {
        return new RegisterModel(this);
    }

    @Override
    public void onRegister(Map map) {
        if (mModle != null) {
            mModle.onRegister(map);
        }
    }

    @Override
    public void onRegisterSuccess(RegisterBean bean) {
        if (getView() != null) {
            getView().onRegisterSuccess(bean);
        }
    }

    @Override
    public void onRegisterFailure(Throwable t) {
        if (getView() != null) {
            getView().onRegisterFailure(t);
        }
    }

    @Override
    public void Login(Map map) {
        if (mModle != null) {
            mModle.onRegister(map);
        }
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        if (getView() != null) {
            getView().loginSuccess(bean);
        }
    }

    @Override
    public void loginFailure(Throwable t) {
        if (getView() != null) {
            getView().loginFailure(t);
        }
    }
}
