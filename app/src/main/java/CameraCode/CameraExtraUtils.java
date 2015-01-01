package CameraCode;

import android.app.Activity;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

/**
 * Created by stephen on 12/31/14.
 */
public class CameraExtraUtils {
    private static final String DEBUG_CAMERA_PREVIEW = "Camera";
    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        Log.d("camera", "Camera orientation (" + info.orientation + ", " + rotation + ", " + rotation + ")");

                camera.setDisplayOrientation(result);
    }
}
