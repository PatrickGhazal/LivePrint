package griffmedia.ghazal.liveprint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.fabric.sdk.android.Fabric;

public class CreateLinkPage extends AppCompatActivity {

    private static final double gapPerc1 = 1.31;
    private static final double gapPerc24 = 0.88;
    private static final double gapPerc3 = 2.62;
    private static final double gapPerc5 = 4.38;

//    private static final int GET_FROM_GALLERY = 77;
//    private Bitmap mImageBitmap;
//    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_create_link_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFormatting();
    }

    public void loadPhoto(View v) {

        TextView tvImage = findViewById(R.id.selected_image);
        tvImage.setText("chosen_image.jpg");
    }

    public void loadVideo(View v) {


        TextView tvVideo = findViewById(R.id.selected_video);
        tvVideo.setText("chosen_video.mp4");

    }

    public void createLink(View v) {
        TextView tvImage = findViewById(R.id.selected_image);
        TextView tvVideo = findViewById(R.id.selected_video);

        String selectedImage = tvImage.getText().toString();
        String selectedVideo = tvVideo.getText().toString();

        if (!selectedImage.equals("") && !selectedVideo.equals("")) {

            Intent intent = new Intent(this, ControlPanel.class);
            startActivity(intent);
        } else {
            TextView tvSuccess = findViewById(R.id.create_success);
            tvSuccess.setText("Failure..");
        }
    }

    private void setFormatting() {
        View v1 = findViewById(R.id.lpc3_empty1);
        View v2 = findViewById(R.id.lpc3_empty2);
        View v3 = findViewById(R.id.lpc3_empty3);
        View v4 = findViewById(R.id.lpc3_empty4);
        View v5 = findViewById(R.id.lpc3_empty5);

        setGapSizes(v1, v2, v3, v4, v5);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour(Data.bgColourBeforeM, v1, v2, v3, v4, v5);
        }
    }

    private void setGapSizes(View v1, View v2, View v3, View v4, View v5) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double fullWindowHeight = displayMetrics.heightPixels + 0.0;
        double density = displayMetrics.density;

        // add a dividing factor for screens with height < 1000 px
        if (fullWindowHeight < 1000)
            density /= 1.5;

        Funcs.setGapHeight(v1, Funcs.resizeHeight(gapPerc1, fullWindowHeight), density);
        Funcs.setGapHeight(v2, Funcs.resizeHeight(gapPerc24, fullWindowHeight), density);
        Funcs.setGapHeight(v3, Funcs.resizeHeight(gapPerc3, fullWindowHeight), density);
        Funcs.setGapHeight(v4, Funcs.resizeHeight(gapPerc24, fullWindowHeight), density);
        Funcs.setGapHeight(v5, Funcs.resizeHeight(gapPerc5, fullWindowHeight), density);
    }

    /*
        The following commented methods were designed to upload an image from the gallery.
        The behaviour could not reproduced after an initial success, so the methods are
        set aside for the moment in order to work on something else.
        For clarity purposes, the concerned methods are:
            loadPhoto
            loadPhotoFullIntent
            onRequestPermissionsResult
            hasPermissions
            createImageFile
            onActivityResult
        Three global variables were also commented out for the same reason.

     */

//    public void loadPhoto(View v) {
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            if (!hasPermissions(this, PERMISSIONS)) {
//                ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, GET_FROM_GALLERY );
//            } else {
//                loadPhotoFullIntent();
//            }
//        } else {
//            loadPhotoFullIntent();
//        }
//    }

//    private void loadPhotoFullIntent() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        Intent cameraIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                startActivityForResult(cameraIntent, GET_FROM_GALLERY);
//            }
//        }
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case GET_FROM_GALLERY: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    loadPhotoFullIntent();
//                } else {
//                    System.out.println("-----ERROR: Could not write-----");
//                }
//            }
//        }
//    }
//
//    private static boolean hasPermissions(Context context, String... permissions) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DCIM), "Camera");
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
//        return image;
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK) {
//            try {
//                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
//                ImageView imageView = findViewById(R.id.disp_image);
//                imageView.setImageBitmap(mImageBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
