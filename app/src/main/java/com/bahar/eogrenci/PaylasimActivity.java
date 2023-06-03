package com.bahar.eogrenci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaylasimActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "PaylasimActivity";

    private Button downloadButton;
    private ProgressBar progressBar;
    private TextView statusTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paylasim);
        downloadButton = findViewById(R.id.downloadButton);
        progressBar = findViewById(R.id.progressBar);
        statusTextView = findViewById(R.id.statusTextView);
    }

    public void downloadPdf(View view) {
        // İzinleri kontrol et
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            return;
        }
        // İzin verilmişse PDF'yi indir
        startPdfDownload();
    }
    private void startPdfDownload() {
        String fileUrl = "https://pufpdf.com/";
        String fileName = "benim_dosyam.pdf";

        new PdfDownloaderTask().execute(fileUrl, fileName);
    }

    private class PdfDownloaderTask extends AsyncTask<String, Integer, Boolean> {
        private static final int BUFFER_SIZE = 4096;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            downloadButton.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            statusTextView.setVisibility(View.VISIBLE);
            statusTextView.setText("İndiriliyor...");
        }
        @Override
        protected Boolean doInBackground(String... urls) {
            String fileUrl = urls[0];
            String fileName = urls[1];
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.connect();

                int fileLength = connection.getContentLength();

                File file = new File(directory, fileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = connection.getInputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                long totalBytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;

                    // İlerleme yüzdesini hesapla ve güncelle
                    int progress = (int) (totalBytesRead * 100 / fileLength);
                    publishProgress(progress);
                }
                outputStream.close();
                inputStream.close();
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error downloading PDF: " + e.getMessage());
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            progressBar.setProgress(progress);
            statusTextView.setText("İndiriliyor... " + progress + "%");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            downloadButton.setEnabled(true);
            progressBar.setVisibility(View.GONE);
            statusTextView.setVisibility(View.GONE);

            if (result) {
                Toast.makeText(PaylasimActivity.this, "PDF indirme başarılı!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PaylasimActivity.this, "PDF indirme başarısız!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}