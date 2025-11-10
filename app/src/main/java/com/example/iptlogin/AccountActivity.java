package com.example.iptlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button buttonLogout;
    private TextView textViewUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();

        buttonLogout = findViewById(R.id.buttonLogout);
        textViewUserInfo = findViewById(R.id.textViewUserInfo);

        updateUserInfo();

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(AccountActivity.this, getString(R.string.logged_out_successfully), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private void updateUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String uid = user.getUid();
            textViewUserInfo.setText(getString(R.string.user_info_format, email, uid));
        } else {
            textViewUserInfo.setText(getString(R.string.user_data_unavailable));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}