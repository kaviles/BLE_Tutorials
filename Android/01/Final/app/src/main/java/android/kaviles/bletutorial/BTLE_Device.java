package android.kaviles.bletutorial;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Kelvin on 5/8/16.
 */
public class BTLE_Device {

    private BluetoothDevice bluetoothDevice;
    private int rssi;

    public BTLE_Device(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    public String getName() {
        return bluetoothDevice.getName();
    }

    public void setRSSI(int rssi) {
        this.rssi = rssi;
    }

    public int getRSSI() {
        return rssi;
    }
}
