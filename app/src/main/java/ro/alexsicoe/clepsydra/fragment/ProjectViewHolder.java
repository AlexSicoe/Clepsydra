package ro.alexsicoe.clepsydra.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.activity.ProjectActivity;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.util.DateUtil;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvProjectName)
    TextView tvProjectName;
    @BindView(R.id.tvCreatedBy)
    TextView tvCreatedBy;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;

    ProjectViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }


    public void setModel(final Context context, String userEmail, final Project model) {
        String id = model.getId();
        String name = model.getName();
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
                //TODO edit menu_project
                Toast.makeText(context, model.getId(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //TODO swipe delete
    }



}

