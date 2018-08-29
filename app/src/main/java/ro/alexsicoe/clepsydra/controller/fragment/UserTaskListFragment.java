package ro.alexsicoe.clepsydra.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Task;
import ro.alexsicoe.clepsydra.model.User;
import ro.alexsicoe.clepsydra.view.recyclerView.adapter.UserAdapter;

public class UserTaskListFragment extends Fragment {
    private static final String TAG = UserTaskListFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private FirebaseFirestore rootRef;
    private CollectionReference userProjectRef;
    private String userEmail;
    private UserAdapter adapter;


    public static UserTaskListFragment newInstance() {
        UserTaskListFragment fragment = new UserTaskListFragment();
        Bundle args = new Bundle();
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
        rootRef = FirebaseFirestore.getInstance();
        userProjectRef = rootRef.collection("projects").document(userEmail).collection("userProjects");

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
                tasks.add(new Task("Task" + k, false, null, null));
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
        //TODO?
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
    public void onClickFab() {
        //TODO
    }

    private void getAccountDetails(Context context) {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);
        if (googleSignInAccount != null) {
            userEmail = googleSignInAccount.getEmail();
        }
    }
}
