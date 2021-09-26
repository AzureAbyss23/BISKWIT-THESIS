package com.example.biskwit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Dialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogueMessage extends AppCompatDialogFragment {
    //pang alert kapag nakapag create sya ng account
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("System Message").setMessage("You have created an Account!").setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
