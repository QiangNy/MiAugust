package com.ludeng.july.factorytests.present;

import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.view.BaseView;

public interface MiContract {
    interface Presenter extends BasePresenter {

        void isDone(Pig pig);

        void onStopTask(Pig pig);

        void onDestroy();

    }

    interface View extends BaseView<Presenter> {
        //刷新界面
        void refreshUI(Pig mPig);
        //显示错误界面
        void showError();

        void stopFactoryTask();
    }

}
