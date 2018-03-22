package store.photo.share.project.photoshare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.Snackbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by appu2 on 2018/03/15.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editPassword;
    private Button loginButton;
    private String emailString;
    private String passwordString;
    private ProgressDialog DialogLogin;
    private DatabaseReference databasereference;
    private OnCompleteListener<AuthResult> CreateAccountListener;
    private FirebaseAuth auth;
    private Intent UserIntent;
    private SubActivity subclass;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setTitle("サインアップ");
//        ===========================データベース設定================================
        databasereference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
//        ==========================================================================
//        ===================プログレスダイアログログイン==========================
        DialogLogin = new ProgressDialog(this);
        DialogLogin.setTitle("サインアップ");
        DialogLogin.setMessage("サインアップ処理中");
//        ==========================================================================
//====================サインアップ処理==============================================
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("ボタン","ボタンを押した");
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                emailString = editEmail.getText().toString();
                passwordString = editPassword.getText().toString();

                if(emailString.length() !=0 && passwordString.length() >=6){
//                    ダイアログを表示する
                    DialogLogin.show();
                    auth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(CreateAccountListener);
                    UserIntentset();
                }else{
                    Snackbar.make(v,"正しく入力してください",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
    private void UserIntentset(){
        UserIntent = new Intent(this,UserActivity.class);
        startActivity(UserIntent);
    }
}
