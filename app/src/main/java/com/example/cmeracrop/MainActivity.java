package com.example.cmeracrop;

import static com.google.android.gms.cast.framework.media.ImagePicker.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.cast.framework.media.ImagePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    Button btn, btn1;

    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.ivCroppedImage);
        btn = findViewById(R.id.btnChooseImage);
        btn1 = findViewById(R.id.btnSaveImg);


        btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                com.github.dhaval2404.imagepicker.ImagePicker.with(MainActivity.this)
                        .crop( )//Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .saveDir(new File(getExternalCacheDir(), "ImagePicker"))
                        .start( );
            }
        });

        btn1.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File( filepath.getAbsolutePath()+"/Demo/");
                dir.mkdir();
                File file = new File(dir, System.currentTimeMillis()+".jpg");
                try {
                    outputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace( );
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100,outputStream);
                Toast.makeText(getApplicationContext(), "Image saved to device", Toast.LENGTH_SHORT).show( );
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace( );
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace( );
                }

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData( );
        imageView.setImageURI(uri);


            }

        }




