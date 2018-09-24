package ro.alexsicoe.clepsydra.view.recyclerView.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.controller.activity.ProjectActivity;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.util.DateUtil;

public class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<ProjectRecyclerViewAdapter.ViewHolder> {
    private final List<Project> items;
    private Context context;

    public ProjectRecyclerViewAdapter(Context context, List<Project> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Project model = items.get(position);
        holder.setModel(model);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvProjectName)
        TextView tvProjectName;
        @BindView(R.id.tvCreatedBy)
        TextView tvCreatedBy;
        @BindView(R.id.tvCreatedAt)
        TextView tvCreatedAt;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        public void setModel(final Project model) {
            final String id = model.getId();
            final String name = model.getName();
            String createdBy = context.getString(R.string.created_by) + model.getCreatedBy();
            Date createdAt = model.getCreatedAt();
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference docRef = db.
                    collection("Projects").document(id);


            tvProjectName.setText(name);
            tvCreatedBy.setText(createdBy);
            DateFormat df = new DateUtil(context).getDateFormat(DateFormat.MEDIUM);

            tvCreatedAt.setText(df.format(createdAt));


            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.putExtra("Project", model);
                v.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.edit_project_name);

                final EditText et = new EditText(context);
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                et.setText(name);
                et.setSelection(et.getText().length());
                et.setHint(R.string.project_name);
                builder.setView(et);

                builder.setPositiveButton(R.string.update, (dialog, which) -> {
                    String newProjectName = et.getText().toString().trim();
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", newProjectName);
                    docRef.update(map);
                }).setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            });
        }
    }
}