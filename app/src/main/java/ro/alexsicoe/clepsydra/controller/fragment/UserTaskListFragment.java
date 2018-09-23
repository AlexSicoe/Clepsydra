package ro.alexsicoe.clepsydra.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.thoughtbot.expandablerecyclerview.ExpandableListUtils;

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
import ro.alexsicoe.clepsydra.model.Taskk;
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

    private CollectionReference usersRef;
    private String loggedUserEmail;
    private UserAdapter adapter;
    private CollectionReference userProjectsRef;
    private CollectionReference tasksRef;
    private String projectId;
    private List<User.GroupItem> items;

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


        projectId = (String) getArguments().getSerializable("projectId");
        db = FirebaseFirestore.getInstance();
        tasksRef = db.collection("Projects").document(projectId).collection("Tasks");
        usersRef = db.collection("Users");
        userProjectsRef = db.collection("UserProjects");


        items = new ArrayList<>();
        adapter = new UserAdapter(items, getContext(), user -> addTask(user));
        recyclerView.setAdapter(adapter);
        readUsers();
//        readMockUsers();

        return view;
    }

    public void readMockUsers() {
        for (int k = 0; k < 5; k++) {
            List<Taskk> taskks = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Taskk.Interval interval = new Taskk.Interval(new Date(), new Date());
                taskks.add(new Taskk.Builder("000", "Taskk" + k, "fake@mail.com", interval).isComplete().build());
            }
            User user = new User("MockUser" + k,
                    "user" + k + "@gmail.com",
                    "Developer", String.valueOf(k), taskks);
            User.GroupItem userGroupItem = new User.GroupItem(user);
            items.add(userGroupItem);
        }
        ExpandableListUtils.notifyGroupDataChanged(adapter);
    }


    private void readUsers() {
        Query query = userProjectsRef
                .whereEqualTo("projectId", projectId);
        query.addSnapshotListener((snapshots, e) -> {
            items.clear();

            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
            assert snapshots != null;
            for (QueryDocumentSnapshot doc : snapshots) {
                if (doc.get("email") != null) {
                    String email = doc.getString("email");
                    queryUsers(email);
                }
            }
        });
    }

    @SuppressWarnings("Convert2MethodRef")
    private void queryUsers(String email) {
        Query query = usersRef
                .whereEqualTo("email", email);
        query.addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                Log.w(TAG, "listen:error", e);
                return;
            }

            if (snapshots != null) {
                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    User user = dc.getDocument().toObject(User.class);
                    queryUserTasks(user, tasks -> {
                        user.setTaskks(tasks);
                        User.GroupItem item = new User.GroupItem(user);
                        int index = items.indexOf(item);

                        switch (dc.getType()) {
                            case ADDED:
                                items.add(item);
                                Log.d(TAG, "ADDED: " + user.toString());
//                            adapter.notifyItemInserted(items.size() - 1);
                                break;
                            case MODIFIED:
                                Log.d(TAG, "MODIFIED: " + user.toString());
                                items.set(index, item);
//                            adapter.notifyItemChanged(index);
                                break;
                            case REMOVED:
                                Log.d(TAG, "REMOVED: " + user.toString());
                                items.remove(item);
//                            adapter.notifyItemRemoved(index);
                                break;
                        }
                        ExpandableListUtils.notifyGroupDataChanged(adapter);
                    });

                }
                //TODO sort
            }
        });


    }

    private void queryUserTasks(User user, OnReadTasksCallback callback) {
        List<Taskk> taskks = new ArrayList<>();
        tasksRef.whereEqualTo("ownerEmail", user.getEmail())
                .orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "listen:error", e);
                        return;
                    }

                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        Taskk taskk = dc.getDocument().toObject(Taskk.class);
                        int index = items.indexOf(taskk);

                        switch (dc.getType()) {
                            case ADDED:
                                Log.d(TAG, "ADDED: " + taskk.toString());
                                taskks.add(taskk);
                                break;
                            case MODIFIED:
                                Log.d(TAG, "MODIFIED: " + taskk.toString());
                                taskks.set(index, taskk);
                                break;
                            case REMOVED:
                                Log.d(TAG, "REMOVED: " + taskk.toString());
                                taskks.remove(taskk);
                                break;
                        }
                    }
                    ExpandableListUtils.notifyGroupDataChanged(adapter); // TODO ?
                    callback.onReadTasks(taskks);
                });
    }

    private void addTask(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserTaskListFragment.this.getContext());
        builder.setTitle(R.string.create_task);

        LinearLayout layout = new LinearLayout(UserTaskListFragment.this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText etTaskName = new EditText(UserTaskListFragment.this.getContext());
        etTaskName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        etTaskName.setHint(R.string.task_name);

        final EditText etStartDate = new EditText(UserTaskListFragment.this.getContext());
        final EditText etFinishDate = new EditText(UserTaskListFragment.this.getContext());
        etStartDate.setFocusable(false);
        etFinishDate.setFocusable(false);
        etStartDate.setHint(R.string.start_date);
        etFinishDate.setHint(R.string.finish_date);

        DateTimeObserver dateTimeObserver = new DateTimeObserver(UserTaskListFragment.this.getContext());
        etStartDate.setOnClickListener(dateTimeObserver);
        etFinishDate.setOnClickListener(dateTimeObserver);

        layout.addView(etTaskName);
        layout.addView(etStartDate);
        layout.addView(etFinishDate);
        builder.setView(layout);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            String taskName = etTaskName.getText().toString();
            Date startDate = null;
            Date finishDate = null;
            DateFormat df = new DateUtil(UserTaskListFragment.this.getContext()).getDateTimeFormat();
            try {
                startDate = df.parse(etStartDate.getText().toString());
                finishDate = df.parse(etFinishDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String id = tasksRef.document().getId();
            Taskk.Interval interval = new Taskk.Interval(startDate, finishDate);
            Taskk taskk = new Taskk.Builder(id, taskName, user.getEmail(), interval).build();

            tasksRef.document(id).set(taskk).addOnSuccessListener(aVoid -> {
                Log.d(TAG, "Taskk added");
                Toast.makeText(UserTaskListFragment.this.getContext(), R.string.success, Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> Log.w(TAG, e.toString()));


        }).setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onClickFab(View v) {

    }

    private void getAccountDetails(Context context) {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);
        if (googleSignInAccount != null) {
            loggedUserEmail = googleSignInAccount.getEmail();
        }
    }


    @FunctionalInterface
    public interface OnReadTasksCallback {
        void onReadTasks(List<Taskk> taskks);
    }

    @FunctionalInterface
    public interface OnAddTaskCallback {
        void onAddTask(User user);
    }


}
