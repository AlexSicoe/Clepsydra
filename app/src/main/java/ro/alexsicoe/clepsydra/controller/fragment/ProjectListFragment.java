package ro.alexsicoe.clepsydra.controller.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.view.recyclerView.adapter.ProjectRecyclerViewAdapter;

public class ProjectListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = ProjectListFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    private int columnCount = 1;
    private String userEmail;
    private Unbinder unbinder;
    private FirebaseFirestore rootRef;
    private CollectionReference usersRef;
    private CollectionReference projectsRef;
    private CollectionReference userProjectsRef;
    private List<Project> projects;
    private ProjectRecyclerViewAdapter adapter;


    public static ProjectListFragment newInstance(int columnCount) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        final Context context = view.getContext();
        unbinder = ButterKnife.bind(this, view);
        initLayoutManager(context);
        getAccountDetails(context);
        rootRef = FirebaseFirestore.getInstance();

        usersRef = rootRef.collection("Users");
        projectsRef = rootRef.collection("Projects");
        userProjectsRef = rootRef.collection("UserProjects");


        //TODO debug
        readUserProjects();
        adapter = new ProjectRecyclerViewAdapter(projects, getContext(), userEmail);
        recyclerView.setAdapter(adapter);

        initItemTouchHelper();
        return view;
    }

    private void readUserProjects() {
        projects = new ArrayList<>();
        final Query queryProjectIds = userProjectsRef
                .whereEqualTo("userEmail", userEmail);
        queryProjectIds.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                assert snapshots != null;
                for (QueryDocumentSnapshot doc : snapshots) {
                    if (doc.get("projectId") != null) {
                        String projectId = doc.getString("projectId");
                        Query queryProjects = projectsRef
                                .orderBy("name", Query.Direction.ASCENDING)
                                .whereEqualTo("id", projectId);
                        queryProjects.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                                assert snapshots != null;
                                if (e != null) {
                                    Log.w(TAG, "Listen failed.", e);
                                    return;
                                }
                                for (QueryDocumentSnapshot doc : snapshots) {
                                    Project project = doc.toObject(Project.class);
                                    projects.add(project);
                                    //TODO notify adapter
                                    recyclerView.getAdapter().notifyDataSetChanged();
                                    Log.d(TAG, "project: " + project.toString());
                                }
                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void getAccountDetails(Context context) {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);
        if (googleSignInAccount != null) {
            userEmail = googleSignInAccount.getEmail();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onClickFab() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.create_project);

        final EditText etProjectName = new EditText(getContext());
        etProjectName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        etProjectName.setHint(R.string.project_name);
        builder.setView(etProjectName);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String projectName = etProjectName.getText().toString().trim();

                createProject(projectName);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void createProject(String projectName) {
        final String projectId = projectsRef.document().getId();
        Project project = new Project(projectId, projectName, userEmail);
        projectsRef.document(projectId).set(project).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                createUserProject();
            }

            private void createUserProject() {
                Map<String, Object> map = new HashMap<>();
                map.put("userEmail", userEmail);
                map.put("projectId", projectId);
                userProjectsRef.add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Project successfully created!");
                        Toast.makeText(getContext(), R.string.project_successfully_created, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void initLayoutManager(Context context) {
        if (columnCount < 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
    }


    private void initItemTouchHelper() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                //TODO delete from rootRef
                recyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
