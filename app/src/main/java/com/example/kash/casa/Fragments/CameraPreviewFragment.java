package com.example.kash.casa.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.kash.casa.Helpers.CasaPreferenceManager;
import com.example.kash.casa.R;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kash on 2/14/2015.
 * Do work homie.
 */
@SuppressWarnings("deprecation")
public class CameraPreviewFragment extends Fragment
{

    //UI Elements
    @InjectView(R.id.camera_preview)
    SurfaceView mSurfaceView;
    @InjectView(R.id.camera_button_capture)
    Button mButtonCapture;
    @InjectView(R.id.camera_button_switch)
    ImageButton mButtonSwitch;

    private CasaPreferenceManager camPrefs;
    private SurfaceHolder previewHolder;
    private Camera mCamera;
    private int mCameraId;
    SurfaceHolder.Callback surfaceCallback = new MySurfaceHolderCallback();
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_camera_preview, container, false);
        ButterKnife.inject(this, view);

        context = getActivity();
        camPrefs = new CasaPreferenceManager(context, CasaPreferenceManager.PrefType.Camera);
        mCameraId = camPrefs.getCameraOreintationId();

        previewHolder = mSurfaceView.getHolder();
        previewHolder.addCallback(surfaceCallback);

//        set up capture button
//        mButtonCapture.setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View v) {
//                        mCamera.takePicture(null, null, null, mPicture);
//                    }
//                }
//        );
//
//        set up switch button TODO is this efficient?
//        mButtonSwitch.setOnClickListener(new View.OnClickListener() {
//                                            public void onClick(View v) {
//                                                if (mCameraId == CAMERA_FACING_FRONT)
//                                                    mCameraId = CAMERA_FACING_BACK;
//                                                else
//                                                    mCameraId = CAMERA_FACING_FRONT;
//
//                                                stopPreviewAndFreeCamera();
//                                                mCamera.release();
//
//                                                mCamera = Camera.open(mCameraId);
//
//                                                Display display = getActivity().getWindowManager().getDefaultDisplay();
//                                                Point size = new Point();
//                                                display.getSize(size);
//                                                int width = size.x;
//                                                int height = size.y;
//                                                setCameraDisplayOrientation();
//
//                                                try {
//                                                    mCamera.setPreviewDisplay(previewHolder);
//                                                } catch (IOException e) {
//                                                    Log.d("Camera", "Switch failed to setPreviewDisplay");
//                                                }
//                                                startPreview();
//                                            }
//                                        }
//        );

        return view;
    }


    @Override
    public void onResume()
    {
        startPreview();
        super.onResume();
    }

    @Override
    public void onPause()
    {
        stopPreviewAndFreeCamera();
        super.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        camPrefs.storeCameraSettings(mCameraId);
    }
