package com.ludeng.july.factorytests;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.ludeng.july.R;
import com.ludeng.july.factorytests.utils.DswLog;
import com.ludeng.july.factorytests.utils.ToolsUtil;
import com.ludeng.july.factorytests.broadcast.Mi2BroadCast;
import com.ludeng.july.factorytests.model.MiBatteryInfo;
import com.ludeng.july.factorytests.present.MiContract;

public class CTaskActivity extends Base2Activity implements MiContract.RecieveListen<MiBatteryInfo> {

    private static final String TAG = "CTaskActivity";
    private Mi2BroadCast mi2BroadCast;
    private TextView mContentTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charge_activity);

        initView();
        
        initData();
    }

    private void initData() {
        mi2BroadCast = new Mi2BroadCast(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ToolsUtil.OLDTEST_ACTION_CHARGEED);
        intentFilter.addAction(ToolsUtil.OLDTEST_ACTION_CHARSTOP);
        registerReceiver(mi2BroadCast,intentFilter);

        ToolsUtil.setAlarm(getApplicationContext(), ToolsUtil.OLDTEST_ACTION_CHARSTOP, 5);// 5 minutes
    }

    private void initView() {
        mContentTv = (TextView) findViewById(R.id.chargetest_content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mi2BroadCast);
    }

    @Override
    public void doRecieve(String action, MiBatteryInfo var) {
        if (action.equals(ToolsUtil.OLDTEST_ACTION_CHARGEED)) {
            mContentTv.setText(var.getContent());

            if (var.isChargePass()) {
                DswLog.i(TAG, "is pass");
                ToolsUtil.cancelAlarm(getApplicationContext(), ToolsUtil.OLDTEST_ACTION_CHARSTOP);
                setResult(RESULT_OK);

                finish();
            }
        }else if (action.equals(ToolsUtil.OLDTEST_ACTION_CHARSTOP)) {
            DswLog.i(TAG, "is fail");
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
