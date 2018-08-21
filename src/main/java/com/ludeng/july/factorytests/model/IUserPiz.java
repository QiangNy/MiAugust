package com.ludeng.july.factorytests.model;

public interface IUserPiz{

    interface task<T> extends MBasePresenter{
        T getTaskName();
        void setTaskName(T var);
    }

}
