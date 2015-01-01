package CameraCode; /**
 * Created by stephen on 12/29/14.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/** A basic Camera preview class */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String DEBUG_CAMERA_PREVIEW = "Camera";

    private SurfaceHolder mHolder;
    private Context mainContext;

    private Camera mCamera;
    private int cameraId;

    public CameraPreview(Context context, Camera camera, int cameraId) {
        super(context);
        mCamera = camera;
        mainContext = context;
        this.cameraId = cameraId;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        //Camera.Parameters p = mCamera.getParameters();
        //p.setPictureSize(IMAGE_WIDTH, IMAGE_HEIGHT);

        //mCamera.getParameters().setRotation(90);

        //Camera.Size s = p.getSupportedPreviewSizes().get(0);
        //p.setPreviewSize( s.width, s.height );

        //p.setPictureFormat(PixelFormat.JPEG);
        //p.set("flash-mode", "auto");
//        mCamera.setDisplayOrientation(90);
      //  mCamera.setParameters(p);
         CameraExtraUtils.setCameraDisplayOrientation((Activity)mainContext, cameraId, mCamera);

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

      /*  try {
            Camera.Parameters parameters = mCamera.getParameters();
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            }
            else {
                // This is an undocumented although widely known feature
                parameters.set("orientation", "landscape");
                // For Android 2.2 and above
                mCamera.setDisplayOrientation(180);
                // Uncomment for Android 2.0 and above
                // parameters.setRotation(0);
            }
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (IOException e) {
            // left blank for now
        }*/
        try {
            CameraExtraUtils.setCameraDisplayOrientation((Activity)mainContext, cameraId, mCamera);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(DEBUG_CAMERA_PREVIEW, "Error starting camera preview: " + e.getMessage());
        }
    }


}