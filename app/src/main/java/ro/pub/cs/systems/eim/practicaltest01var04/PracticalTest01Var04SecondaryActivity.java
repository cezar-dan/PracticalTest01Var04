package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {
    Button ok;
    Button cancel;
    TextView name;
    TextView group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        ok = findViewById(R.id.ok);
        cancel = findViewById(R.id.cancel);
        name = findViewById(R.id.name);
        group = findViewById(R.id.group);

        String n = getIntent().getStringExtra("name");
        String g = getIntent().getStringExtra("group");

        name.setText(n);
        group.setText(g);

        ok.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        cancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}