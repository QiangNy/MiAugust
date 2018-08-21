package com.ludeng.july.factorytests;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ludeng.july.R;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.imp.MiPresenterImp1;

public class Task1Activity extends Base2Activity {

    private Button runBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runBtn= (Button) findViewById(R.id.runBtn);

        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter = new MiPresenterImp1(Task1Activity.this);
                mPresenter.startGroup(1);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();




    }


    @Override
    public void stopFactoryTask() {
        super.stopFactoryTask();
    }


    @Override
    public void refreshUI(Pig mPig) {
        super.refreshUI(mPig);

        mPresenter = null;
        Log.i("chengq", "the class is mm");
    }
}
