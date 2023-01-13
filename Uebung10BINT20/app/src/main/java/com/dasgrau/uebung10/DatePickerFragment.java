package com.dasgrau.uebung10;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    protected static final String TAG = DatePickerFragment.class.toString();

    private DatePickerDialog.OnDateSetListener listener = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        return new DatePickerDialog(requireContext(), listener,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof DatePickerDialog.OnDateSetListener)
            listener = (DatePickerDialog.OnDateSetListener) context;
    }
}
