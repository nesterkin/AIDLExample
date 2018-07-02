package com.github.nesterkin.aidlexample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView mTextView;
    private Button mWriteButton;
    private Button mReadButton;

    private IDataInterface mDataInterface;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDataInterface = IDataInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDataInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text);
        mTextView = findViewById(R.id.text_view);
        mWriteButton = findViewById(R.id.write_button);
        mReadButton = findViewById(R.id.read_button);

        mWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
            }
        });

        mReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, SharedPrefService.class);
        intent.setAction(IDataInterface.class.getName());
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
    }

    private void writeData() {
        try {
            mDataInterface.setData(mEditText.getText().toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void readData() {
        try {
            mTextView.setText(mDataInterface.getData());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}