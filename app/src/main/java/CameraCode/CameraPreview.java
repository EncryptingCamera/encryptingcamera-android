package CameraCode; /**
 * Created by stephen on 12/29/14.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/** A basic Camera preview class */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String DEBUG_CAMERA_PREVIEW = "Camera Preview";

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        Camera.Parameters p = mCamera.getParameters();
        //p.setPictureSize(IMAGE_WIDTH, IMAGE_HEIGHT);

        mCamera.getParameters().setRotation(90);

        Camera.Size s = p.getSupportedPreviewSizes().get(0);
        p.setPreviewSize( s.width, s.height );

        p.setPictureFormat(PixelFormat.JPEG);
        p.set("flash-mode", "auto");
        mCamera.setDisplayOrientation(90);
        mCamera.setParameters(p);

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mHolder.setKeepScreenOn(true);
        } catch (IOException e) {
            Log.d(DEBUG_CAMERA_PREVIEW, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        //setCameraDisplayOrientation(this, 0, mCamera);

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(DEBUG_CAMERA_PREVIEW, "Error starting camera preview: " + e.getMessage());
        }
    }


}