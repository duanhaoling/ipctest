package com.example.ipctest.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ldh on 2016/8/4 0004.
 */
public class Book implements Parcelable {
    public int bookId;
    public String bookName;

    public Book() {
    }

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    //返回当前对象的内容描述
    @Override
    public int describeContents() {
        return 0;
    }

    //将当前对象写入序列化结构中
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookId);
        dest.writeString(this.bookName);
    }

    //从序列化的对象中创建原始对象
    protected Book(Parcel in) {
        this.bookId = in.readInt();
        this.bookName = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        //从序列化的对象中创建原始对象
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        //创建指定长度的原始对象数组
        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
