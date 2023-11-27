package com.rafi.attendecetaker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    public static final String CLASS_ADD_DIALOG = "addClass";
    public static final String STUDENT_ADD_DIALOG = "addStudent";
    public static final String CLASS_UPDATE_DIALOG = "updateClass";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";


    private onClickListener listener;
    private int roll;
    private String name;
    private String classname;
    private String subjectname;

    public MyDialog(int roll, String name) {

        this.roll = roll;
        this.name = name;
    }

    public MyDialog() {

    }

    public MyDialog(String classname, String subjectname) {

        this.classname = classname;
        this.subjectname = subjectname;
    }

    public interface onClickListener {
        void onClick(String text1, String text2);
    }

    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag().equals(CLASS_ADD_DIALOG))
            dialog = getAddClassDialog();
        if (getTag().equals(STUDENT_ADD_DIALOG))
            dialog = getAddStudentDialog();
        if (getTag().equals(CLASS_UPDATE_DIALOG))
            dialog = getUpdateClassDialog();
        if (getTag().equals(STUDENT_UPDATE_DIALOG))
            dialog = getUpdateStudentDialog();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    // This is used for the update the student table.
    private Dialog getUpdateStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Update Student");
        EditText roll_edt = view.findViewById(R.id.edt01);
        EditText name_edt = view.findViewById(R.id.edt02);

        roll_edt.setHint("Student Roll");
        name_edt.setHint("Student Name");
        Button cancel = view.findViewById(R.id.canclebtn);
        Button add = view.findViewById(R.id.addbtn);
        add.setText("UPDATE");
        roll_edt.setText(roll+"");
        name_edt.setText(name);
        roll_edt.setEnabled(false); // By this line we only show the roll edit text not edit it.
        cancel.setOnClickListener(V -> dismiss());
        add.setOnClickListener(v -> {
            String roll = roll_edt.getText().toString();
            String name = name_edt.getText().toString();
            // After adding one entry next time roll number is increment here by this logic.
            listener.onClick(roll, name);
            dismiss();
        });
        return builder.create();
    }

    //This is used for the class update dialog.
    private Dialog getUpdateClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Update Class");
        EditText classeditText = view.findViewById(R.id.edt01);
        EditText subjecteditText = view.findViewById(R.id.edt02);

        classeditText.setHint("Class Name");
        subjecteditText.setHint("Subject Name");
        Button cancel = view.findViewById(R.id.canclebtn);
        Button add = view.findViewById(R.id.addbtn);
        add.setText("UPDATE");
        classeditText.setText(classname);
        subjecteditText.setText(subjectname);

        cancel.setOnClickListener(V -> dismiss());
        add.setOnClickListener(v -> {
            String classname = classeditText.getText().toString();
            String subjentName = subjecteditText.getText().toString();
            listener.onClick(classname, subjentName);
            dismiss();
        });
        return builder.create();
    }

    // Create student dialog here.
    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add New Student");
        EditText roll_edt = view.findViewById(R.id.edt01);
        EditText name_edt = view.findViewById(R.id.edt02);

        roll_edt.setHint("Student Roll");
        name_edt.setHint("Student Name");
        Button cancel = view.findViewById(R.id.canclebtn);
        Button add = view.findViewById(R.id.addbtn);

        cancel.setOnClickListener(V -> dismiss());
        add.setOnClickListener(v -> {
            String roll = roll_edt.getText().toString();
            String name = name_edt.getText().toString();
            // After adding one entry next time roll number is increment here by this logic.
            roll_edt.setText(String.valueOf(Integer.parseInt(roll)+1));
            name_edt.setText("");
            listener.onClick(roll, name);
        });
        return builder.create();
    }


    // Create class dialog here.
    private Dialog getAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add New Class");
        EditText classeditText = view.findViewById(R.id.edt01);
        EditText subjecteditText = view.findViewById(R.id.edt02);

        classeditText.setHint("Class Name");
        subjecteditText.setHint("Subject Name");
        Button cancel = view.findViewById(R.id.canclebtn);
        Button add = view.findViewById(R.id.addbtn);

        cancel.setOnClickListener(V -> dismiss());
        add.setOnClickListener(v -> {
            String classname = classeditText.getText().toString();
            String subjentName = subjecteditText.getText().toString();
            listener.onClick(classname, subjentName);
            dismiss();
        });
        return builder.create();
    }
}
