package com.example.kash.casa.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kash.casa.CameraPreview;
import com.example.kash.casa.Helpers.CasaPreferenceManager;
import com.example.kash.casa.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kash on 2/19/2015.
 * Do work homie.
 */
@SuppressWarnings("deprecation")
public class NewCameraPreviewFragment extends Fragment
{
    //UI Elements
    @InjectView(R.id.camera_preview)
    SurfaceView mSurfaceView;
    @InjectView(R.id.camera_button_capture)
    Button mButtonCapture;
    @InjectView(R.id.camera_button_switch)
    ImageButton mButtonSwitch;

    //Props
    private CasaPreferenceManager cameraPreferences;
    private SurfaceHolder previewHolder;
    private Camera mCamera;
    private boolean inPreview = false;
    private boolean cameraConfigured = false;
    private int cameraId;
    CameraPreview preview;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera_preview, container, false);
        ButterKnife.inject(this, view);

        //instantiate
        context = getActivity();
        preview = new CameraPreview(context,
                                            (SurfaceView) view.findViewById(R.id.camera_preview));
        cameraPreferences = new CasaPreferenceManager(context, CasaPreferenceManager.PrefType.Camera);
        cameraId = cameraPreferences.getCameraOreintationId();


        previewHolder = mSurfaceView.getHolder();
        previewHolder.addCallback(preview);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int numCams = Camera.getNumberOfCameras();
        if(numCams > 0){
            try{
                mCamera = Camera.open(0);
                mCamera.startPreview();
                preview.setCamera(mCamera);
            } catch (RuntimeException ex){
                Log.d("Casa", ex.getLocalizedMessage());
            }
        }
    }

    @Override
    public void onPause() {
        if(mCamera != null) {
            mCamera.stopPreview();
            preview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        cameraPreferences.storeCameraSettings(cameraId);
    }

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
