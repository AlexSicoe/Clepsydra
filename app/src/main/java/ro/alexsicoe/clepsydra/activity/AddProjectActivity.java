package ro.alexsicoe.clepsydra.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.util.DateTimeUtil;
import ro.alexsicoe.clepsydra.model.Project;

@Deprecated
public class AddProjectActivity extends AppCompatActivity {

    private static final String TAG = AddProjectActivity.class.getSimpleName();
    @BindView(R.id.etName)
    EditText etName;
    //TODO replace with calendar and clock selection
    @BindView(R.id.etStartDate)
    EditText etStartDate;
    private CollectionReference projectsCollectionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        ButterKnife.bind(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        projectsCollectionRef = db.collection("projects");
    }

    @OnClick(R.id.fab)
    public void onClickFab(View v) {
        //TODO add validations
        String name = etName.getText().toString();
        Date startDate = null;
        try {
            startDate =
                    DateTimeUtil.getFormat().parse(
                            etStartDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert startDate != null;
        //TODO change johnDoe to logged user id
//        Project project = new Project(name, "JohnDoe", startDate);
//        addToDB(project);
        finish();
    }

    private void addToDB(Project project) {
        projectsCollectionRef.add(project)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(AddProjectActivity.this, R.string.project_successfully_created, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(AddProjectActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
