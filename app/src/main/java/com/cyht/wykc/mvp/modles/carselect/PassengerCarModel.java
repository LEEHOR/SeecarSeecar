package com.cyht.wykc.mvp.modles.carselect;

import android.accounts.NetworkErrorException;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.contract.carselect.PassengerCarContract;
import com.cyht.wykc.mvp.modles.bean.BrandListBean;
import com.cyht.wykc.mvp.modles.bean.CarListBean;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.NetworkUtils;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class PassengerCarModel extends BaseModel<PassengerCarContract.Presenter> implements PassengerCarContract.Modle {

    private List<BrandListBean.DataEntity.BrandListEntity> listEntities;
    private List<CarListBean.DataEntity.CarListEntity> dataEntities;

    public PassengerCarModel(PassengerCarContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

        final Map map = new HashMap();
        map.put(Constants.TYPE, "1");
        HttpHelper.getInstance().initService().getBrandList(map).enqueue(new Callback<BrandListBean>() {
            @Override
            public void onResponse(Call<BrandListBean> call, Response<BrandListBean> response) {
                KLog.e("response:" + response.message());
                if (response.isSuccessful()) {
                    BrandListBean mbrandlistbean = response.body();
                    if (mbrandlistbean.getResult() == 1) {
                        listEntities = mbrandlistbean.getData().getBrandList();
                        if (getPresenter() != null)
                            getPresenter().onRequestBrandSuccess(listEntities);
                    } else {
                        if (getPresenter() != null)
                            getPresenter().onrequestBrandFailue(null);
                    }
                } else {
                    if (getPresenter() != null)
                        getPresenter().onrequestBrandFailue(null);
                }
            }


            @Override
            public void onFailure(Call<BrandListBean> call, Throwable t) {
                if (getPresenter() != null)
                    getPresenter().onrequestBrandFailue(t);
                KLog.d(t.getCause());
            }
        });

    }


    @Override
    public void createRequestUrl() {

    }

    @Override
    public void requestPassengerCarList(BrandListBean.DataEntity.BrandListEntity entity) {

        final Map map = new HashMap();
        map.put(Constants.TYPE, "1");
        map.put(Constants.BRAND, entity.getId());
        HttpHelper.getInstance().initService().getCarList(map).enqueue(new Callback<CarListBean>() {

            @Override
            public void onResponse(Call<CarListBean> call, Response<CarListBean> response) {
                if (response.isSuccessful()) {
                    CarListBean carListBean = response.body();
                    if (carListBean.getResult() == 1) {
                        dataEntities = carListBean.getData().getCarList();
                        if (getPresenter() != null) {
                            getPresenter().onRequestCarSuccess(dataEntities);
                        }
                    } else {
                        if (getPresenter() != null)
                            getPresenter().onrequestCarFailue(null);
                    }
                } else {
                    if (getPresenter() != null)
                        getPresenter().onrequestCarFailue(null);
                }
            }

            @Override
            public void onFailure(Call<CarListBean> call, Throwable t) {
                if (getPresenter() != null)
                    getPresenter().onrequestCarFailue(t);
                KLog.e(t.getCause());
            }
        });
    }
}
