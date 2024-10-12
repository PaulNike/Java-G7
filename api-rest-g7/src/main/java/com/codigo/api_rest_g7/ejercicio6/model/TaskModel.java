package com.codigo.api_rest_g7.ejercicio6.model;

public class TaskModel {

    private int numTask;
    private String nameTask;
    private String stateTask;

    public int getNumTask() {
        return numTask;
    }

    public void setNumTask(int numTask) {
        this.numTask = numTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getStateTask() {
        return stateTask;
    }

    public void setStateTask(String stateTask) {
        this.stateTask = stateTask;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "numTask=" + numTask +
                ", nameTask='" + nameTask + '\'' +
                ", stateTask='" + stateTask + '\'' +
                '}';
    }
}
