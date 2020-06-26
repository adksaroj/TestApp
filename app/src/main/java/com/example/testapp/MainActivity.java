package com.example.testapp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtEmailID;

    private Button btnGetUser;
    private EditText tbUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextViewResult = findViewById(R.id.textView);

        tbUserID = findViewById(R.id._tb_user_ID);
        btnGetUser = findViewById(R.id._btn_getUser);

        txtFirstName = findViewById(R.id.txt_FirstName);
        txtLastName = findViewById(R.id.txt_lastName);
        txtEmailID = findViewById(R.id.txt_emailID);


    }


    public void onGetUserClick(View view) {
        if(tbUserID.getText()!=null){
            getUpdateUser(Integer.parseInt(tbUserID.getText().toString()));
        }
    }

    protected void getUpdateUser(int userID){

        OkHttpClient client = new OkHttpClient();

        String url = "https://reqres.in/api/users/" + Integer.toString(userID) ;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String _usrFn;
                    final String _usrLn;
                    String _usrEm;

                    final String myResponse = response.body().string();

                    final User _usr = new User();
                    JSONObject _usrObj = null;
                    try {
                        _usrObj = new JSONObject(myResponse);
                        JSONObject _usrData = _usrObj.getJSONObject("data");

                        _usr.firstName = _usrData.getString("first_name");
                        _usr.lastName = _usrData.getString("last_name");
                        _usr.email = _usrData.getString("email");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            txtFirstName.setText((_usr.firstName));
                            txtLastName.setText((_usr.lastName));
                            txtEmailID.setText((_usr.email));

                        }
                    });
                }
            }
        });

    }
}