package com.ludeng.july.factorytests;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.ludeng.july.R;
import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.Utils.SPreference;
import com.ludeng.july.factorytests.Utils.Singleton;
import com.ludeng.july.factorytests.Utils.ToolsUtil;
import com.ludeng.july.factorytests.broadcast.Mi2BroadCast;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.MiContract;
import com.ludeng.july.factorytests.present.imp.MiPresenterImp1;

import java.util.ArrayList;
import java.util.Arrays;

public class Task1Activity extends Base2Activity implements MiContract.View, MiContract.RecieveListen,
        CompoundButton.OnCheckedChangeListener,AdapterView.OnItemSelectedListener {

    private static final String TAG = "chenguang";
    private Button runBtn,stopBtn;
    private Pig pig = new Pig();
    private Mi2BroadCast mi2BroadCast;
    private CheckBox mCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        runBtn= (Button) findViewById(R.id.runBtn);
        stopBtn= (Button) findViewById(R.id.stopBtn);

        mPresenter = new MiPresenterImp1(Task1Activity.this,pig);
        Spinner mSpinner = null;

        mSpinner.setOnItemSelectedListener(this);
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBtn.setClickable(false);

                SPreference.putBoolean(getApplicationContext(),SPreference.OLDTEST_SPRE_TASKSTART,true);

                ToolsUtil.setAlarm(getApplicationContext(), ToolsUtil.OLDTEST_ACTION_FINISHED, 10);// 10 minutes

                ArrayList arrayList = pig.getGroupsList();
                arrayList.add(0);
                arrayList.add(1);
                arrayList.add(2);
                pig.setGroupsList(arrayList);

                //is first time
                pig.setModelTaskStop(false);
                pig.setTimeTaskStop(false);


                StringBuffer sb = new StringBuffer();
                sb.setLength(0);
                Log.i(TAG, "the class is #1");
                mPresenter.startGroup(1);
                mPresenter.onDataInit();



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
    protected void onDestroy() {
        super.onDestroy();

        if (mi2BroadCast != null)
            unregisterReceiver(mi2BroadCast);
    }


    @Override
    public void doFreshUI(Pig mPig) {
        DswLog.i(TAG, "doFreshUI");
        this.pig = mPig;

        if (mPig.isFinishTest()) {

            if (mPresenter != null) {
                mPresenter.onDestroy();
                mPresenter = null;
            }

            runBtn.setClickable(true);
        }

    }

    @Override
    public void doFactoryTask() {

    }

    @Override
    public void doTimeOut() {
        DswLog.i(TAG, "doTimeOut");
        if (mPresenter != null) {
            mPresenter.onTimeOut(!SPreference.getBoolean(getApplicationContext(),SPreference.OLDTEST_SPRE_TASKSTART,false));
        }
    }

    @Override
    public void doRecieve(String action) {
        DswLog.i(TAG, "doRecieve action="+action);
        if (ToolsUtil.OLDTEST_ACTION_FINISHED.equals(action)) {
            SPreference.clearSPreference(getApplicationContext());
            if (mPresenter != null) {
                pig.setTimeTaskStop(true);
                mPresenter.onStopTask(pig);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
