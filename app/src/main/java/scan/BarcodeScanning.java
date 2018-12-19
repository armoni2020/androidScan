package myPackage.scan;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.media.MediaPlayer;
import android.content.SharedPreferences;

public class BarcodeScanning extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;
//    private Button onBtn;
    private Button close;
    private Button archive;
    private TextView text;
    private BluetoothAdapter myBluetoothAdapter;
    private ListView myListView;
    private ArrayAdapter<String> BTArrayAdapter;
    String input;
    String mode;
    String user;
    private TextView scanText;

//    SharedPreferences pref = getApplicationContext().getSharedPreferences("MySharedPref", 0); // 0 - for private mode
//here the first parameter is name of your pref file which will hold your data. you can give any name.
// Note here the 2nd parameter 0 is the default parameter for private access.

//    SharedPreferences.Editor editor = pref.edit(); // used for save data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        input = "";
        user = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // take an instance of BluetoothAdapter - Bluetooth scanner
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(myBluetoothAdapter == null) {
//            onBtn.setEnabled(false);
            text.setText("Status: not supported");

            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",
                    Toast.LENGTH_LONG).show();
        } else {
            myBluetoothAdapter.enable();

            text = (TextView) findViewById(R.id.text);
            text.setText("Please scan userID");

            scanText = (TextView)findViewById(R.id.scans);
            scanText.setMovementMethod(new ScrollingMovementMethod());
            FileOperations fop = new FileOperations();
            scanText.setText(fop.read());

            close=(Button)findViewById(R.id.close);

            close.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return (event.getAction() != 1 || letterCheck(v, keyCode));
                }
            });

            close.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    setModeFile("USER");
                    logoff();
                }
            });

            archive=(Button)findViewById(R.id.archive);

            archive.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    FileOperations fop = new FileOperations();
                    fop.rename(getNow());
                    scanText.setText("");
                    success();
                }
            });

            myListView = (ListView)findViewById(R.id.listView1);

            // create the arrayAdapter that contains the BTDevices, and set it to the ListView
            BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            myListView.setAdapter(BTArrayAdapter);

        }
    }

    public boolean letterCheck(View v, int keyCode) {
//        Log.d("TEST", "*****************");
        switch (keyCode) {
            case KeyEvent.KEYCODE_A:
                setLetter("A");
                return true;
            case KeyEvent.KEYCODE_B:
                setLetter("B");
                return true;
            case KeyEvent.KEYCODE_C:
                setLetter("C");
                return true;
            case KeyEvent.KEYCODE_D:
                setLetter("D");
                return true;
            case KeyEvent.KEYCODE_E:
                setLetter("E");
                return true;
            case KeyEvent.KEYCODE_F:
                setLetter("F");
                return true;
            case KeyEvent.KEYCODE_G:
                setLetter("G");
                return true;
            case KeyEvent.KEYCODE_H:
                setLetter("H");
                return true;
            case KeyEvent.KEYCODE_I:
                setLetter("I");
                return true;
            case KeyEvent.KEYCODE_J:
                setLetter("J");
                return true;
            case KeyEvent.KEYCODE_K:
                setLetter("K");
                return true;
            case KeyEvent.KEYCODE_L:
                setLetter("L");
                return true;
            case KeyEvent.KEYCODE_M:
                setLetter("M");
                return true;
            case KeyEvent.KEYCODE_N:
                setLetter("N");
                return true;
            case KeyEvent.KEYCODE_O:
                setLetter("O");
                return true;
            case KeyEvent.KEYCODE_P:
                setLetter("P");
                return true;
            case KeyEvent.KEYCODE_Q:
                setLetter("Q");
                return true;
            case KeyEvent.KEYCODE_R:
                setLetter("R");
                return true;
            case KeyEvent.KEYCODE_S:
                setLetter("S");
                return true;
            case KeyEvent.KEYCODE_T:
                setLetter("T");
                return true;
            case KeyEvent.KEYCODE_U:
                setLetter("U");
                return true;
            case KeyEvent.KEYCODE_V:
                setLetter("V");
                return true;
            case KeyEvent.KEYCODE_W:
                setLetter("W");
                return true;
            case KeyEvent.KEYCODE_X:
                setLetter("X");
                return true;
            case KeyEvent.KEYCODE_Y:
                setLetter("Y");
                return true;
            case KeyEvent.KEYCODE_Z:
                setLetter("Z");
                return true;
            case KeyEvent.KEYCODE_0:
                setLetter("0");
                return true;
            case KeyEvent.KEYCODE_1:
                setLetter("1");
                return true;
            case KeyEvent.KEYCODE_2:
                setLetter("2");
                return true;
            case KeyEvent.KEYCODE_3:
                setLetter("3");
                return true;
            case KeyEvent.KEYCODE_4:
                setLetter("4");
                return true;
            case KeyEvent.KEYCODE_5:
                setLetter("5");
                return true;
            case KeyEvent.KEYCODE_6:
                setLetter("6");
                return true;
            case KeyEvent.KEYCODE_7:
                setLetter("7");
                return true;
            case KeyEvent.KEYCODE_8:
                setLetter("8");
                return true;
            case KeyEvent.KEYCODE_9:
                setLetter("9");
                return true;
            case KeyEvent.KEYCODE_SLASH:
                setLetter("/");
                return true;
            case KeyEvent.KEYCODE_ENTER:
                Log.d("ENTER", input);
                return processInput();
        }
        return true;
    }

    public String getNow() {
        String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss'#'";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return sdf.format(now);
    }

    public boolean processInput() {
//        Log.d("MODE: ", mode);
        mode = getModeFile();
        if (mode == null) {
            setModeFile("USER");
        }
        if (input.equals("DONE")) {
            setModeFile("USER");
            logoff();
        }
        String isGood = "";
        if (checkDualCode(input)) {
            setModeFile("BOXL");
            isGood = checkInput();
            input= "MOVE-" + input;
        } else {
            isGood = checkInput();
            setMode(input);
        }

        if (isGood.matches("true")) {
            String temp = getNow() + input + "\n";
            writeOutput(temp);
        }
        input = "";
        return true;
    }

    private boolean logoff() {
        myBluetoothAdapter.disable();
        System.exit(1);
        return true;
    }

    public boolean checkDualCode(String s) {
        if (s.matches("^MOVEL.*")) {
            Log.d("CHECK DUAL CODE", "MOVEL FOUND!!");
            input = s.replace("MOVE","");
            Log.d("INPUT=", input);
            return true;
        } else if (s.matches("^MOVE-L.*")) {
            Log.d("CHECK DUAL CODE", "MOVEL FOUND!!");
            input = s.replace("MOVE-","");
            Log.d("INPUT=", input);
            return true;
        }
        return false;
    }

    public boolean writeOutput(String s) {
        try {
            FileOperations fop = new FileOperations();
            fop.write(s);
            scanText.setText(fop.read());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String checkInput() {
        Log.d("CHECKING INPUT: ", input);
        if (mode.equals("USER")) {
            if (input.matches("^S.*")) {
                user = input;
                text.setText("User logged in: " + user);
                String temp = getNow() + input + "\n";
                writeOutput(temp);
                setMode("FUNC");

                return "false";
            } else {
                Log.d("FAIL: ", "USER MODE");
                return fail();
            }
        } else {
            Log.d("REGEX", "Mode: " + mode + ", Input: " + input);
            if (input.matches("^S.*")
                    || mode.equals("USER") && input.matches("^[^S].*")
                    || mode.equals("FUNC") && input.matches("^[LBP].*")
                    || mode.equals("LOC") && input.matches("^[BP].*")
                    || mode.equals("PAT") && input.matches("^[LB].*")
                    || mode.equals("BOXL") && input.matches("^P.*")
                    || mode.equals("BOXP") && input.matches("^L.*")
                    || mode.equals("BOXC") && input.matches("^[LP].*")) {
                Log.d("FAIL-mode+input", "Mode: " + mode + ", Input: " + input);
                return fail();
            } else if (input.matches("^[BLP].*")) {
                return "true";
            } else if (input.matches("ASSIGN|MOVE|RESERVE|CHECKOUT|CANCEL|CHECKIN|HOME")) {
                    return "true";
            } else {
                Log.d("FAIL-OTHER", "Mode: " + mode + ", Input: " + input);
                    return fail();
            }
        }
    }

    public String fail() {
        final MediaPlayer btnSound = MediaPlayer.create(BarcodeScanning.this, R.raw.beep01);
        btnSound.start();
        return "false";
    }

    public String success() {
        final MediaPlayer btnSound = MediaPlayer.create(BarcodeScanning.this, R.raw.beep10);
        btnSound.start();
        return "false";
    }

    public void setMode(String m) {
        if (m.matches("ASSIGN|MOVE")) {
//            mode = "LOC";
            setModeFile("LOC");
        }
        if (m.matches("RESERVE|CHECKOUT")) {
//            mode = "PAT";
            setModeFile("PAT");
        }
        if (m.matches("CANCEL|CHECKIN|HOME")) {
//            mode = "BOXC";
            setModeFile("BOXC");
        }
        if (m.matches("MOVE-.*") || (mode.matches("LOC") && m.matches("^L.*"))) {
//            mode = "BOXL";
            setModeFile("BOXL");
        }
        if (mode.matches("PAT") && m.matches("^P.*")) {
//            mode = "BOXP";
            setModeFile("BOXP");
        }
        if (m.matches("FUNC")) {
            setModeFile("FUNC");
        }
    }

    public void setModeFile(String m) {
        try {
            FileOperations fop = new FileOperations();
            mode = fop.setMode(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getModeFile() {
        String result = "";
        try {
            FileOperations fop = new FileOperations();
            result = fop.getMode();
//            mode = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setLetter(String letter) {
//        Log.d("TEST-LETTER", letter);
        input = input + letter;
    }

    public void on(View view){
        if (!myBluetoothAdapter.isEnabled()) {
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

            Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Bluetooth is already on",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == REQUEST_ENABLE_BT){
            if(myBluetoothAdapter.isEnabled()) {
                text.setText("Status: Enabled");
            } else {
                text.setText("Status: Disabled");
            }
        }
    }

    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                BluetoothGatt gatt = device.connectGatt();
//                gatt.d
                // add the name and the MAC address of the object to the arrayAdapter
                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                BTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            unregisterReceiver(bReceiver);
        } catch (Exception e) {}
    }

}
