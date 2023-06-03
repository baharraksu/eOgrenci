package com.bahar.eogrenci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TartismaActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText commentEditText;
    private LinearLayout commentsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tartisma);

        usernameEditText = findViewById(R.id.usernameEditText);
        commentEditText = findViewById(R.id.commentEditText);
        commentsLayout = findViewById(R.id.commentsLayout);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String comment = commentEditText.getText().toString();

                if (!username.isEmpty() && !comment.isEmpty()) {
                    addComment(username, comment);
                    usernameEditText.setText("");
                    commentEditText.setText("");
                }
            }
        });
    }
    private void addComment(String username, String comment) {
        TextView commentTextView = new TextView(this);
        commentTextView.setText(username + ": " + comment);

        commentsLayout.addView(commentTextView);
    }
}

