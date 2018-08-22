package com.ludeng.july.factorytests;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ludeng.july.R;
import com.ludeng.july.factorytests.Utils.SPreference;
import com.ludeng.july.factorytests.Utils.Singleton;
import com.ludeng.july.factorytests.Utils.ToolsUtil;
import com.ludeng.july.factorytests.broadcast.Mi2BroadCast;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.MiContract;
import com.ludeng.july.factorytests.present.imp.MiPresenterImp1;

import java.util.ArrayList;

public class Task1Activity extends Base2Activity implements MiContract.View, MiContract.RecieveListen{

    private static final String TAG = "chenguang";
    private Button runBtn,stopBtn;
    private Pig pig = new Pig();
    private Mi2BroadCast mi2BroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        runBtn= (Button) findViewById(R.id.runBtn);
        stopBtn= (Button) findViewById(R.id.stopBtn);
        Singleton.getInstance().setmContext(getApplicationContext());

        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBtn.setClickable(false);

                SPreference.putBoolean(getApplicationContext(),SPreference.OLDTEST_SPRE_TASKSTART,true);

                ToolsUtil.setAlarm(getApplicationContext(), ToolsUtil.OLDTEST_ACTION_FINISHED, 0.5f);// 10 minutes

                ArrayList arrayList = pig.getGroupsList();
                arrayList.add(0);
                arrayList.add(1);
                arrayList.add(2);
                pig.setGroupsList(arrayList);

                //is first time
                pig.setModelTaskStop(false);
                pig.setTimeTaskStop(false);


                mPresenter = new MiPresenterImp1(Task1Activity.this,pig);
                Log.i(TAG, "the class is #1");
                mPresenter.startGroup(1);

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SPreference.putBoolean(getApplicationContext(),SPreference.OLDTEST_SPRE_TASKSTART,false);

                ToolsUtil.cancelAlarm(getApplicationContext(), ToolsUtil.OLDTEST_ACTION_FINISHED);

                if (mPresenter != null) {
                    pig.setTimeTaskStop(true);
                    mPresenter.onStopTask(pig);
                }



            }
        });

        mi2BroadCast = new Mi2BroadCast(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ToolsUtil.OLDTEST_ACTION_FINISHED);
        registerReceiver(mi2BroadCast,intentFilter);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void stopFactoryTask() {

    }

    @Override
    public void isTimeOut() {
        if (mPresenter != null) {
            mPresenter.onTimeOut(!SPreference.getBoolean(getApplicationContext(),SPreference.OLDTEST_SPRE_TASKSTART,false));
        }
    }


    @Override
    public void refreshUI(Pig mPig) {

        this.pig = mPig;

        if (mPig.isFinishTest()) {

            if (mPresenter != null) {
                mPresenter.onDestroy();
                mPresenter = null;
            }


            runBtn.setClickable(true);
        }
        Log.i(TAG, "Task1Activity class  refreshUI is Tid"+ Thread.currentThread().getId());

    }

    @Override
    public void showError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mi2BroadCast != null)
            unregisterReceiver(mi2BroadCast);
    }

    @Override
    public void onRecieve(String action) {
        if (ToolsUtil.OLDTEST_ACTION_FINISHED.equals(action)) {
            SPreference.clearSPreference(getApplicationContext());
            if (mPresenter != null) {
                pig.setTimeTaskStop(true);
                mPresenter.onStopTask(pig);
            }
        }
    }
}
