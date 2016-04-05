package com.example.m.bankapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogedActivity extends ListActivity {
    List<String> accounts= new ArrayList<>();
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged);


        /*Intent intent=getIntent();
        Bundle extra=intent.getExtras();
        String user = extra.getString("username");*/
        String url="http://192.168.0.3:8080/bankserver/users/";

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow,R.id.rowTextView, accounts);
        setListAdapter(listAdapter);
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        final String user = extras.getString("username");
        JsonArrayRequest req= new JsonArrayRequest(url+user,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                accounts.add(obj.getString("name") + "-" + obj.getString("amount"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        listAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
        Button button = (Button) findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLoginDialog(user);
                listAdapter.notifyDataSetChanged();
            }

        });
    }

    private void callLoginDialog(final String user)
    {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.addaccount);
        myDialog.setCancelable(false);
        Button createA = (Button) myDialog.findViewById(R.id.confirmAdd);

        final EditText accName = (EditText) myDialog.findViewById(R.id.accName);
        final EditText accAmount = (EditText) myDialog.findViewById(R.id.accAmount);
        myDialog.show();

        createA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long l = null;
                l = l.valueOf(accAmount.getText().toString());
                accounts.add(accName.getText().toString() + "-" + accAmount.getText().toString());
                postAccount(user, accName.getText().toString(), l);
                myDialog.hide();
            }
        });
    }

    public void postAccount(String user,String name,Long amount){
        JSONObject obj= new JSONObject();
        try {
            obj.put("name", name);
            obj.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest("http://192.168.0.3:8080/bankserver/users/"+user, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Account added", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loged, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        final String user = extras.getString("username");
        new AlertDialog.Builder(this)
                .setTitle("Options")
                .setMessage("" + getListView().getItemAtPosition(position))
                .setPositiveButton("deposit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String l=getListView().getItemAtPosition(position).toString();
                                String[] parts = l.split("-");
                                callDepositDialog(user,parts[0],parts[1]);
                            }
                        })
                .setNegativeButton("withdraw",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){
                                String l=getListView().getItemAtPosition(position).toString();
                                String[] parts = l.split("-");
                                callWithdrawDialog(user,parts[0],parts[1]);
                            }
                        })
                .show();
    }

    public void callDepositDialog(final String user, final String name,final String amount1){
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.deposit);
        myDialog.setCancelable(false);
        Button createA = (Button) myDialog.findViewById(R.id.deposit);

        final EditText accAmount = (EditText) myDialog.findViewById(R.id.depAmount);
        myDialog.show();

        createA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long l = null;
                l = l.valueOf(accAmount.getText().toString());
                Long k = null;
                k = k.valueOf(amount1);
                l = k + l;
                updateAccount(user, name, l);
                for (String acc : accounts) {
                    if (acc.equals(name + "-" + amount1)) {
                        acc = name + "-" + l;
                    }
                }
                listAdapter.notifyDataSetChanged();
                myDialog.hide();
            }
        });
    }

    public void callWithdrawDialog(final String user, final String name,final String amount1){
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.withdraw);
        myDialog.setCancelable(false);
        Button createA = (Button) myDialog.findViewById(R.id.withdrawA);

        final EditText accAmount = (EditText) myDialog.findViewById(R.id.wthAmount);
        myDialog.show();

        createA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long l = null;
                l = l.valueOf(accAmount.getText().toString());
                Long k=null;
                k = k.valueOf(amount1);
                l=k-l;
                updateAccount(user, name, l);
                for(String acc:accounts){
                    if(acc.equals(name+"-"+amount1)){
                        acc=name+"-"+l;
                    }
                }
                listAdapter.notifyDataSetChanged();
                myDialog.hide();
            }
        });
    }

    public void updateAccount(String user,String name,Long amount){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, "http://192.168.0.3:8080/bankserver/users/"+user, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Account updated", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(putRequest);

    }

}
