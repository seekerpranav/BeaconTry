package com.example.prana.beacontry;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;

public class MainActivity extends AppCompatActivity {

    MessageListener mMessageListener;
    Message mMessage;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (!havePermissions()) {
            Toast.makeText(MainActivity.this,"Requesting permissions needed for this app.",Toast.LENGTH_LONG).show();
            Log.i(TAG, "Requesting permissions needed for this app.");
            requestPermissions();
        }
        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                Toast.makeText(MainActivity.this,"on found",Toast.LENGTH_LONG).show();
               // Toast.makeText(MainActivity.this,"",Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this,new String(message.getContent()),Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this,message.getNamespace() +
                        "/" + message.getType(),Toast.LENGTH_LONG).show();
                Log.i(TAG, "Message found: " + message);
                Log.i(TAG, "Message string: " + new String(message.getContent()));
                Log.i(TAG, "Message namespaced type: " + message.getNamespace() +
                        "/" + message.getType());
            }

            @Override
            public void onLost(Message message) {
                Toast.makeText(MainActivity.this,"on lost",Toast.LENGTH_LONG).show();
                Log.i(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };

        subscribe();

        //mMessage = new Message("Hello World".getBytes());

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMessagesClient = Nearby.getMessagesClient(this, new MessagesOptions.Builder()
                    .setPermissions(NearbyPermissions.BLE)
                    .build());
        }*/


    }
    private void subscribe() {
        Toast.makeText(MainActivity.this,"subscribing",Toast.LENGTH_LONG).show();
        Log.i(TAG, "Subscribing.");
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .build();
        Nearby.getMessagesClient(this).subscribe(mMessageListener, options);
    }

    @Override
    public void onStart() {
        super.onStart();

        /*Nearby.getMessagesClient(this).publish(mMessage);
        Nearby.getMessagesClient(this).subscribe(mMessageListener);*/
    }

    @Override
    public void onStop() {
      /* Nearby.getMessagesClient(this).unpublish(mMessage);
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener);*/

        super.onStop();
    }

    private void requestPermissions() {
        Toast.makeText(MainActivity.this,"request permisiions",Toast.LENGTH_LONG).show();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1111);
    }

    private boolean havePermissions() {
        Toast.makeText(MainActivity.this,"have permissions",Toast.LENGTH_LONG).show();
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }


}
