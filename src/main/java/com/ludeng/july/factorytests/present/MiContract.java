package com.ludeng.july.factorytests.present;

import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.view.BaseView;

public interface MiContract {
    interface Presenter extends BasePresenter {
        //获取数据
        void getData(int userId);
        //检查数据是否有效
        void checkData();

        void isDone(Pig pig);

        void onStopTask();

    }

    interface View extends BaseView<Presenter> {
        //显示加载中
        void showLoading();
        //刷新界面
        void refreshUI(Pig mPig);
        //显示错误界面
        void showError();

        void stopFactoryTask();
    }

}
