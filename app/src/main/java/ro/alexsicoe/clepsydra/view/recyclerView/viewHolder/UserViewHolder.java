package ro.alexsicoe.clepsydra.view.recyclerView.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;

public class UserViewHolder extends GroupViewHolder {
    @BindView(R.id.tvUserName)
    TextView tvUserName;

    public UserViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setTitle(ExpandableGroup group) {
        tvUserName.setText(group.getTitle());
    }
}
