package sci.dashboard.dashboardhelp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Context con;
    private EditText msg;
    String user = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = this;

        msg = (EditText)findViewById(R.id.remark_id);

    }

    public void calling(String call){
        Intent callIntent1 = new Intent(Intent.ACTION_CALL);
        callIntent1.setData(Uri.parse("tel:" + call));
        if(AlertMessage.checkPermission(con))
            con.startActivity(callIntent1);
    }

    public void ambulance(View v) {
        calling("111");
    }
    public void police(View v) {
        calling("777");
    }
    public void others(View v) {

        calling("888");
    }
    public void fire(View v) {
        calling("999");
    }
    public void message(View v) {

        user = msg.getText().toString();

        if (user.equalsIgnoreCase("")) {
            AlertMessage.showMessage(MainActivity.this, "Please provide message.",
                    "");

        }
        else if(!AlertMessage.isNetConnected(con)) {
            AlertMessage.showMessage(MainActivity.this, "Sorry!!!",
                    "Network is not connected.");

        }
        else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Sending Message.Please wait...");
            progressDialog.show();


            String URL = "http://vmsservice.scibd.info/vmsservice.asmx/SetMessage?";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d(".response--.", ".." + response.toString());
                            msg.setText("");
                            progressDialog.dismiss();
                            Toast.makeText(con,"Message Sent Successfully.",Toast.LENGTH_LONG).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<String, String>();

                    data.put("message", user);
                    Log.d(".response--.", ".." + data);
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

}
