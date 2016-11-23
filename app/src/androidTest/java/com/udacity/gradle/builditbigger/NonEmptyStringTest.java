package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.mockito.Mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by swatir on 22-11-2016.
 */

public class NonEmptyStringTest extends InstrumentationTestCase implements EndpointsAsyncTask.AsyncTaskResponse {

    public static final String LOG_TAG = NonEmptyStringTest.class.getSimpleName();
    EndpointsAsyncTask task;
    CountDownLatch countDownLatch;
    Context mContext;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getInstrumentation().getTargetContext();
        countDownLatch = new CountDownLatch(1);
        task = new EndpointsAsyncTask(mContext, NonEmptyStringTest.this){
            @Override
            protected void onPostExecute(String joke) {
                //No need to launch intent, so override this method
            }

            @Override
            protected void onPreExecute() {
                //No need to launch loader, so override this method
            }
        };
    }

    /**
     * Override getTaskResponse in EndpointsAsyncTask.AsyncTaskResponse to test response returned
     * @param response
     */
    @Override
    public void getTaskResponse(String response) {
        Log.v(LOG_TAG, "In testResult():" + response);
        countDownLatch.countDown();
        assertTrue(response != null && !response.isEmpty());        //test if not null/non empty joke is returned
    }

    /**
     * Run asnc task  on Ui thread
     * @throws Throwable
     */
    public void testAsyncTask() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    task.execute();
                    countDownLatch.await(30, TimeUnit.SECONDS);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}