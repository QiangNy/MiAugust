package com.ludeng.july.factorytests.model;

import java.util.ArrayList;

public class Pig {
    public static final String TAG = Pig.class.getName();
    public static final int ERROR_IS_INIT = -1;
    public static final int ERROR_IS_SUCCED = 1;

    private String actionName;
    private int groupID;
    private static boolean isTimeTaskStop;;
    private static boolean isModelTaskStop;
    private ArrayList groupsList = new ArrayList();


    public ArrayList getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(ArrayList groupsList) {
        this.groupsList = groupsList;
    }

    public int getnextGroupID() {
        int index = groupsList.indexOf(groupID);
        if (index < groupsList.size() - 1) {
            return (int) groupsList.get(index + 1);
        }else {
            return (int) groupsList.get(0);
        }
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public boolean isTimeTaskStop() {
        return isTimeTaskStop;
    }

    public void setTimeTaskStop(boolean timeTaskStop) {
        isTimeTaskStop = timeTaskStop;
    }

    public boolean isModelTaskStop() {
        return isModelTaskStop;
    }

    public void setModelTaskStop(boolean modelTaskStop) {
        isModelTaskStop = modelTaskStop;
    }

    public boolean isFinishTest() {
        return isModelTaskStop && isTimeTaskStop;
    }


}
