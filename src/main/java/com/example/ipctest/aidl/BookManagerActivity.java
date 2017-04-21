package com.example.ipctest.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.ipctest.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIIVED = 1;

    private IBookManager mRemoteBookManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIIVED:
                    Log.d(TAG, "receive new book :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //用于将服务端的Binder对象转换城客户端所需的AIDL接口类型的对象，这种转换是区分进程的
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                Log.i(TAG, "query book list,list type: " + list.getClass().getCanonicalName());
                Log.i(TAG, "query book list: " + list.toString());
                Book newBook = new Book(3, "Android development art exploration");
                bookManager.addBook(newBook);
                Log.i(TAG, "add book:" + newBook);
                List<Book> newList = bookManager.getBookList();
                Log.i(TAG, "query book list:" + newList.toString());
                mRemoteBookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
            Log.e(TAG, "binder died.");
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIIVED, newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        //Android5.0 不允许隐式调用服务,使用以下方式
/*        Intent intent1 = new Intent();
        intent1.setComponent(new ComponentName("com.example.ipctest", "com.example.ipctest.aidl.BookManagerService"));*/
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                Log.i(TAG, "unregister listener:" + mOnNewBookArrivedListener);
                //这里的解注册逻辑是必然失败的，因为客户端与服务端根本不是同一个对象
                //对象的跨进程传输本质上都是反序列化的过程
                mRemoteBookManager.unRegisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }

    public void binderPoolTest(View view) {
        new Thread() {
            @Override
            public void run() {
                dowork();
            }
        }.start();
    }

    ISecurityCenter mSecurityCenter = null;
    ICompute mCompute = null;
    private void dowork() {
        BinderPool binderPool = BinderPool.getInstance(BookManagerActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG, "visit ISecurityCenter");
        String msg = "helloworld-安卓";
        Log.d(TAG, "content:" + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            Log.d(TAG, "encrypt:" + msg);
            Log.d(TAG, "decrypt:" + mSecurityCenter.decrypt(password));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Log.d(TAG, "3+6 = " + mCompute.add(3, 6));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
