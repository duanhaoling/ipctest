package com.example.ipctest.provider;

        import android.net.Uri;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import com.example.ipctest.R;

public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri uri = Uri.parse("content://com.example.ipctest.book.provider");
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
    }
}
