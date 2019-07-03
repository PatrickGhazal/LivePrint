package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Create Link page.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class CreateLink extends AppCompatActivity {

    private static boolean fromArtificialLogin = false;

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
    }

    /**
     * <code>onClick</code> method for the <code>Load Photo</code> button.
     * Loads a photo from the gallery.
     * TODO: Update the Javadoc when the method is properly implemented
     *
     * @param v view that contains the clicked button
     */
    public void loadPhoto(View v) {

        TextView tvImage = findViewById(R.id.selected_image);
        tvImage.setText("chosen_image.jpg");
    }

    /**
     * <code>onClick</code> method for the <code>Load Video</code> button.
     * Loads a video from the gallery.
     * TODO: Update the Javadoc when the method is properly implemented
     *
     * @param v view that contains the clicked button
     */
    public void loadVideo(View v) {

        TextView tvVideo = findViewById(R.id.selected_video);
        tvVideo.setText("chosen_video.mp4");

    }

    /**
     * <code>onClick</code> method for the <code>Create Link</code> button.
     * Creates a new link based on the provided photo and video file names.
     *
     * @param v view that contains the clicked button
     */
    public void createLink(View v) {
        TextView tvImage = findViewById(R.id.selected_image);
        TextView tvVideo = findViewById(R.id.selected_video);

        String selectedImage = tvImage.getText().toString();
        String selectedVideo = tvVideo.getText().toString();

        if (!selectedImage.equals("") && !selectedVideo.equals("")) {

            Link newLink = new Link(selectedImage, selectedVideo);

            if (Login.currLoggedInComp != null) {
                Login.currLoggedInComp.addLink(newLink);
                if (fromArtificialLogin) {
                    Login.currLoggedInComp = null;
                    fromArtificialLogin = false;
                }
                Funcs.saveFullData(this);
                Intent intent = new Intent(this, ControlPanel.class);
                startActivity(intent);
            } else {
                promptCompany();
            }
        } else {
            TextView tvSuccess = findViewById(R.id.create_success);
            tvSuccess.setText(R.string.lpc3_create_link_fail);
        }
    }

    /**
     * <code>onClick</code> method for the second <code>Create Link</code> button.
     * This button appears only if no company is currently logged in.
     * It simulates a login for the duration of the creation of the link.
     *
     * @param v view that contains the clicked button
     */
    public void createLinkWithCompany(View v) {
        EditText compET = (EditText) findViewById(R.id.lpc3_unknown_comp_et);
        EditText compPassET = (EditText) findViewById(R.id.lpc3_unknown_comp_pass_et);
        String compName = compET.getText().toString();
        String compPass = compPassET.getText().toString();

        Data data = Data.getInstance();
        Company foundComp = data.getCompByName(compName);
        if (foundComp != null && foundComp.getPassword().equals(compPass)) {
            Login.currLoggedInComp = foundComp;
            fromArtificialLogin = true;
            createLink(v);
        } else {
            TextView tvSuccess = findViewById(R.id.create_success);
            tvSuccess.setText(R.string.lpc3_create_link_fail);
        }
    }

    /**
     * Makes the secondary login views visible to provide a logged in company to add the link to
     */
    private void promptCompany() {
        TextView compTV = (TextView) findViewById(R.id.lpc3_unknown_comp_tv);
        LinearLayout compLayout = (LinearLayout) findViewById(R.id.lpc3_unknown_comp_credentials);
        EditText compET = (EditText) findViewById(R.id.lpc3_unknown_comp_et);
        EditText compPassET = (EditText) findViewById(R.id.lpc3_unknown_comp_pass_et);
        Button compButton = (Button) findViewById(R.id.lpc3_unknown_comp_button);
        Button oldButton = (Button) findViewById(R.id.lpc3_create_link_button);

        compTV.setVisibility(View.VISIBLE);
        compLayout.setVisibility(View.VISIBLE);
        compET.setVisibility(View.VISIBLE);
        compPassET.setVisibility(View.VISIBLE);
        compButton.setVisibility(View.VISIBLE);
        oldButton.setVisibility(View.INVISIBLE);
    }

    /**
     * <code>onClick</code> method for the <code>Back</code> button
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Intent intent = new Intent(this, ControlPanel.class);
        finish();
        startActivity(intent);
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
