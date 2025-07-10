package com.example.todolist;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ListFragment extends Fragment {
    TextView textView;
    Button button;
    String itemText;
    public ListFragment(Editable itemText){
        this.itemText = String.valueOf(itemText);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_frag,container,false);
        textView = view.findViewById(R.id.item);
        button = view.findViewById(R.id.del);
        textView.setText(itemText);
        button.setOnClickListener(v -> {
            removeFragment();
        });

        return view;
    }
    private void removeFragment() {
        // Check if the fragment is attached to the activity
        if (isAdded()) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(this); // Remove this fragment
            fragmentTransaction.commit();
        }
    }
}
