package ro.alexsicoe.clepsydra.controller.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Task;
import ro.alexsicoe.clepsydra.model.User;
import ro.alexsicoe.clepsydra.util.DateTimeObserver;
import ro.alexsicoe.clepsydra.util.DateUtil;
import ro.alexsicoe.clepsydra.view.recyclerView.adapter.UserAdapter;

public class UserTaskListFragment extends Fragment {
    private static final String TAG = UserTaskListFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private FirebaseFirestore db;
    private CollectionReference tasksRef;
    private String userEmail;
    private UserAdapter adapter;


    public static UserTaskListFragment newInstance(String projectId) {
        UserTaskListFragment fragment = new UserTaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable("projectId", projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_task_list, container, false);
        Context context = view.getContext();
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getAccountDetails(context);


        String projectId = (String) getArguments().getSerializable("projectId");
        db = FirebaseFirestore.getInstance();
        tasksRef = db.collection("Projects").document(projectId).collection("Tasks");

        List<User.Group> users = mockUsers();
        adapter = new UserAdapter(users, context);
        recyclerView.setAdapter(adapter);
        return view;
    }


    public List<User.Group> mockUsers() {
        List<User.Group> userGroupList = new ArrayList<>();
        for (int k = 0; k < 5; k++) {
            List<Task> tasks = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                tasks.add(new Task.Builder("000", "Task" + k, null).isComplete().build());
            }
            User user = new User("MockUser" + k,
                    "user" + k + "@gmail.com",
                    "Developer", String.valueOf(k), tasks);
            User.Group userGroup = new User.Group(user.getName(), user.getTasks());
            userGroupList.add(userGroup);
        }
        return userGroupList;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {    //in loc de onRestoreInstanceState
        super.onActivityCreated(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onClickFab(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.create_task);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText etTaskName = new EditText(getContext());
        etTaskName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        etTaskName.setHint(R.string.task_name);

        final EditText etStartDate = new EditText(getContext());
        final EditText etFinishDate = new EditText(getContext());
        etStartDate.setFocusable(false);
        etFinishDate.setFocusable(false);
        etStartDate.setHint(R.string.start_date);
        etFinishDate.setHint(R.string.finish_date);

        DateTimeObserver dateTimeObserver = new DateTimeObserver(getContext());
        etStartDate.setOnClickListener(dateTimeObserver);
        etFinishDate.setOnClickListener(dateTimeObserver);

        layout.addView(etTaskName);
        layout.addView(etStartDate);
        layout.addView(etFinishDate);
        builder.setView(layout);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String taskName = etTaskName.getText().toString();
                Date startDate = null;
                Date finishDate = null;
                DateFormat df = new DateUtil(getContext()).getDateTimeFormat();
                try {
                    startDate = df.parse(etStartDate.getText().toString());
                    finishDate = df.parse(etFinishDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                createTask(taskName, startDate, finishDate);
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void createTask(String name, Date startDate, Date finishDate) {
        final String id = tasksRef.document().getId();
        Task.Interval interval = new Task.Interval(startDate, finishDate);
        Task task = new Task.Builder(id, name, interval).build();
        tasksRef.document(id).set(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Task added");
                Toast.makeText(getContext(), R.string.success, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, e.toString());
            }
        });
    }

    private void getAccountDetails(Context context) {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);
        if (googleSignInAccount != null) {
            userEmail = googleSignInAccount.getEmail();
        }
    }
}
