package bookstore.laidu.org.bookstore.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import bookstore.laidu.org.bookstore.R;
import bookstore.laidu.org.bookstore.uitl.HttpUtil;

/**
 * Created by laidu on 16-9-21.
 */

public class LoginActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "haha";
    private ProgressDialog progressDialog;
    private Handler mHandler = new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    if(progressDialog !=null)
                        progressDialog.dismiss();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    if(progressDialog != null)
                        progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "帐号密码错误", Toast.LENGTH_SHORT);
                    break;

            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login) ;
    }

    public void login(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, LoginActivity.class);


        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("正在加载中。。。");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override

            public void run() {

                EditText userLogin = (EditText) findViewById(R.id.username);
                EditText userPass = (EditText) findViewById(R.id.password);
                String username = userLogin.getText().toString();
                String password  = userPass.getText().toString();
                String url = "http://192.168.90.79:8080/Bookstore2/LoginServletN?username="+username+"&password="+password;
                Log.d("bookStore", url);

                try {
                    String re = HttpUtil.getJsonData(url);
                    JSONObject jo = new JSONObject(re);
                    if (jo.getInt("respone")==0){
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    public void register(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, RegisterActivity.class);
        EditText editText = (EditText) findViewById(R.id.username);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}