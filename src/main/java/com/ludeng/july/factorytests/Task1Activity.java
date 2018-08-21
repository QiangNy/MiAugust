package com.ludeng.july.factorytests;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ludeng.july.R;
import com.ludeng.july.factorytests.Utils.ToolsUtil;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.imp.MiPresenterImp1;

import java.util.ArrayList;

public class Task1Activity extends Base2Activity {

    private static final String TAG = "chenguang";
    private Button runBtn,stopBtn;
    private Pig pig = new Pig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        runBtn= (Button) findViewById(R.id.runBtn);
        stopBtn= (Button) findViewById(R.id.stopBtn);

        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBtn.setClickable(false);

                ToolsUtil.setAlarm(getApplicationContext(), ToolsUtil.OLDTEST_ACTION_FINISHED, 10);// 10 minutes

                ArrayList arrayList = pig.getGroupsList();
                arrayList.add(1);
                arrayList.add(2);
                arrayList.add(4);
                pig.setGroupsList(arrayList);

                //is first time
                pig.setModelTaskStop(false);
                pig.setModelTaskStop(false);
                mPresenter = new MiPresenterImp1(Task1Activity.this,pig);

                mPresenter.startGroup(1);

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPresenter != null)
                    if (mPresenter != null) {
                        pig.setTimeTaskStop(true);
                        mPresenter.onStopTask(pig);
                    }

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

        if (mPig.isFinishTest()) {

            if (mPresenter != null) {
                mPresenter.onDestroy();
                mPresenter = null;
            }


            runBtn.setClickable(true);
        }
        Log.i(TAG, "Task1Activity class  refreshUI is Tid"+ Thread.currentThread().getId());

    }
}
