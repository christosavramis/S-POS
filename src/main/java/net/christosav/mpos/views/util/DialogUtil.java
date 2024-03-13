package net.christosav.mpos.views.util;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

public class DialogUtil {
    public static void createConfirmationDialog(ComponentEventListener<ConfirmDialog.CancelEvent> cancelListener,
                                          ComponentEventListener<ConfirmDialog.ConfirmEvent> saveListener) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Unsaved changes");
        dialog.setText("There are unsaved changes. Do you want to cancel or save them?");

        dialog.setCancelable(true);
        dialog.addCancelListener(cancelListener);

        dialog.setConfirmText("Save");
        dialog.addConfirmListener(saveListener);

        Button button = new Button("Open confirm dialog");
        button.addClickListener(event -> dialog.open());

        dialog.open();
    }


    public static void createConfirmationDialog(
            String header, String text, String confirmText,
            ComponentEventListener<ConfirmDialog.CancelEvent> cancelListener,
            ComponentEventListener<ConfirmDialog.ConfirmEvent> saveListener) {

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader(header);
        dialog.setText(text);

        dialog.setCancelable(true);
        dialog.addCancelListener(cancelListener);

        dialog.setConfirmText(confirmText);
        dialog.addConfirmListener(saveListener);

        Button button = new Button("Open confirm dialog");
        button.addClickListener(event -> dialog.open());

        dialog.open();
    }
}
