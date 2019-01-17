//package fh.ooe.mcm.inactivitytracker.utils;
//
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import fh.ooe.mcm.inactivitytracker.interfaces.Observer;
//
//public class Logger extends AsyncTask<ArrayList<String>, Integer, Void> {
//
//    ArrayList<Observer> observers;
//
//    String filename = "features.txt";
//    FileOutputStream outputStream;
//    File file;
//
//    protected Void doInBackground(ArrayList<String>... arrayLists) {
//        ArrayList<String> lines = arrayLists[0];
//        try {
//            outputStream = new FileOutputStream(file, true);
//            for (String line : lines) {
//                outputStream.write(line.getBytes());
//                // Escape early if cancel() is called
//                if (isCancelled()) break;
//            }
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public Logger(File path, Observer observer) {
//        File file = new File(path +"/" + filename);
//        if (!file.exists()) {
//            try {
//                if (!file.createNewFile()) {
//                    Toast.makeText(parent.getApplicationContext(), "File not created.", Toast.LENGTH_SHORT);
//                    parent.finish();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
