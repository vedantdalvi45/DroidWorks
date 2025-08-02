package com.example.recyclerview.adapter;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.data.NewUser;
import com.example.recyclerview.listeners.CheckBoxCheckedListener;
import com.example.recyclerview.listeners.RemoveButtonListener;

import java.util.List;

public class RecyclerUserAdapter extends RecyclerView.Adapter<RecyclerUserAdapter.UserVewHolder> {
    private List<NewUser> userList;

    private Context context;

    private RemoveButtonListener removeButtonListenerCallback;

    private CheckBoxCheckedListener checkBoxCheckedListenerCallback;

    public RecyclerUserAdapter(List<NewUser> userList, Context context,RemoveButtonListener removeButtonListener) {
        this.userList = userList;
        this.context = context;
        this.removeButtonListenerCallback = removeButtonListener;
    }

    public RecyclerUserAdapter(List<NewUser> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public void onRemoveButtonClicked(RemoveButtonListener removeButtonListener){
        this.removeButtonListenerCallback = removeButtonListener;
    }

    public void onCheckBoxChecked(CheckBoxCheckedListener checkBoxCheckedListener){
        this.checkBoxCheckedListenerCallback = checkBoxCheckedListener;
    }

    class UserVewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView userName,userEmail,userAbout;
        Button removeBtn;
        public UserVewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.name_view);
            userEmail = itemView.findViewById(R.id.email_view);
            userAbout = itemView.findViewById(R.id.about_view);
            removeBtn = itemView.findViewById(R.id.remove_button);
            checkBox = itemView.findViewById(R.id.checkbox_view);
        }

        public void setData(NewUser newUser){
            userName.setText(newUser.getName());
            userEmail.setText(newUser.getEmail());
            userAbout.setText(newUser.getAbout());
            removeBtn.setOnClickListener(v->{
                removeButtonListenerCallback.onRemoveButtonClicked(itemView,getAdapterPosition());
            });
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                checkBoxCheckedListenerCallback.onItemChecked(itemView,b,getAdapterPosition());
            });
        }
    }

    @NonNull
    @Override
    public UserVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
        UserVewHolder userVewHolder = new UserVewHolder(view);

        return userVewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserVewHolder holder, int position) {

        holder.setData(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
