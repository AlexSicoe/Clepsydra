package ro.alexsicoe.clepsydra.view.recyclerView.expandable;

import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.User;

public class UserViewHolder extends GroupViewHolder {
    @BindView(R.id.tvUserName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;

    private FirebaseFirestore db;
    private User user;

    private OnAddTaskCallback callback;

    public UserViewHolder(View view, OnAddTaskCallback callback) {
        super(view);
        ButterKnife.bind(this, view);

        db = FirebaseFirestore.getInstance();
        this.callback = callback;
    }

    public void setViewHolder(User.GroupItem group) {
        user = group.getUser();
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
    }

    @OnClick(R.id.btnAddTask)
    public void onClickBtnAddTask(View v) {
        //Sends user to fragment
        callback.onAddTask(user);
    }

}
