package com.cyht.wykc.mvp.presenter;

import com.cyht.wykc.mvp.contract.RegisterContract;
import com.cyht.wykc.mvp.modles.RegisterModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;

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
}
