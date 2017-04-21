package com.example.ipctest.aidl;

import android.os.RemoteException;

/**
 * Created by ldh on 2017/4/20 0020.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
