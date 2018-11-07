package com.cyht.wykc.mvp.modles;

import com.cyht.wykc.mvp.contract.RegisterContract;
import com.cyht.wykc.mvp.modles.base.BaseModel;

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
}
