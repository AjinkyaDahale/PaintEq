package com.dahale.ajinkya.painteq;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import com.dahale.ajinkya.painteq.utils.Utils;

public class NewCustomSymbolDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View v = getActivity().getLayoutInflater().inflate(R.layout.new_symbol_dialog_layout,null);
        builder.setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (v != null) {
                            EditText editText = (EditText) v.findViewById(R.id.new_symbol_edit);
                            if(editText!=null) {
                                String s = editText.getText().toString();
                                if (!s.equals("")) Utils.insertCustom(s);
                            }
                        }
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
