package io.delr3ves.whereismy.app.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import io.delr3ves.whereismy.app.WhereismyApplication;
import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.dao.SearchableDao;
import io.delr3ves.whereismy.app.service.TrackedLocationService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class BluetoothDisconnectionReceiver extends BroadcastReceiver {

    @Inject
    SearchableDao searchableDao;

    @Inject
    TrackedLocationService trackedLocationService;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((WhereismyApplication) context.getApplicationContext()).inject(this);
        BluetoothDevice device = getBluetoothDevice(intent);

        List<Searchable> searchables = searchableDao.findByDeviceId(device.getAddress());
        trackedLocationService.locate(searchables);
    }

    private BluetoothDevice getBluetoothDevice(Intent intent) {
        return intent
                .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
    }
}
