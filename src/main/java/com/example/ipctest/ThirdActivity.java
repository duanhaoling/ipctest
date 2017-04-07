package com.example.ipctest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ThirdActivity extends AppCompatActivity {
    private String tag = "duanhao";

    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        /** 1*/
        String[] name = {"hello", "Hi", "Aloha"};
        Observable.from(name).subscribe(onNextAction);
        /** 2*/
        observable.subscribe(subscriber);
        /** 3*/
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                observable1.subscribe(observer);
            }
        },10000);
        /**4*/
        Observable.just(1, 2, 3, 4, 5, 6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(tag, "number:" + integer);
                    }
                });
    }

    Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            Log.d(tag,s);
        }
    };

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item:" + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onNext(String s) {
            Log.d(tag, "Item:" + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };

    Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });

    Observable observable1 = Observable.just("Nice", "To", "Meet", "You");

    @Override
    protected void onStop() {
        if (!subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
