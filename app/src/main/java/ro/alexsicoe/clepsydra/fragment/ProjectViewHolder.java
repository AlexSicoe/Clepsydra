package ro.alexsicoe.clepsydra.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
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


    public void setModel(Context context, String userEmail, Project model) {
        String id = model.getId();
        String name = model.getName();
        tvProjectName.setText(name);
        String createdBy = context.getString(R.string.created_by) + model.getCreatedBy();
        tvCreatedBy.setText(createdBy);
        Date startDate = model.getStart();
        DateFormat df = new DateUtil(context).getDateFormat(DateFormat.MEDIUM);
        tvStartDate.setText(df.format(startDate));
    }



}

