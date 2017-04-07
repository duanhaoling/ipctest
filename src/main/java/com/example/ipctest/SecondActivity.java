package com.example.ipctest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {

    private File[] folders = new File[10];
    private ViewGroup imageCollectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("SecondActivity", "UserManager.sUserId = " + UserManager.sUserId);
    }

    public void onclick(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    public void addImages() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (File folder : folders) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".png")) {
                            final Bitmap bitmap = getBitmapFromFile(file);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addImage(bitmap);
                                }
                            });
                        }
                    }
                }
            }
        }.start();
    }

    private void addImage(Bitmap bitmap) {
        ImageView iv = new ImageView(SecondActivity.this);
        iv.setImageBitmap(bitmap);
        imageCollectorView.addView(iv);
    }

    private Bitmap getBitmapFromFile(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        return bitmap;
    }


    public void addImagesRx() {
        Observable.from(folders)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        return getBitmapFromFile(file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        addImage(bitmap);
                    }
                });


    }

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();

    Observable<List<String>> query(String text) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(list1);

            }
        });
    }

}
