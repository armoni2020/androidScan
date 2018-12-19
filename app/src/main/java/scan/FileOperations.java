package myPackage.scan;

/**
 * Created by ttilstra on 12/30/2014
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;
public class FileOperations {
    public FileOperations() {
    }
    public Boolean write(String fcontent){
        try {
            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/scans");
            Log.d("FOLDER", mFolder.toString());
            if (!mFolder.exists()) {
                Log.d("FOLDER: ", "Making new folder!");
                mFolder.mkdir();
            }
            String s = "scans.txt";

            File file = new File(mFolder.getAbsolutePath(), s);
            // If file does not exists, then create it
            if (!file.exists()) {
                Log.d("FILE: ", "Making new file!");
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();
            Log.d("Success","Success");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String setMode(String fcontent) {
        try {
            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/scans");
            Log.d("FOLDER", mFolder.toString());
            if (!mFolder.exists()) {
                Log.d("FOLDER: ", "Making new folder!");
                mFolder.mkdir();
            }
            String s = "mode.txt";

            File file = new File(mFolder.getAbsolutePath(), s);
            // If file does not exists, then create it
            if (!file.exists()) {
                Log.d("FILE: ", "Making new file!");
                file.createNewFile();
            } else {
                file.delete();
                file = new File(mFolder.getAbsolutePath(), s);
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();
            Log.d("Success","Success");
            return fcontent;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getMode() {
        BufferedReader br = null;
        String response = null;
        try {
            StringBuffer output = new StringBuffer();
            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/scans");
            String s = "mode.txt";
            File file = new File(mFolder.getAbsolutePath(), s);
            br = new BufferedReader(new FileReader(file));
            String line = "";
            if ((line = br.readLine()) != null) {
                output.append(line);
            }
            response = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public String read(){
        BufferedReader br = null;
        String response = null;
        try {
            StringBuffer output = new StringBuffer();
            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/scans");
            String s = "scans.txt";
            File file = new File(mFolder.getAbsolutePath(), s);
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line +"\n");
            }
            response = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public Boolean rename(String name){
        try {
            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/scans");
            String s = "scans.txt";
            File file = new File(mFolder.getAbsolutePath(), s);
            File newfile = new File(mFolder.getAbsolutePath(), "scans" + name + ".txt");
            return file.renameTo(newfile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
