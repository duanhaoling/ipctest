// IBookManager.aidl
package com.example.ipctest.aidl;

// Declare any non-default types here with import statements
import com.example.ipctest.aidl.Book;
import com.example.ipctest.aidl.IOnNewBookArrivedListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);
}
