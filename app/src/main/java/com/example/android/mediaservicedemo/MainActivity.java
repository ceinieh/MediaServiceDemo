package com.example.android.mediaservicedemo;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DownloadManager downloadManager;

   // String downloadFileUrl = "http://pngimg.com/download/549";
    String downloadFileUrl = "http://www.pdf995.com/samples/pdf.pdf";

    private long doanloadReference;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startPlayer(View view) {
        Intent service=new Intent(this, SoundService.class);
        startService(service);
    }

    public void stopPlayer(View view) {
        Intent intent = new Intent(this, SoundService.class);
        stopService(intent);
    }

    public void downloadFile(View view) {
        Cursor cursor = null;

        Uri uri = Uri.parse(downloadFileUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDescription("My Beautiful Downloader")
                .setTitle("Download the file");
        request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS, "downloaded_file.png");
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
        | DownloadManager.Request.NETWORK_MOBILE);

        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Query query = new DownloadManager.Query();
        if(query!=null) {
            query.setFilterByStatus(DownloadManager.STATUS_FAILED|DownloadManager.STATUS_PAUSED|DownloadManager.STATUS_SUCCESSFUL|
                    DownloadManager.STATUS_RUNNING|DownloadManager.STATUS_PENDING);
        } else {
            return;
        }
        cursor = downloadManager.query(query);
        if(cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    break;
                case DownloadManager.STATUS_PENDING:
                    break;
                case DownloadManager.STATUS_RUNNING:
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Toast.makeText(this, "Doanload success", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(this, "Doanload Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

   //     Toast.makeText(this, "" + DownloadManager.STATUS_SUCCESSFUL, Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent();
//        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
//        startActivity(intent);


    }


}
