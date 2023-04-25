package com.bahar.eogrenci;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bahar.eogrenci.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    EditText edUsername, edEmail, edPassword, edConfirm;
    Button registerBtn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextRegUserName);
        edEmail = findViewById(R.id.editTextRegEmail);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfirm = findViewById(R.id.editTextRegConfirmPassword);
        registerBtn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String confirm = edConfirm.getText().toString();
                Database db = new Database(getApplicationContext(),"eogrenci",null,1);
                if (username.length() == 0 || email.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Lütfen boş alan bırakmayınız!", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(confirm) == 0) {
                        if (isValid(password)) {
                            db.register(username,email,password);
                            Toast.makeText(getApplicationContext(), "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "şifre en az bir karakter, harf, rakam ve özel sembol içermelidir", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Parolalar eşleşmemektedir!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public static boolean isValid(String passwordCheck) {
        int kontrol1 = 0, kontrol2 = 0, kontrol3 = 0;
        if (passwordCheck.length() < 8) {
            return false;
        } else {
            for (int i = 0; i < passwordCheck.length(); i++) {
                if (Character.isLetter(passwordCheck.charAt(i))) {
                    kontrol1 = 1;
                }
            }
            for (int j = 0; j < passwordCheck.length(); j++) {
                if (Character.isDigit(passwordCheck.charAt(j))) {
                    kontrol2 = 1;
                }
            }
            for (int k = 0; k < passwordCheck.length(); k++) {
                char c = passwordCheck.charAt(k);
                if (c >= 33 && c <= 46 || c == 64) {
                    kontrol3 = 1;
                }
            }
            if (kontrol1 == 1 && kontrol2 == 1 && kontrol3 == 1)
                return true;
            return false;
        }
    }
}

