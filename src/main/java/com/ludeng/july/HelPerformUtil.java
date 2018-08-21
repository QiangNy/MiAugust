package com.ludeng.july;

import android.os.Handler;

public class HelPerformUtil {
    private static final HelPerformUtil ourInstance = new HelPerformUtil();

    public static HelPerformUtil getInstance() {
        return ourInstance;
    }

    private Handler handler = new Handler();

    private OnPerformListen onPerformListen;

    private TRunnable mTRunnable = new TRunnable();

    private HelPerformUtil() {
    }



    public void setOnPerformListenNULL(OnPerformListen onPerformListen) {
        HelPerformUtil.this.onPerformListen = null;

    }

    public void performDelayed(OnPerformListen onPerformListen,int time) {
        mTRunnable.setOnPerformListen(onPerformListen);
        handler.postDelayed(mTRunnable,time);
    }

    private class TRunnable implements Runnable {

        private OnPerformListen  onPerformListen;

        public void setOnPerformListen(OnPerformListen onPerformListen) {
            HelPerformUtil.this.onPerformListen = onPerformListen;
        }


        @Override
        public void run() {
            HelPerformUtil.this.onPerformListen = TRunnable.this.onPerformListen;
            TRunnable.this.onPerformListen = null;

            if(HelPerformUtil.this.onPerformListen != null) {
                HelPerformUtil.this.onPerformListen.OnButtonPerform();
            }
        }
    }

}
