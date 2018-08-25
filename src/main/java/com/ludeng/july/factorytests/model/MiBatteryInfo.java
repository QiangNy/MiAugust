package com.ludeng.july.factorytests.model;

import android.os.BatteryManager;

import com.ludeng.july.R;
import com.ludeng.july.factorytests.Base2Activity;
import com.ludeng.july.factorytests.utils.DswLog;
import com.ludeng.july.factorytests.utils.Singleton;
import com.ludeng.july.factorytests.utils.ToolsUtil;

public class MiBatteryInfo {

    private static final String TAG = "MiBatteryInfo";
    public static final String CHARGE_CURRENT_PATH = "/sys/class/power_supply/battery/current_now";
    public static final String CHARGE_HIC_CURRENT_PATH = "/sys/class/power_supply/hic_battery/current_now";
    public static final String CHARGE_CURRENT_PATH_MTK = "/sys/class/power_supply/battery/BatteryAverageCurrent";
    public static final String NODE_TYPE_BATTERY_OLD_TEST = "NODE_TYPE_BATTERY_OLD_TEST";
    private static final int CHARGE_TARGET_VALUE = 200;

    public MiBatteryInfo() {
    }


    private int status,health,level,scale,icon_small,plugged,voltage,temperature;
    private boolean isPresent;
    private String technology;
    private String content;
    private boolean chargePass;

    public boolean isChargePass() {
        return chargePass;
    }

    public void setChargePass() {
        if (0 != plugged && getCurtageToInt() > CHARGE_TARGET_VALUE) {
            DswLog.i(TAG, "current success");
            this.chargePass = true;
        }else {
            this.chargePass = false;
        }
    }

    public String getStatus() {
        String statusString = "";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                statusString = Singleton.getInstance().getmContext().getString(R.string.battery_status_unknow);
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusString = Singleton.getInstance().getmContext().getString(R.string.battery_status_charging);
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusString = Singleton.getInstance().getmContext().getString(R.string.battery_status_discharging);
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusString = Singleton.getInstance().getmContext().getString(R.string.battery_status_nocharging);
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusString = Singleton.getInstance().getmContext().getString(R.string.battery_status_full);
                break;
        }
        return statusString;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHealth() {
        String healthString = "";

        switch (health) {
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                healthString = Singleton.getInstance().getmContext().getString(R.string.battery_health_unknow);
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthString = Singleton.getInstance().getmContext().getString(R.string.battery_health_good);
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthString = Singleton.getInstance().getmContext().getString(R.string.battery_health_overheart);
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthString = Singleton.getInstance().getmContext().getString(R.string.battery_health_dead);
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthString = Singleton.getInstance().getmContext().getString(R.string.battery_health_over_voltage);
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthString = Singleton.getInstance().getmContext().getString(R.string.battery_health_unspecified_failure);
                break;
        }
        return healthString;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getIcon_small() {
        return icon_small;
    }

    public void setIcon_small(int icon_small) {
        this.icon_small = icon_small;
    }

    public String getPlugged() {
        String plugString = Singleton.getInstance().getmContext().getString(R.string.no_battery_plugged);
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugString = Singleton.getInstance().getmContext().getString(R.string.battery_plugged_ac);
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugString = Singleton.getInstance().getmContext().getString(R.string.battery_plugged_usb);
                break;
        }
        return plugString;
    }

    public void setPlugged(int plugged) {
        this.plugged = plugged;
    }

    public String getCurtage() {
        String curtage = ToolsUtil.getNodeData(CHARGE_CURRENT_PATH_MTK);
        DswLog.i(TAG, "curtage="+curtage);
        return curtage;
    }

    private int getCurtageToInt() {
        int curtage = 0;
        try {
            curtage = Integer.parseInt(getCurtage());
        }catch (Exception e) {
            DswLog.i(TAG, "getCurtageToInt Fail");
            e.printStackTrace();
        }
        return curtage;
    }

    public void setContent() {

        StringBuffer sb = new StringBuffer();

        if (plugged != 0 && voltage > 0) {
            sb.append(Singleton.getInstance().getmContext().getString(R.string.battery_status))
                    .append(getStatus())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_health))
                    .append(getHealth())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_plugged))
                    .append(getPlugged())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.charge_v))
                    .append(voltage)
                    .append(" mV\n")
                    .append(String.format(Singleton.getInstance().getmContext().getString(R.string.charge_i),CHARGE_TARGET_VALUE))
                    .append(getCurtage())
                    .append(" mA\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_capacity))
                    .append(getScale())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_power))
                    .append(getLevel())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_technology))
                    .append(getTechnology())
                    .append("\n");
        } else {
            sb.append(Singleton.getInstance().getmContext().getString(R.string.battery_status))
                    .append(getStatus())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_health))
                    .append(getHealth())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_plugged))
                    .append(getPlugged())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.charge_v))
                    .append(voltage)
                    .append(" mV\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_capacity))
                    .append(getScale())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_power))
                    .append(getLevel())
                    .append("\n")
                    .append(Singleton.getInstance().getmContext().getString(R.string.battery_technology))
                    .append(getTechnology())
                    .append("\n");
        }


        this.content = sb.toString();
    }

    public String getContent() {

        return content;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
