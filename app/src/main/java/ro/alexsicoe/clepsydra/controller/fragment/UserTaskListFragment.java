package ro.alexsicoe.clepsydra.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.*;
import com.thoughtbot.expandablerecyclerview.ExpandableListUtils;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Task;
import ro.alexsicoe.clepsydra.model.User;
import ro.alexsicoe.clepsydra.view.recyclerView.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserTaskListFragment extends Fragment {
    private static final String TAG = UserTaskListFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private FirebaseFirestore db;

    private CollectionReference usersRef;
    private String userEmail;
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

//        mockUsers();
        return view;
    }

    private void addTask(User user) {
        Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
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
                    User.GroupItem item = new User.GroupItem(user);
                    int index = items.indexOf(item);
                    user.setTasks(new ArrayList<>()); // mock tasks


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
                }
                //TODO sort
                ExpandableListUtils.notifyGroupDataChanged(adapter);
            }
        });
    }

    private List<Task> queryUserTasks() {
        return null;
        //TODO
    }


    public void mockUsers() {
        for (int k = 0; k < 5; k++) {
            List<Task> tasks = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                tasks.add(new Task.Builder("000", "Task" + k, null).isComplete().build());
            }
            User user = new User("MockUser" + k,
                    "user" + k + "@gmail.com",
                    "Developer", String.valueOf(k), tasks);
            User.GroupItem userGroupItem = new User.GroupItem(user);
            items.add(userGroupItem);
        }
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
            userEmail = googleSignInAccount.getEmail();
        }
    }

    public String getProjectId() {
        return projectId;
    }


    @FunctionalInterface
    public interface AddTaskListener {
        void onAddTask(User user);
    }


}
