package m.covidstat;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatButton submitBtn;
    private AppCompatEditText name, phone,location;
    private TextInputLayout describeText;
    private DatabaseReference rootRef,demoRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        toolbar = findViewById(R.id.toolbar);
        submitBtn = findViewById(R.id.submit_btn);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        location = findViewById(R.id.location);
        describeText = findViewById(R.id.describe_text);
        rootRef = FirebaseDatabase.getInstance().getReference();


        toolbar.setNavigationOnClickListener(v -> finish());
        submitBtn.setOnClickListener(v -> {
            String name = FormActivity.this.name.getText().toString();
            String phone = FormActivity.this.phone.getText().toString();
            String location = FormActivity.this.location.getText().toString();
            String description = FormActivity.this.describeText.getEditText().getText().toString();
            Volunteer volunteer = new Volunteer(name,phone,description,location);
            demoRef = rootRef.getRef().child("volunteer");
            demoRef.setValue(volunteer);
            Toast.makeText(getApplicationContext(), "Submitted Succesfully", Toast.LENGTH_LONG).show();

        });

    }
}
