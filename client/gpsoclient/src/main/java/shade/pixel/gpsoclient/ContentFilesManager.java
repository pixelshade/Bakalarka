package shade.pixel.gpsoclient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pixelshade on 13.2.2014.
 */
public class ContentFilesManager {
    Context mContext;
    ProgressDialog pDialog;
    ArrayList<String> localFiles;
    MyHtmlBrowser htmlBrowser;

    public ContentFilesManager(Context mContext) {
        this.mContext = mContext;
        htmlBrowser = MyHtmlBrowser.getInstance(mContext);

    }

    public void UpdateFiles() {
        UpdateLocalFilesList();
        GetServerContentFilenames();

    }

    private void GetServerContentFilenames() {
        AsyncTask<String, Integer, ArrayList<String>> asyncTask = new AsyncTask<String, Integer, ArrayList<String>>() {
            ArrayList<String> serverFilenames = new ArrayList<String>();

            @Override
            protected ArrayList<String> doInBackground(String... strings) {
                JSONArray jsonArray = null;

                String json = htmlBrowser.HttpGetString(Settings.getContentFilesListURL());
                try {
                    jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++)
                            serverFilenames.add(jsonArray.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(mContext, json, Toast.LENGTH_LONG).show();
                return serverFilenames;
            }

            @Override
            protected void onPostExecute(ArrayList<String> remoteFiles) {
                super.onPostExecute(remoteFiles);
                ArrayList<String> filesToDownload = GetListOfMissingLocalFiles(remoteFiles);
                if (filesToDownload.isEmpty()) {
                    Toast.makeText(mContext, "nothing to update", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "Starting downlaoding files"+ remoteFiles, Toast.LENGTH_LONG).show();
                    DownloadFiles(filesToDownload);
                }
            }


        };


        asyncTask.execute();
    }

    private ArrayList<String> GetListOfMissingLocalFiles(ArrayList<String> remoteFiles) {
        remoteFiles.removeAll(localFiles);
        return remoteFiles;
    }

    private void UpdateLocalFilesList() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            CreateDirIfDoesntExist();
            File contentFileDir = new File(Environment.getExternalStorageDirectory() + "/" + Settings.getContentFileDir());
            localFiles = new ArrayList<String>(Arrays.asList(contentFileDir.list()));
            Log.d("AHA", localFiles.toString());
        } else {
            Toast.makeText(mContext, "No sdcard", Toast.LENGTH_SHORT).show();
        }
    }

    private void CreateDirIfDoesntExist() {
        File contentFileDir = new File(Environment.getExternalStorageDirectory() + "/" + Settings.getContentFileDir());
        if (!contentFileDir.isDirectory()) {
            contentFileDir.mkdir();
        }
    }

    protected Dialog showDialog() {
        pDialog = new ProgressDialog(mContext);
        pDialog.setTitle("Synchronizing files");
        pDialog.setMessage("Downloading files. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.show();
        return pDialog;
    }

    /**
     * Background Async Task to download file
     */

    private void DownloadFiles(ArrayList<String> filenames) {
        AsyncTask<ArrayList<String>, Integer, ArrayList<String>> ast = new AsyncTask<ArrayList<String>, Integer, ArrayList<String>>() {
            /**
             * Before starting background thread
             * Show Progress Bar Dialog
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog();
            }

            /**
             * Downloading file in background thread
             */
            @Override
            protected ArrayList<String> doInBackground(ArrayList<String>... filenames) {
                int count;

                for (String filename : filenames[0]) {
                    String fileURL = Settings.getServerContentDirURL() + filename;
                    Log.d("AHA", fileURL);
                    try {
                        URL url = new URL(fileURL);
                        URLConnection conection = url.openConnection();
                        conection.connect();
                        // getting file length
                        int lenghtOfFile = conection.getContentLength();

                        // input stream to read file - with 8k buffer
                        InputStream input = new BufferedInputStream(url.openStream(), 8192);

                        // Output stream to write file
                        OutputStream output = new FileOutputStream(getLocalContentFolder() + filename);

                        byte data[] = new byte[1024];

                        long total = 0;

                        while ((count = input.read(data)) != -1) {
                            total += count;
                            onProgressUpdate((int) ((total * 100) / lenghtOfFile));

                            // writing data to file
                            output.write(data, 0, count);
                        }

                        // flushing output
                        output.flush();

                        // closing streams
                        output.close();
                        input.close();

                    } catch (Exception e) {
                        Log.e("Error: ", e.getMessage());
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                // setting progress percentage
                pDialog.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(ArrayList<String> result) {
                pDialog.dismiss();

                // Displaying downloaded image into image view
                // Reading image path from sdcard
                //String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
                // setting downloaded into image view
                // my_image.setImageDrawable(Drawable.createFromPath(imagePath));
            }


        };
        ast.execute(filenames);
    }

    private String getLocalContentFolder() {
        return Environment.getExternalStorageDirectory() + "/" + Settings.getContentFileDir() + "/";
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    //    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
//        protected Long doInBackground(URL... urls) {
//            int count = urls.length;
//            long totalSize = 0;
//            for (int i = 0; i < count; i++) {
//                totalSize += Downloader.downloadFile(urls[i]);
//                publishProgress((int) ((i / (float) count) * 100));
//                // Escape early if cancel() is called
//                if (isCancelled()) break;
//            }
//            return totalSize;
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
//        }
//
//        protected void onPostExecute(Long result) {
//            showDialog("Downloaded " + result + " bytes");
//        }
//    }
}
