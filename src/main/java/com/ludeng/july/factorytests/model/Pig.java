package com.ludeng.july.factorytests.model;

import java.util.ArrayList;

public class Pig {
    public static final String TAG = Pig.class.getName();


    private int groupID;
    private static boolean isTimeTaskStop;;
    private static boolean isModelTaskStop;
    private ArrayList groupsList = new ArrayList();
    private String box1,box2,box3,box4,box5;
    private boolean isNVRAM_FAC_FLAG;

    private boolean isTaskStart;

    public boolean isTaskStart() {
        return isTaskStart;
    }

    public void setTaskStart(boolean taskStart) {
        isTaskStart = taskStart;
    }

    public boolean isNVRAM_FAC_FLAG() {
        return isNVRAM_FAC_FLAG;
    }

    public void setNVRAM_FAC_FLAG(boolean NVRAM_FAC_FLAG) {
        isNVRAM_FAC_FLAG = NVRAM_FAC_FLAG;
    }

    public String getBox1() {
        return box1;
    }

    public void setBox1(String box1) {
        this.box1 = box1;
    }

    public String getBox2() {
        return box2;
    }

    public void setBox2(String box2) {
        this.box2 = box2;
    }

    public String getBox3() {
        return box3;
    }

    public void setBox3(String box3) {
        this.box3 = box3;
    }

    public String getBox4() {
        return box4;
    }

    public void setBox4(String box4) {
        this.box4 = box4;
    }

    public String getBox5() {
        return box5;
    }

    public void setBox5(String box5) {
        this.box5 = box5;
    }

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
