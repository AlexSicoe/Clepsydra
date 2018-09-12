package ro.alexsicoe.clepsydra.view.recyclerView.viewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.controller.activity.ProjectActivity;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.util.DateUtil;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvProjectName)
    TextView tvProjectName;
    @BindView(R.id.tvCreatedBy)
    TextView tvCreatedBy;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;

    public ProjectViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }


    public void setModel(final Context context, final String userEmail, final Project model) {
        final String id = model.getId();

        final String name = model.getName();
        tvProjectName.setText(name);
        String createdBy = context.getString(R.string.created_by) + model.getCreatedBy();
        tvCreatedBy.setText(createdBy);
        Date startDate = model.getStart();
        DateFormat df = new DateUtil(context).getDateFormat(DateFormat.MEDIUM);
        tvStartDate.setText(df.format(startDate));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.putExtra("Project", model);
                v.getContext().startActivity(intent);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.edit_project_name);

                final EditText et = new EditText(context);
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                et.setText(name);
                et.setSelection(et.getText().length());
                et.setHint(R.string.project_name);
                builder.setView(et);

                final FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                .setTimestampsInSnapshotsEnabled(true)
                                .build();
                        rootRef.setFirestoreSettings(settings);

                final Map<String, Object> map = new HashMap<>();

                builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newProjectName = et.getText().toString().trim();
                        map.put("name", newProjectName);
                        rootRef
                                .collection("projects").document(userEmail)
                                .collection("userProjects").document(id)
                                .update(map);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

        //TODO swipe delete
    }


}

