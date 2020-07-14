package com.example.myapplication;

import java.util.ArrayList;

public interface FireBaseDBCallback {
    public void writeResult(boolean flag);
    public void updateResult(boolean flag);
    public void readResult(UserInfo info, boolean flag);
    public void readAllResult(ArrayList<UserInfo> infolist, boolean flag);
}
