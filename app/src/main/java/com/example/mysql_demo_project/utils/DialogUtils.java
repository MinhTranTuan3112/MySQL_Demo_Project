package com.example.mysql_demo_project.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class DialogUtils {
    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showConfirmationDialog(Context context, String title, String message, Runnable onConfirmAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Yes", (dialogInterface, i) -> onConfirmAction.run());

        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
