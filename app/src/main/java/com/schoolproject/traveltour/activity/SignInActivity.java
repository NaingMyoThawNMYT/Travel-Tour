package com.schoolproject.traveltour.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.UiUtil;

public class SignInActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SIGN_UP = 1324;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        final TextInputEditText edtEmail = findViewById(R.id.edt_email);
        final TextInputEditText edtPassword = findViewById(R.id.edt_password);
        Button btnSignIn = findViewById(R.id.btn_sign_in);
        TextView tvSignUp = findViewById(R.id.tv_sign_up);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = UiUtil.getString(edtEmail);
                final String password = UiUtil.getString(edtPassword);

                if (TextUtils.isEmpty(email)) {
                    edtEmail.requestFocus();
                    edtEmail.setError("Enter email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edtPassword.requestFocus();
                    edtPassword.setError("Enter password");
                    return;
                }

                DataSet.isAdmin = email.equals("admin@gmail.com");

                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    goToMainActivity();
                                } else {
                                    Toast.makeText(SignInActivity.this, "Sign in failed!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(SignInActivity.this, SignUpActivity.class),
                        REQUEST_CODE_SIGN_UP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                requestCode == REQUEST_CODE_SIGN_UP) {
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }
}
