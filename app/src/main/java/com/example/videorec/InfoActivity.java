package com.example.videorec;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

public class InfoActivity  extends AppCompatActivity {

    private final int VIDEO_REQUEST_CODE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        checkFolderPermission();

            }

    private void checkFolderPermission() {
        if (PermissionManager.checkIsGreaterThanM()) {
            if (!PermissionManager.checkPermissionForWriteExternalStorage(InfoActivity.this) ||
                    !PermissionManager.checkPermissionForCamara(InfoActivity.this) ||
                    !PermissionManager.checkPermissionForRecordAudio (InfoActivity.this) ) {
                PermissionManager.requestPermissionForAll(InfoActivity.this);
            } else {
                Log.e("recordVideo", "Permission_1");
//                launchApp();
                    }
                } else {
//                 launchApp();
                Log.e("recordVideo", "Permission_Else");
                    }
                }

     public void captureVideo(View view) {
         Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
         File videoFile = getFilepath();

         Uri imageUri = FileProvider.getUriForFile(
                 InfoActivity.this,
                 "com.example.videorec.fileprovider", //(use your app signature + ".provider" )
                 videoFile);

//         Uri videoUri = Uri.fromFile(videoFile);
         cameraIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
         cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
         cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
         startActivityForResult(cameraIntent, VIDEO_REQUEST_CODE);
                }

     public File getFilepath() {

        File folder = new File("sdcard/video_app");
        if(!folder.exists()) {
            folder.mkdir();
                }

        File video_file = new File(folder, "sample_video.mp4");


        return video_file;
            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(getApplicationContext(), "Video Successfully Recorded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Video Record Failed...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
