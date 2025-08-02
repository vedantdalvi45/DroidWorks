package com.example.restaurantapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class FilterDialogFragment extends DialogFragment {

    private Button btnSelectCuisines, btnApplyFilter;
    private TextView tvSelectedCuisines;
    private EditText etMinPrice, etMaxPrice;
    private Slider sliderMinRating;

    private List<String> selectedCuisines = new ArrayList<>();

    public interface FilterDialogListener {
        void onFilterApplied(List<String> cuisines, int minPrice, int maxPrice, float minRating);
    }

    private FilterDialogListener listener;

    public void setFilterDialogListener(FilterDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80); // 95% width
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

        btnSelectCuisines = view.findViewById(R.id.btnSelectCuisines);
        tvSelectedCuisines = view.findViewById(R.id.tvSelectedCuisines);
        etMinPrice = view.findViewById(R.id.etMinPrice);
        etMaxPrice = view.findViewById(R.id.etMaxPrice);
        sliderMinRating = view.findViewById(R.id.sliderMinRating);
        btnApplyFilter = view.findViewById(R.id.btnApplyFilter);

        btnSelectCuisines.setOnClickListener(v -> showCuisineSelectionDialog());

        btnApplyFilter.setOnClickListener(v -> {
            int minPrice = 0;
            int maxPrice = Integer.MAX_VALUE;

            String minPriceText = etMinPrice.getText().toString();
            String maxPriceText = etMaxPrice.getText().toString();

            if (!TextUtils.isEmpty(minPriceText)) {
                try {
                    minPrice = Integer.parseInt(minPriceText);
                } catch (NumberFormatException ignored) {
                }
            }
            if (!TextUtils.isEmpty(maxPriceText)) {
                try {
                    maxPrice = Integer.parseInt(maxPriceText);
                } catch (NumberFormatException ignored) {
                }
            }

            float minRating = sliderMinRating.getValue();

            if (listener != null) {
                listener.onFilterApplied(selectedCuisines, minPrice, maxPrice, minRating);
            }
            dismiss();
        });

        updateSelectedCuisinesText();

        return view;
    }

    private void showCuisineSelectionDialog() {
        String[] cuisinesArray = {"North Indian", "Chinese", "South Indian", "Italian", "Mexican"};
        boolean[] checkedItems = new boolean[cuisinesArray.length];

        for (int i = 0; i < cuisinesArray.length; i++) {
            checkedItems[i] = selectedCuisines.contains(cuisinesArray[i]);
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Select Cuisines")
                .setMultiChoiceItems(cuisinesArray, checkedItems, (dialog, which, isChecked) -> {
                    if (isChecked) {
                        if (!selectedCuisines.contains(cuisinesArray[which]))
                            selectedCuisines.add(cuisinesArray[which]);
                    } else {
                        selectedCuisines.remove(cuisinesArray[which]);
                    }
                })
                .setPositiveButton("OK", (dialog, which) -> updateSelectedCuisinesText())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateSelectedCuisinesText() {
        if (selectedCuisines.isEmpty()) {
            tvSelectedCuisines.setText("Selected cuisines: None");
        } else {
            tvSelectedCuisines.setText("Selected cuisines: " + TextUtils.join(", ", selectedCuisines));
        }
    }
}
