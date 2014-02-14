package com.dahale.ajinkya.painteq;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.dahale.ajinkya.painteq.utils.Utils;

public class CustomSymbolDialogFragment extends DialogFragment {
    private String message, initialText;

    public CustomSymbolDialogFragment(String message) {
        this(message, "");
    }

    public CustomSymbolDialogFragment(String message, String initialText) {
        this.message = message;
        this.initialText = initialText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View v = getActivity().getLayoutInflater().inflate(R.layout.edit_symbol_dialog_layout,null);
        if(v!=null) {
            ((TextView) v.findViewById(R.id.dialog_message)).setText(message);
            ((EditText) v.findViewById(R.id.custom_symbol_edittext)).setText(initialText);
        }
        builder.setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (v != null) {
//                            ((TextView) v.findViewById(R.id.dialog_message)).setText(message);
                            EditText editText = (EditText) v.findViewById(R.id.custom_symbol_edittext);
                            if(editText!=null) {
                                @SuppressWarnings("ConstantConditions") String s = editText.getText().toString();
                                if (!s.equals("")) Utils.insertCustom(s);
                                editText.requestFocus();
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
