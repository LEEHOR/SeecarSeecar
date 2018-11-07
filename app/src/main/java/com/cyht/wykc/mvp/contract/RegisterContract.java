package com.cyht.wykc.mvp.contract;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.RegisterBean;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/7
 * on 11:01
 */
public interface RegisterContract {
    interface view extends BaseContract.View {

        void onRegisterSuccess(RegisterBean bean);

        void onRegisterFailure(Throwable t);

        void loginSuccess(LoginBean bean);

        void loginFailure(Throwable t);
    }

    interface Presenter extends BaseContract.presenter {

        void onRegister(Map map);

        void onRegisterSuccess(RegisterBean bean);

        void onRegisterFailure(Throwable t);

        void Login(Map map);

        void loginSuccess(LoginBean bean);

        void loginFailure(Throwable t);
    }

    interface model extends BaseContract.Model {

        void onRegister(Map map);

        void Login(Map map);
    }
}
