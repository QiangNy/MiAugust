package com.ludeng.july.factorytests;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ludeng.july.R;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.BasePresenter;
import com.ludeng.july.factorytests.present.MiContract;
import com.ludeng.july.factorytests.view.BaseView;

public class Base2Activity extends AppCompatActivity implements MiContract.View{

    protected MiContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    @Override
    public void refreshUI(Pig mPig) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void stopFactoryTask() {

    }


}
