package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {
    Button displayInformation;
    Button navigateToSecondaryActivity;
    EditText name;
    EditText group;
    TextView displayInfo;

    CheckBox checkBoxName;
    CheckBox checkBoxGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        displayInformation = findViewById(R.id.displayInformation);
        navigateToSecondaryActivity = findViewById(R.id.navigateToSecondaryActivity);
        name = findViewById(R.id.name);
        group = findViewById(R.id.group);
        displayInfo = findViewById(R.id.displayInfo);
        checkBoxName = findViewById(R.id.checkName);
        checkBoxGroup = findViewById(R.id.checkGroup);

        displayInformation.setOnClickListener(v -> {
            String nameText = "";
            String groupText = "";
            if (checkBoxName.isChecked()) {
                nameText = name.getText().toString();
                if (nameText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name is empty", Toast.LENGTH_LONG).show();
                }
            }
            if (checkBoxGroup.isChecked()) {
                groupText = group.getText().toString();
                if (groupText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Group is empty", Toast.LENGTH_LONG).show();
                }
            }
            displayInfo.setText(nameText + " " + groupText);

            if (checkBoxGroup.isChecked() && checkBoxName.isChecked()) {
                if (!name.getText().toString().isEmpty() && !group.getText().toString().isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("group", group.getText().toString());
                    getApplicationContext().startService(intent);
                }
            }
        });

        navigateToSecondaryActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("group", group.getText().toString());
            startActivityForResult(intent, 1);
        });
    }

    private final BroadcastReceiver messageBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), intent.getStringExtra("name") + " -- " + intent.getStringExtra("group"), Toast.LENGTH_LONG).show();
            Log.d("receivedBroadcast", "Bcast was received: name " + name.getText() + "" + " group " + group.getText());
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.broadcast");
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "CANCEL", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("name", name.getText().toString());
        savedInstanceState.putString("group", group.getText().toString());
        savedInstanceState.putString("displayInfo", displayInfo.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("name")) {
            name.setText(savedInstanceState.getString("name"));
        }
        if (savedInstanceState.containsKey("group")) {
            group.setText(savedInstanceState.getString("group"));
        }
        if (savedInstanceState.containsKey("displayInfo")) {
            displayInfo.setText(savedInstanceState.getString("displayInfo"));
        }
    }
}