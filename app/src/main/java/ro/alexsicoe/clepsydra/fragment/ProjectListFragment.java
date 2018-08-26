package ro.alexsicoe.clepsydra.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.util.RequestType;

public class ProjectListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String STR_PROJECT = "project";
    private static final String STR_INDEX = "index";
    private static final String TAG = ProjectListFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    private String userEmail;
    private int columnCount = 1;
    private List<Project> projects;
    private Unbinder unbinder;
    private FirebaseFirestore rootRef;
    private CollectionReference userProjectRef;
    private FirestoreRecyclerAdapter<Project, ProjectViewHolder> firestoreAdapter;


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
        userProjectRef = rootRef.collection("projects").document(userEmail).collection("userProjects");

        Query query = userProjectRef.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Project> options = new FirestoreRecyclerOptions.Builder<Project>()
                .setQuery(query, Project.class)
                .build();

        firestoreAdapter = new FirestoreRecyclerAdapter<Project, ProjectViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProjectViewHolder holder, int position, @NonNull Project model) {
                holder.setModel(context, userEmail, model);
            }

            @NonNull
            @Override
            public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_project_list_item, parent, false);
                return new ProjectViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };
        recyclerView.setAdapter(firestoreAdapter);


//        readProjects();
        initItemTouchHelper();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firestoreAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firestoreAdapter != null) {
            firestoreAdapter.stopListening();
        }
    }

    private void getAccountDetails(Context context) {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);
        if (googleSignInAccount != null) {
            userEmail = googleSignInAccount.getEmail();
        }
    }

    @Deprecated
    private void readProjects() {
        //TODO user is part of project
        projects = new ArrayList<>();
        userProjectRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Project project = document.toObject(Project.class);
                                projects.add(project);
                                setProjectRecyclerViewAdapter();
                            }
                            //
                        } else {
                            Log.d(TAG, "Error getting document: ", task.getException());
                            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Deprecated
    private void setProjectRecyclerViewAdapter() {
        recyclerView.setAdapter(new ProjectRecyclerViewAdapter(projects, new OnItemClickListener() {
                    @Override
                    public void onClickItem(Project project, int index) {
                        //TODO start ProjectActivity
                        Toast.makeText(getContext(), project.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        );
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
        //etProjectName.setHintTextColor(Color.GRAY);
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
        //Daca nu precizez un id pt document, este generat unul
        String projectId = userProjectRef.document().getId();
        Project project = new Project(projectId, projectName, userEmail);
        userProjectRef.document(projectId).set(project).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Project successfully created!");
                Toast.makeText(getContext(), R.string.project_successfully_created, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestType.ADD.getCode()) {
                Project project = (Project) data.getSerializableExtra(STR_PROJECT);

                projects.add(project);
                //TODO insert into DB
                recyclerView.getAdapter().notifyItemInserted(projects.size() - 1);
                Toast.makeText(getContext(), "Project added successfully!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (requestCode == RequestType.EDIT.getCode()) {
                Project project = (Project) data.getSerializableExtra(STR_PROJECT);
                int index = data.getIntExtra(STR_INDEX, projects.size() - 1);
                projects.set(index, project);
                //TODO update DB
                recyclerView.getAdapter().notifyItemChanged(index);
                Toast.makeText(getContext(), "Project modified!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                throw new Exception("Request code " + requestCode + " not implemented!");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                Project project = projects.remove(position);
                //TODO delete from rootRef
                recyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public interface OnItemClickListener {
        void onClickItem(Project project, int index);
    }
}
