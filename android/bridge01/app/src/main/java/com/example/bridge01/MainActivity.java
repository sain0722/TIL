package com.example.bridge01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Button btn_login;
    Button btn_signup;
    EditText et_text;
    CheckBox chk_1;
    CheckBox chk_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        et_text = (EditText)findViewById(R.id.et_text);
        et_text.setText("텍스트 설정");
        String str_view = et_text.getText().toString();
        et_text.addTextChangedListener(this);

        chk_1 = (CheckBox)findViewById(R.id.chk_1);
        chk_2 = (CheckBox)findViewById(R.id.chk_2);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            Toast.makeText(getApplicationContext(), "로그인 버튼을 클릭하였습니다.", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.btn_signup) {
            Toast.makeText(getApplicationContext(), "회원가입 버튼을 클릭하였습니다.", Toast.LENGTH_LONG).show();
        }

        if (v.getId() == R.id.chk_1) {
            if(chk_1.isChecked()) {
                chk_2.setChecked(false);
            }
            else {
                chk_2.setChecked(true);
            }
        }

        if (v.getId() == R.id.chk_2) {
            if(chk_2.isChecked()) {
                chk_1.setChecked(false);
            }
            else {
                chk_1.setChecked(true);
            }
        }

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("EditText", "beforeTextChanged");
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("EditText", "onTextChanged");
        char char_last = charSequence.charAt(charSequence.length() - 1);
        if(char_last == 'x') {
            Toast.makeText(this, "x가 입력되었습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {
        Log.d("EditText", "afterTextChanged");

    }
}

