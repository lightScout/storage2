package com.brithbroadcast.storage_internal_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private String fileName = "MyPage.html";


      //Binding using ButterKnife

    @BindView(R.id.main_textview)
    public WebView mainWebView;

    @BindView(R.id.main_edittext)
    public EditText mainEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        // Initially read from file
//        try {
//            readFromInternal();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
    @OnClick(R.id.main_button)
    public void onClick(View view){

        try {
            saveToInternalStorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Writing to internal storage
    private void saveToInternalStorage() throws IOException {

        FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
        // Data from user
        String value = mainEditText.getText().toString().trim();
        // UI clean up
        mainEditText.setText("");
        // Write to file using bites
        outputStream.write((value + "\n").getBytes());
        // Close stream to prevent memory leek
        outputStream.close();

        // Reading after writing
        writeWebView();


    }

    // Reading from internal storage
    private void readFromInternal() throws IOException {
        // Retrieving file name
        FileReader fileReader = new FileReader(getFilesDir()+"/"+fileName);
        // Buffer to read the file
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder stringBuilder = new StringBuilder();
        String input = null;

        while((input = bufferedReader.readLine()) != null){
            stringBuilder.append(input).append("\n");
        }

//        mainWebView.setText(stringBuilder.toString());

    }

    private void writeWebView(){

        mainWebView.loadUrl("file://"+getFilesDir()+"/"+fileName);
    }

}