//
//    private Camera.Size getBestPreviewSize(int w, int h, Camera.Parameters parameters)
//    {
//        final double ASPECT_TOLERANCE = 0.1;
//        double targetRatio = (double) h / w;
//
//        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
//        if (sizes == null) return null;
//
//        Camera.Size optimalSize = null;
//        double minDiff = Double.MAX_VALUE;
//
//        for (Camera.Size size : sizes)
//        {
//            double ratio = (double) size.width / size.height;
//            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
//            if (Math.abs(size.height - h) < minDiff)
//            {
//                optimalSize = size;
//                minDiff = Math.abs(size.height - h);
//            }
//        }
//
//        if (optimalSize == null)
//        {
//            minDiff = Double.MAX_VALUE;
//            for (Camera.Size size : sizes)
//            {
//                if (Math.abs(size.height - h) < minDiff)
//                {
//                    optimalSize = size;
//                    minDiff = Math.abs(size.height - h);
//                }
//            }
//        }
//        return optimalSize;
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private Camera.Size getClosestPictureSize(Camera.Parameters parameters)
//    {
//        Camera.Size result = null;
//
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        Point point = new Point();
//        display.getRealSize(point);
//        int width = point.x;
//        int height = point.y;
//
//        for (Camera.Size size : parameters.getSupportedPictureSizes())
//        {
//            if (result == null)
//            {
//                result = size;
//            }
//            else
//            {
//                int resultArea = result.width * result.height;
//                int newArea = size.width * size.height;
//
//                if (newArea >= width * height || (newArea > resultArea && newArea <= width * height))
//                {
//                    result = size;
//                }
//            }
//        }
//
//        return (result);
//    }

    public void setCameraDisplayOrientation()
    {

        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, info);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation)
        {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
        {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        }
        else
        {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        mCamera.setDisplayOrientation(result);
    }
//
//    private void setCameraDisplayOrientation(int width, int height)
//    {
//        Camera.Parameters parameters = mCamera.getParameters();
//        Camera.Size previewSize = getBestPreviewSize(width, height, parameters);
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        DisplayMetrics localDisplayMetrics = getActivity().getResources().getDisplayMetrics();
//        Camera.getCameraInfo(0, new Camera.CameraInfo());
//
//        //fix rotation
//        switch (display.getRotation())
//        {
//            case Surface.ROTATION_0: // This is display orientation
//                if (previewSize.height > previewSize.width)
//                    parameters.setPreviewSize(previewSize.height, previewSize.width);
//                else parameters.setPreviewSize(previewSize.width, previewSize.height);
//                mCamera.setDisplayOrientation(90);
//                break;
//            case Surface.ROTATION_90:
//                if (previewSize.height > previewSize.width)
//                    parameters.setPreviewSize(previewSize.height, previewSize.width);
//                else parameters.setPreviewSize(previewSize.width, previewSize.height);
//                mCamera.setDisplayOrientation(0);
//                break;
//            case Surface.ROTATION_180:
//                if (previewSize.height > previewSize.width)
//                    parameters.setPreviewSize(previewSize.height, previewSize.width);
//                else parameters.setPreviewSize(previewSize.width, previewSize.height);
//                mCamera.setDisplayOrientation(270);
//                break;
//            case Surface.ROTATION_270:
//                if (previewSize.height > previewSize.width)
//                    parameters.setPreviewSize(previewSize.height, previewSize.width);
//                else parameters.setPreviewSize(previewSize.width, previewSize.height);
//                mCamera.setDisplayOrientation(180);
//                break;
//        }
//
//        parameters.setPictureSize(previewSize.width, previewSize.height);
//
//        //This may cause errors later on. Make sure camera mode is focusable
//        if (!parameters.getFocusMode().equals(FOCUS_MODE_FIXED) &&
//                !parameters.getFocusMode().equals(FOCUS_MODE_INFINITY))
//            parameters.setFocusMode(FOCUS_MODE_CONTINUOUS_PICTURE);
//
//        mCamera.setParameters(parameters);
//        //cameraConfigured = true;
//    }
//
//    private void initPreview()
//    {
//        if (mCamera != null && previewHolder.getSurface() != null)
//        {
//            try
//            {
//                mCamera.setPreviewDisplay(previewHolder);
//            } catch (Throwable t)
//            {
//                Log.d("Camera", "setPreviewDisplay failed");
//            }
//
//            setCameraDisplayOrientation();
//        }
//    }

    public void safeCameraOpen(final int cameraId)
    {
        try
        {
            stopPreviewAndFreeCamera();
            mCamera = Camera.open(cameraId);
        } catch (Exception e)
        {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }
    }

    public void startPreview()
    {
        safeCameraOpen(mCameraId);

        if (mCamera != null)
        {
            try
            {
                // Important: Call startPreview() to start updating the preview
                // surface. Preview must be started before you can take a picture.
                setCameraDisplayOrientation();
                mCamera.setPreviewDisplay(previewHolder);
                mCamera.startPreview();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void stopPreviewAndFreeCamera()
    {
        if (mCamera != null)
        {
            // Call stopPreviewAndFreeCamera() to stop updating the preview surface.
            mCamera.stopPreview();

            // Important: Call release() to release the camera for use by other
            // applications. Applications should release the camera immediately
            // during onPause() and re-open() it during onResume()).
            mCamera.release();

            mCamera = null;
        }
    }

    public class MySurfaceHolderCallback implements SurfaceHolder.Callback
    {
        public void surfaceCreated(SurfaceHolder holder)
        {
            startPreview();
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {
            startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder)
        {
            stopPreviewAndFreeCamera();
        }
    }

//    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
//
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            Display display = getActivity().getWindowManager().getDefaultDisplay();
//            int rotation = 0;
//            Matrix mat = new Matrix();
//
//            //find rotation of screen
//            switch (display.getRotation()) {
//                case Surface.ROTATION_0: // This is display orientation
//                    rotation = 90;
//                    break;
//                case Surface.ROTATION_90:
//                    rotation = 0;
//                    break;
//                case Surface.ROTATION_180:
//                    rotation = 270;
//                    break;
//                case Surface.ROTATION_270:
//                    rotation = 180;
//                    break;
//            }
//
//            //Front facing needs to mirror or something. Who knows, this fixes the image
//            if (mCameraId == CAMERA_FACING_FRONT) {
//                mat.preScale(-1, 1);
//            }
//
//            //rotate picture
//            mat.postRotate(rotation);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                    bitmap.getHeight(), mat, true);
//
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
//        }
//    };
}
