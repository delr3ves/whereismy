package io.delr3ves.whereismy.app.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import io.delr3ves.whereismy.app.R;
import io.delr3ves.whereismy.app.WhereismyApplication;
import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.dao.SearchableDao;

import javax.inject.Inject;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class AddSearchableFragment extends Fragment {

    @Inject
    SearchableDao searchableDao;

    public static final String APP_TAG = "wheresmy";
    private Searchable searchable = new Searchable();
    private Uri tmpImagePath;
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;

    private List<BluetoothDevice> bluetoothDevices;
    private EditText name;
    private EditText description;
    private ImageView photo;
    private RadioGroup bluetoothList;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((WhereismyApplication) getActivity().getApplication()).inject(this);

        final View rootView = inflater.inflate(R.layout.fragment_add_new_searchable, container, false);

        name = (EditText) rootView.findViewById(R.id.new_searchable_name);
        bluetoothList = (RadioGroup) rootView.findViewById(R.id.bluetooth_devices);
        description = (EditText) rootView.findViewById(R.id.new_searchable_description);
        saveButton = (Button) rootView.findViewById(R.id.save_searchable);
        photo = (ImageView) rootView.findViewById(R.id.searchable_image);

        name.setText(searchable.getName());
        description.setText(searchable.getDescription());
        setPhoto();
        //Bluetooth devices
        List<BluetoothDevice> pairedDevices = getBluetoothDevices();
        for (BluetoothDevice device : pairedDevices) {
            addBluetoothDeviceToList(bluetoothList, device);
        }
        bluetoothList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                         BluetoothDevice bluetoothDevice = findSelectedBluetoothDevice();
                                                         searchable.setDeviceId(bluetoothDevice.getAddress());
                                                         if (searchable.getName() == null || searchable.getName().isEmpty()) {
                                                             searchable.setName(bluetoothDevice.getName());
                                                             name.setText(searchable.getName());
                                                         }
                                                     }
                                                 }
        );

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean hasError = false;
                int errorTextColor = getResources().getColor(R.color.error_message_color);
                searchable.setName(name.getText().toString());
                if (searchable.getName() == null || searchable.getName().isEmpty()) {
                    name.setHintTextColor(errorTextColor);
                    hasError = true;
                } else {
                    name.setHintTextColor(getResources().getColor(R.color.hint_foreground_material_light));
                }
                searchable.setDescription(description.getText().toString());
                TextView text = (TextView) rootView.findViewById(R.id.select_bluetooth_device_text);

                BluetoothDevice bluetoothDevice = findSelectedBluetoothDevice();
                if (bluetoothDevice == null) {
                    text.setTextColor(errorTextColor);
                    hasError = true;
                } else {
                    searchable.setDeviceId(bluetoothDevice.getAddress());
                    text.setTextColor(getResources().getColor(R.color.abc_primary_text_material_light));
                }

                if (hasError) {
                    Toast.makeText(getActivity(), "Fill all mandatory fields", Toast.LENGTH_LONG).show();
                    return;
                }
                searchableDao.create(searchable);
                getActivity().finish();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tmpImagePath = getPhotoFileUri(new Random().nextInt(10000) + ".png");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tmpImagePath); // set the image file name
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                processCameraActivity(resultCode, data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void processCameraActivity(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            searchable.setPhoto(tmpImagePath.toString());
            setPhoto();
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "of fuck!!", Toast.LENGTH_LONG).show();
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    private Uri getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(getTag(), "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }

    private void setPhoto() {
        if (searchable.getPhoto() != null) {
            photo.setImageURI(Uri.parse(searchable.getPhoto()));
            photo.setBackground(null);
            photo.setPadding(0, 0, 0, 0);
        }
    }

    private BluetoothDevice findSelectedBluetoothDevice() {
        View selectedButton = bluetoothList.findViewById(bluetoothList.getCheckedRadioButtonId());
        int position = bluetoothList.indexOfChild(selectedButton);
        if (position >= 0 && position < bluetoothDevices.size()) {
            return bluetoothDevices.get(position);
        }
        return null;
    }

    private void addBluetoothDeviceToList(RadioGroup bluetoothList, BluetoothDevice device) {
        RadioButton button = new RadioButton(getActivity());
        button.setText(MessageFormat.format("{0} ({1})", device.getName(), device.getAddress()));
        bluetoothList.addView(button);
        if (device.getAddress().equals(searchable.getDeviceId())) {
            button.toggle();
        }
    }

    private List<BluetoothDevice> getBluetoothDevices() {
        if (bluetoothDevices == null || bluetoothDevices.isEmpty()) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothDevices = new ArrayList(mBluetoothAdapter.getBondedDevices());
        }
        return bluetoothDevices;
    }

}
