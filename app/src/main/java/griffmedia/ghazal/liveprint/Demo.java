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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Demo class for the inspection process based on LivePrint.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class Demo extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 77;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private static ArrayList<Question> questions;
    private static Question currQuestion;
    private static int currQuestionInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questions = new ArrayList<Question>();
        questions.add(createQuestion("engine", "1st engine Question", "1st engine Answer", "2nd engine Answer"));
        questions.add(createQuestion("engine", "2nd engine Question", "3rd engine Answer", "4th engine Answer"));
        questions.add(createQuestion("engine", "3rd engine Question", "4th engine Answer", "5th engine Answer", "other"));
    }

    /**
     * <code>onClick</code> method for the <code>Camera</code> button.
     * Takes a new picture with the camera.
     *
     * @param v view that contains the clicked button
     */
    public void demoCamera(View v) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(this, permissions)) {
                ActivityCompat.requestPermissions((Activity) this, permissions, GET_FROM_GALLERY);
            } else {
                loadPhotoFullIntent();
            }
        } else {
            loadPhotoFullIntent();
        }
    }

    /**
     * Does the actual work of loading the photo. Called by <code>loadPhoto</code>.
     */
    private void loadPhotoFullIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, GET_FROM_GALLERY);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GET_FROM_GALLERY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadPhotoFullIntent();
                } else {
                    System.out.println("-----ERROR: Could not write-----");
                }
            }
        }
    }

    /**
     * Checks that the necessary permissions are granted.
     *
     * @param context     <code>this Activity</code>
     * @param permissions names of the permissions to be checked
     * @return true if all necessary permissions are granted
     */
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates an image file to store the newly-taken picture.
     *
     * @return the newly-created image file
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
//                System.out.println(mImageBitmap.getHeight());
                if (mImageBitmap != null) {
                    ImageView imageView = findViewById(R.id.demo_image);
                    imageView.setImageBitmap(mImageBitmap);
                    setupForm();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets up the view elements used to fill out the form.
     */
    private void setupForm() {
        String foundResource = setIdentificationString();
        setupSpinner(0, foundResource);
    }

    /**
     * Sets up part of the view elements used to fill out the form.
     *
     * @return the name of the resource identified in the photo
     */
    private String setIdentificationString() {
        String recognized = " " + recognizeImage(mImageBitmap);

        TextView idTV = (TextView) findViewById(R.id.demo_identification);
        idTV.setText(idTV.getText() + recognized);
        idTV.setVisibility(View.VISIBLE);

        TextView introTV = (TextView) findViewById(R.id.demo_intro);
        Button cameraButton = (Button) findViewById(R.id.demo_camera);
        introTV.setVisibility(View.INVISIBLE);
        cameraButton.setVisibility(View.INVISIBLE);

        return recognized;
    }

    // TODO: hardcoded

    /**
     * Identifies a resource in a picture.
     *
     * @param bm Bitmap that contains the picture to be identified
     * @return the name of the identified resource
     */
    private String recognizeImage(Bitmap bm) {
        return "engine";
    }

    /**
     * Sets up the spinner and other view elements to answer the questions.
     *
     * @param qNum    question number
     * @param resName name of the currently identified resource
     */
    private void setupSpinner(int qNum, String resName) {

        TextView questionTV = (TextView) findViewById(R.id.demo_question);
        questionTV.setVisibility(View.VISIBLE);

        Button answerButton = (Button) findViewById(R.id.demo_button);
        answerButton.setVisibility(View.VISIBLE);

        Spinner spinner = (Spinner) findViewById(R.id.answers_spinner);
        spinner.setVisibility(View.VISIBLE);

        Question question = findQuestion(qNum, resName);
        currQuestion = question;
        if (currQuestion != null) {

            questionTV.setText(currQuestion.question);
            ArrayList<String> answers = currQuestion.answers;

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, answers);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        } else {
            spinner.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Creates a <code>Question</code> instance based on the provided parameters.
     *
     * @param resName  resource to which the question is attached
     * @param question text of the question
     * @param answers  one or more possible answers to the question
     * @return the new <code>Question</code> instance
     */
    private Question createQuestion(String resName, String question, String... answers) {
        Question newQ = new Question(resName, question);
        for (String answer : answers) {
            newQ.addAnswer(answer);
        }
        return newQ;
    }

    /**
     * Finds the next question to aske based on the resource name and the question number.
     *
     * @param qNum    question number
     * @param resName name of the currently identified resource
     * @return the appropriate <code>Question</code> instance
     */
    private Question findQuestion(int qNum, String resName) {
        ArrayList<Question> qsWithRightRes = qetQuestionsPerResName(resName);
        if (qsWithRightRes.size() > qNum) {
            currQuestionInt = qNum;
            return qsWithRightRes.get(qNum);
        }
        return null;
    }

    /**
     * Identifies all questions associated to the resource name.
     *
     * @param resName resource for which the questions are extracted
     * @return an <code>ArrayList</code> of all <code>Questions</code> associated to tha given resource
     */
    private ArrayList<Question> qetQuestionsPerResName(String resName) {
        ArrayList<Question> qsWithRightRes = new ArrayList<Question>();
        for (Question q : questions) {
            if (q.resource.trim().equals(resName.trim())) {
                qsWithRightRes.add(q);
            }
        }
        return qsWithRightRes;
    }

    /**
     * <code>onClick</code> method for the <code>Answer</code> button.
     * Processes the given answer.
     *
     * @param v view that contains the clicked button
     */
    public void nextQuestion(View v) {

        Spinner spinner = (Spinner) findViewById(R.id.answers_spinner);
        String answer = String.valueOf(spinner.getSelectedItem());

        if (answer.equals("other")) {
            EditText fillET = (EditText) findViewById(R.id.demo_fill_text);
            Button fillButton = (Button) findViewById(R.id.demo_button_filler);
            fillET.setVisibility(View.VISIBLE);
            fillButton.setVisibility(View.VISIBLE);

            Button ogAnswerButton = (Button) findViewById(R.id.demo_button);
            ogAnswerButton.setVisibility(View.INVISIBLE);
        } else {
            questionAnswered(answer);
        }
    }

    /**
     * <code>onClick</code> method for the second <code>Answer</code> button.
     * Processes the user-provided answer.
     *
     * @param v view that contains the clicked button
     */
    public void nextQuestionFiller(View v) {
        EditText fillET = (EditText) findViewById(R.id.demo_fill_text);
        String answer = fillET.getText().toString();

        questionAnswered(answer);
    }

    /**
     * Processes the answer, saves it, and loads the next question.
     *
     * @param answer chosen answer
     */
    private void questionAnswered(String answer) {
        ArrayList<Question> qsWithRightResName = qetQuestionsPerResName(currQuestion.resource);

        saveAnswer(answer);

        if (qsWithRightResName.size() > currQuestionInt + 1) {
            setupSpinner(++currQuestionInt, currQuestion.resource);
        } else {

            currQuestion = null;
            currQuestionInt = 0;

            TextView introTV = (TextView) findViewById(R.id.demo_intro);
            introTV.setVisibility(View.VISIBLE);

            Button cameraButton = (Button) findViewById(R.id.demo_camera);
            cameraButton.setVisibility(View.VISIBLE);

            TextView idTV = (TextView) findViewById(R.id.demo_identification);
            idTV.setVisibility(View.INVISIBLE);

            TextView questionTV = (TextView) findViewById(R.id.demo_question);
            questionTV.setText("Questions over for this resource. You may take another picture.");

            Button answerButton = (Button) findViewById(R.id.demo_button);
            answerButton.setVisibility(View.INVISIBLE);

            Spinner spinner = (Spinner) findViewById(R.id.answers_spinner);
            spinner.setVisibility(View.INVISIBLE);

            EditText fillET = (EditText) findViewById(R.id.demo_fill_text);
            fillET.setVisibility(View.INVISIBLE);

            Button fillButton = (Button) findViewById(R.id.demo_button_filler);
            fillButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Saves the set of resource/question/answer
     *
     * @param answer provided answer
     */
    private void saveAnswer(String answer) {

        String toSave = currQuestion.toStringNoAnswers();
        toSave += answer;

        try {
            Funcs.write(this, "inspectionReport.txt", toSave, true);
        } catch (IOException e) {
            System.out.println("Data save fail");
        }
    }

    /**
     * <code>onClick</code> method for the <code>Back</code> button
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Funcs.startActivityFunc(this, MainActivity.class);
    }


}

/**
 * Stores a grouping of a resource name, a question about that resource, and possible answers to that question.
 */
class Question {

    String resource;
    String question;
    ArrayList<String> answers;

    public Question(String r, String q) {
        this.resource = r;
        this.question = q;
        this.answers = new ArrayList<String>();
    }

    public void addAnswer(String a) {
        this.answers.add(a);
    }

    @Override
    public String toString() {
        String ts = toStringNoAnswers();
        for (String answer : answers) {
            ts += (answer + " // ");
        }
        return ts;
    }

    public String toStringNoAnswers() {
        return resource + " :: " + question + " :: ";
    }

}
