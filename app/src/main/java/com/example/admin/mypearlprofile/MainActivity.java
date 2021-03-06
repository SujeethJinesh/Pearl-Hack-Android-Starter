package com.example.admin.mypearlprofile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button facebookButton;
    Button emailButton;
    EditText emailEditText;
    EditText postStatusText;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference summaryRef = database.getReference("summary");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facebookButton = (Button) findViewById(R.id.facebook_button);
        emailButton = (Button) findViewById(R.id.email_button);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        postStatusText = (EditText) findViewById(R.id.post_status_text);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://facebook.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sujeethjinesh@gmail.com"});
                intent.putExtra(Intent.EXTRA_TEXT, emailEditText.getText());

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 0);
                }
            }
        });
        summaryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                postStatusText.setText(value);
                Log.d("!!!", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("!!!", "Failed to read value.", error.toException());
            }
        });

    }
    public void writeDatabase(View view){
        EditText editText = (EditText) findViewById(R.id.post_status_text);
        summaryRef.setValue(editText.getText().toString());
    }

}
