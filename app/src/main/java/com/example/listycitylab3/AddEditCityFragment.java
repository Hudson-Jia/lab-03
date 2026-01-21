package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddEditCityFragment extends DialogFragment {

    public interface Listener {
        void onCityAdded(City city);
        void onCityEdited();
    }

    private static final String ARG_CITY = "arg_city";

    private Listener listener;
    private City editingCity;

    public static AddEditCityFragment newInstance(@Nullable City city) {
        AddEditCityFragment fragment = new AddEditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context + " must implement AddEditCityFragment.Listener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            editingCity = (City) getArguments().getSerializable(ARG_CITY);
        }

        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_city, null);
        EditText cityEt = view.findViewById(R.id.edit_city_name);
        EditText provEt = view.findViewById(R.id.edit_province);

        boolean isEdit = (editingCity != null);
        if (isEdit) {
            cityEt.setText(editingCity.getName());
            provEt.setText(editingCity.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view)
                .setTitle(isEdit ? "Edit city" : "Add city")
                .setNegativeButton("Cancel", (d, w) -> {});

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = cityEt.getText().toString().trim();
            String prov = provEt.getText().toString().trim();

            if (name.isEmpty() || prov.isEmpty()) {
                Toast.makeText(getContext(), "City and province cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEdit) {
                editingCity.setName(name);
                editingCity.setProvince(prov);
                listener.onCityEdited();
            } else {
                listener.onCityAdded(new City(name, prov));
            }
            dialog.dismiss();
        }));

        return dialog;
    }
}
