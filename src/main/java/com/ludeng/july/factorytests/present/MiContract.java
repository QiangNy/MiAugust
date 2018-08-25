package com.ludeng.july.factorytests.present;

import com.ludeng.july.factorytests.model.MiBatteryInfo;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.view.BaseView;

public interface MiContract {
    interface Presenter extends BasePresenter {

        void onDone(Pig pig);

        void onStopTask(Pig pig);

        void onDestroy();

        void onTimeOut(boolean isTimeOut);

        void onDataInit();

    }

    interface View extends BaseView<Presenter> {
        //刷新界面
        void doFreshUI(Pig mPig);

        void doFactoryTask();

        void doTimeOut();
    }

    interface RecieveListen<T> {
        void doRecieve(String action, T var);

    }

}
