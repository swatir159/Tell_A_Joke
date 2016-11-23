package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import com.rupeeright.displayjokeandroidlib.DisplayJokeActivity;
import com.rupeeright.tell_a_joke.jokeserver.myApi.MyApi;


import java.io.IOException;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by swatir on 22-11-2016.
 */

class EndpointsAsyncTask extends AsyncTask<String, Integer, String> {
    private static MyApi myApiService = null;
    private Context mContext;
    AsyncTaskResponse mAsyncTaskResponse;
    private ProgressDialog mDialog;

    public EndpointsAsyncTask(Context ctx,  AsyncTaskResponse asyncTaskResponse ) {
        mContext = ctx;
        mDialog = null;
        mAsyncTaskResponse = asyncTaskResponse;
    }

    /**
     * onPreExecute runs on the UI thread before doInBackground.
     * This will start showing a small dialog that says Loading with a spinner
     * to let the user know download is in progress
     */
    @Override
    protected void onPreExecute() {

            super.onPreExecute();
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage(mContext.getString(R.string.AsyncTaskMessage1));
            mDialog.setProgressStyle(mDialog.STYLE_SPINNER);
            mDialog.setCancelable(true);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mDialog.dismiss();
                }
            });
            mDialog.show();
    }

    @Override
    protected String doInBackground(String... params ) {
        if(myApiService == null) {  // Only do this once
            /* MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            */
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-ba334.appspot.com/_ah/api/");

            myApiService = builder.build();
        }


        try {
            return myApiService.sayHi(/* name */ ).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if ( result != null) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                this.mDialog = null;
            }

            Intent intent = new Intent(mContext, DisplayJokeActivity.class);
            intent.putExtra(DisplayJokeActivity.INTENT_JOKE,   result);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            if (mAsyncTaskResponse != null) {
                mAsyncTaskResponse.getTaskResponse(result);
            }
        }
        else {
            Toast.makeText(mContext,mContext.getString(R.string.AsyncTaskMessage2),   Toast.LENGTH_LONG).show();
        }

    }

    public static interface AsyncTaskResponse {
        public void getTaskResponse(String response);
    }
}
