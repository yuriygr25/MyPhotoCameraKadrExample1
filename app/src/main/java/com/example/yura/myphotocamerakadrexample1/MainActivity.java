package com.example.yura.myphotocamerakadrexample1;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.math.RoundingMode;

public class MainActivity extends Activity {

//    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_REQUEST = 2;
    private static final int CAMERA_CAPTURE = 3;

    private static final int REQUEST_IMAGE_CAPTURE=1;
    private static final int PIC_CROP = 2;
    ImageView imageView,imageView2;
    private Uri picUri;
    Bitmap photo;
    Button crop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)this.findViewById(R.id.imageView1);
        imageView2 = (ImageView)this.findViewById(R.id.imageView2);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        Button cropButton=(Button)findViewById(R.id.button2);

        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                startActivityForResult(intent, CAMERA_REQUEST);
            }}
            );
/*
        cropButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                startActivityForResult(intent, CAMERA_CAPTURE);
            }}
        );
*/

//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            // Получим кадрированное изображение
            Bitmap thePic = extras.getParcelable("data");
            // передаём его в ImageView
            imageView.setImageBitmap(thePic);
            // Получим Uri снимка
            picUri = data.getData();
            //performcrop();

            //Альтернативный способ
            photo = (Bitmap) data.getExtras().get("data");
            int width = photo.getWidth(); int height = photo.getHeight();
            int scale=20;
            int width1= (int)(width/100*scale)-1;
            int height1=(int)(height/100*scale)-1;

            Bitmap croppedBmp = Bitmap.createBitmap(photo,  (int)((width-width1)/2),
                                                            (int)((height-height1)/2),
                                                            (int)((width-width1)/2)+width1,
                                                            (int)((height-height1)/2)+height1);

            imageView2.setImageBitmap(croppedBmp);

        }

/*
        if (requestCode == CAMERA_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            // Получим кадрированное изображение
            Bitmap thePic = extras.getParcelable("data");
            // передаём его в ImageView
            imageView2.setImageBitmap(thePic);
        }
*/
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void performcrop() {

                try {
                    // Намерение для кадрирования. Не все устройства поддерживают его
                    //Intent cropIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //Intent cropIntent = new Intent("com.android.camera.action.CROP");
                    Intent cropIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                    cropIntent.setDataAndType(picUri, "image/*");
                    cropIntent.putExtra("crop", "true");
                    cropIntent.putExtra("aspectX", 1);
                    cropIntent.putExtra("aspectY", 1);
                    cropIntent.putExtra("outputX", 80);
                    cropIntent.putExtra("outputY", 80);
                    cropIntent.putExtra("return-data", true);

                        startActivityForResult(cropIntent, CAMERA_CAPTURE);
                }
                catch(ActivityNotFoundException anfe){
                    String errorMessage = "Извините, но ваше устройство не поддерживает кадрирование";
                    Toast toast = Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
    private void manualcrop(int x, int y, int x1, int y1) {
        Bitmap croppedBmp = Bitmap.createBitmap(photo, x, y, x1,y1);
        imageView2.setImageBitmap(croppedBmp);

    }

/*
//                Bitmap croppedBmp = Bitmap.createBitmap(photo, 250, 250, photo.getWidth()-250,photo.getHeight()-100);

                Bitmap croppedBmp = Bitmap.createBitmap(photo, 0, 0, width/2,height/2);
                imageView2.setImageBitmap(croppedBmp);
*/

}