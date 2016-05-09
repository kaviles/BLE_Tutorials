package android.kaviles.bletutorial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by Kelvin on 5/9/16.
 */
public class Dialog_BTLE_Characteristic extends DialogFragment implements DialogInterface.OnClickListener {

    private String title;
    private Service_BTLE_GATT service;
    private BluetoothGattCharacteristic characteristic;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_btle_characteristic, null))
                .setNegativeButton("Cancel", this).setPositiveButton("Send", this);
        builder.setTitle(title);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        // Find a way to check which button as pressed cancel or ok
//            Utils.toast(activity.getApplicationContext(), "Button " + Integer.toString(which) + " Pressed");

        EditText edit = (EditText) ((AlertDialog) dialog).findViewById(R.id.et_submit);

        switch (which) {
            case -2:
                // cancel button pressed
                break;
            case -1:
                // okay button pressed
                if (service != null) {
                    characteristic.setValue(edit.getText().toString());
                    service.writeCharacteristic(characteristic);
                }
                break;
            default:
                break;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setService(Service_BTLE_GATT service) {
        this.service = service;
    }

    public void setCharacteristic(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }
}
