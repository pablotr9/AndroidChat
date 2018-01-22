package com.example.pablo.prueba2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class chat extends Activity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdapter mAdapter;
    private TCPClient mClient;
    private int inicio=0;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        arrayList = new ArrayList<String>();

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button)findViewById(R.id.send_button);

        //relate the listView from java to the one created in xml
        mList = (ListView)findViewById(R.id.list);
        mAdapter = new MyCustomAdapter(this, arrayList);
        mList.setAdapter(mAdapter);

        // connect to the server
        new connectTask().execute("");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString();

                //add the text in the arrayList
                //arrayList.add("c: " + message);

                //sends the message to the server
                if (mClient != null) {
                    FileOutputStream outputStream;

                    mClient.sendMessage(message);
                }

                //refresh the list
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

    }

    public class connectTask extends AsyncTask<String,String,TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            //we create a Client object and
            mClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mClient.run();

            return null;
        }



        public String cifrar(String texto){
            String charset1 = "1234567890!#$%&/()=¿?¡_-+*:";
            String charset2 = "abcdefghijklmnopqrstuvwxyz ";
            texto = texto.toLowerCase();
            for (int i = 0; i < charset2.length(); i++) {
                texto = texto.replace(charset2.charAt(i), charset1.charAt(i));
            }
            return texto;
        }


        public String descifrar(String texto){
            String charset1 = "1234567890!#$%&/()=¿?¡_-+*:";
            String charset2 = "abcdefghijklmnopqrstuvwxyz ";
            texto = texto.toLowerCase();
            for (int i = 0; i < charset1.length(); i++) {
                texto = texto.replace(charset1.charAt(i), charset2.charAt(i));
            }
            return texto;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            arrayList.add(values[0]);
            Log.e("e",values[0]);
            Log.e("e",cifrar(values[0]));
            Log.e("e",values[0]);

            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput("tmpchat", Context.MODE_APPEND + Context.MODE_WORLD_READABLE + MODE_WORLD_WRITEABLE);
                outputStream.write( (cifrar(values[0]) + "\n").getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }



            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();
        }
    }

}