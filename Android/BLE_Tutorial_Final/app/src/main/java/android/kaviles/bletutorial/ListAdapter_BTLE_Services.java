package android.kaviles.bletutorial;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kelvin on 5/8/16.
 */
public class ListAdapter_BTLE_Services extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<BluetoothGattService> services_ArrayList;
    private HashMap<String, ArrayList<BluetoothGattCharacteristic>> characteristics_HashMap;

    public ListAdapter_BTLE_Services(Activity activity, ArrayList<BluetoothGattService> listDataHeader,
                                 HashMap<String, ArrayList<BluetoothGattCharacteristic>> listChildData) {

        this.activity = activity;
        this.services_ArrayList = listDataHeader;
        this.characteristics_HashMap = listChildData;
    }

    @Override
    public int getGroupCount() {
        return services_ArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return characteristics_HashMap.get(
                services_ArrayList.get(groupPosition).getUuid().toString()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return services_ArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return characteristics_HashMap.get(
                services_ArrayList.get(groupPosition).getUuid().toString()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        BluetoothGattService bluetoothGattService = (BluetoothGattService) getGroup(groupPosition);

        String serviceUUID = bluetoothGattService.getUuid().toString();
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.btle_service_list_item, null);
        }

        TextView tv_service = (TextView) convertView.findViewById(R.id.tv_service_uuid);
        tv_service.setText("S: " + serviceUUID);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        BluetoothGattCharacteristic bluetoothGattCharacteristic = (BluetoothGattCharacteristic) getChild(groupPosition, childPosition);

        String characteristicUUID =  bluetoothGattCharacteristic.getUuid().toString();
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.btle_characteristics_list_item, null);
        }

        TextView tv_service = (TextView) convertView.findViewById(R.id.tv_characteristic_uuid);
        tv_service.setText("C: " + characteristicUUID);

        int properties = bluetoothGattCharacteristic.getProperties();

        TextView tv_property = (TextView) convertView.findViewById(R.id.tv_properties);
        StringBuilder sb = new StringBuilder();

        if (Utils.hasReadProperty(properties) != 0) {
            sb.append("R");
        }

        if (Utils.hasWriteProperty(properties) != 0) {
            sb.append("W");
        }

        if (Utils.hasNotifyProperty(properties) != 0) {
            sb.append("N");
        }

        tv_property.setText(sb.toString());

        TextView tv_value = (TextView) convertView.findViewById(R.id.tv_value);

        byte[] data = bluetoothGattCharacteristic.getValue();
        if (data != null) {
            tv_value.setText("Value: " + Utils.hexToString(data));
        }
        else {
            tv_value.setText("Value: ---");
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
