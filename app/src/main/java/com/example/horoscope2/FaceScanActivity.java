package com.example.horoscope2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.horoscope2.PrincipalActivity.capt;

public class FaceScanActivity extends AppCompatActivity {
    //TODO: if face is detected, conserve the pic.

    public static final String CAMERA_FRONT = "1";
    public static final String CAMERA_BACK = "0";

    private String cameraId = CAMERA_BACK;

    private File path;

    private Button btnCapture;
    private TextureView textureView;
    private ImageButton otherCamera;
    String message;

    //Check state orientation of output image
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static{
        ORIENTATIONS.append(Surface.ROTATION_0,270);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,90);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSessions;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;

    Uri outputFileUri;

    //Save to File
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    CameraDevice.StateCallback stateCallBack=new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice=camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.d("err", String.valueOf(error));
            cameraDevice.close();
            cameraDevice=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_scan);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        message = intent.getStringExtra(capt);

        //TODO: set switchBtn background
        textureView = (TextureView)findViewById(R.id.textureView);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);
        btnCapture = (Button)findViewById(R.id.btnCapture);

        otherCamera = (ImageButton) findViewById(R.id.otherCamera);

        if(message.equals("Hand Capture")){
            otherCamera.setImageResource(R.drawable.contornomano1);
        }else{
            otherCamera.setImageResource(R.drawable.cara);
        }

        btnCapture.setText(message);
        btnCapture.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                takePicture();
               /** if(outputFileUri!=null){

                }else createCameraPreview();*/
            }

        });


    }

    private void takePicture(){
        if(cameraDevice==null)
            return;
        CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);

        try{
            CameraCharacteristics characteristics= manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes=null;
            if(characteristics!=null)
                jpegSizes=characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);

            //Capture image with custom size
            int width = 500;
            int height = 500;
            /** Puedes darles el tamaño por defecto: 3264x2448
            if(jpegSizes!=null&&jpegSizes.length>0){
                width=jpegSizes[0].getWidth();
                height=jpegSizes[0].getHeight();
            }*/
            ImageReader reader=ImageReader.newInstance(width,height,ImageFormat.JPEG,2);
            final List<Surface> outputSurface=new ArrayList<>(2);
            outputSurface.add(reader.getSurface());
            outputSurface.add(new Surface(textureView.getSurfaceTexture()));

            final CaptureRequest.Builder captureBuilder=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            //Check orientation base on device
            int rotation=getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));

            path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);


                file=new File(path, "DemoPicture.jpg");
            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;
                    try{
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        save(bytes);
                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    finally{
                        {
                            if(image!=null)
                                image.close();
                        }
                    }
                }
                private void save(byte[] bytes)throws IOException{
                    OutputStream outputStream= null;
                    try{

                        path.mkdirs();

                        outputStream=new FileOutputStream(file);
                        outputStream.write(bytes);
                    }
                    finally{
                        if(outputStream!=null)
                            outputStream.close();
                    }

                }
            };



            reader.setOnImageAvailableListener(readerListener,mBackgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener=new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);

                    Toast.makeText(FaceScanActivity.this,"Saved"+file,Toast.LENGTH_SHORT).show();
                    // Capturo el Uri
                    outputFileUri = Uri.fromFile(file);
                    Toast.makeText(FaceScanActivity.this,outputFileUri.toString(),Toast.LENGTH_SHORT).show();
                    /** Creo un nuevo Intent*/
                    Intent intent1 = new Intent(FaceScanActivity.this, ImageLabelingActivity.class);
                    /**Coloco en el intent el outputFileUri */
                    intent1.putExtra("imageUri", outputFileUri);
                    intent1.putExtra(capt,message);
                    startActivity(intent1);
                    //createCameraPreview();
                    finish();



                }
            };

            cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    try{
                        cameraCaptureSession.capture(captureBuilder.build(),captureListener,mBackgroundHandler);
                    }catch(CameraAccessException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                }
            },mBackgroundHandler);

        }catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void createCameraPreview(){
        try{
            SurfaceTexture texture=textureView.getSurfaceTexture();
            assert texture!=null;
            texture.setDefaultBufferSize(imageDimension.getWidth(),imageDimension.getHeight());
            Surface surface =new Surface(texture);
            captureRequestBuilder=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if(cameraDevice==null)
                        return;
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();

                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                }
            },null);
        }catch(CameraAccessException e){
            e.printStackTrace();
        }



    }

    private void updatePreview(){
        if(cameraDevice==null)
            Toast.makeText(FaceScanActivity.this, "Error",Toast.LENGTH_SHORT).show();
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE,CaptureRequest.CONTROL_MODE_AUTO);
        try{
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(),null, mBackgroundHandler);
        }catch(CameraAccessException e){
            e.printStackTrace();
        }


    }

    private void openCamera(){
        CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            if (cameraId.equals(CAMERA_FRONT)) {
                cameraId=manager.getCameraIdList()[1];

            } else if (cameraId.equals(CAMERA_BACK)) {
                cameraId=manager.getCameraIdList()[0];
            }

            CameraCharacteristics characteristics=manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map!=null;
            imageDimension=map.getOutputSizes(SurfaceTexture.class)[0];
            //Check realtime permission if run higher API 23
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId,stateCallBack,null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CAMERA_PERMISSION){
            if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this,"You cant use camera without permission",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        if(textureView.isAvailable())
            openCamera();
        else
            textureView.setSurfaceTextureListener(textureListener);
    }

    @Override
    protected void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    private void stopBackgroundThread(){
        mBackgroundThread.quitSafely();
        try{
            mBackgroundThread.join();
            mBackgroundThread=null;
            mBackgroundHandler=null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread(){
        mBackgroundThread=new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler=new Handler(mBackgroundThread.getLooper());
    }

    public void switchCamera(View view) {
        if (cameraId.equals(CAMERA_FRONT)) {
            cameraId = CAMERA_BACK;
            closeCamera();
            openCamera();
            //otherCamera.setImageResource(R.drawable.ic_camera_front);

        } else if (cameraId.equals(CAMERA_BACK)) {
            cameraId = CAMERA_FRONT;
            closeCamera();
            openCamera();
            //switchCameraButton.setImageResource(R.drawable.ic_camera_back);
        }
    }

    /**public void reopenCamera() {
        if (textureView.isAvailable()) {
            openCamera(textureView.getWidth(), textureView.getHeight());
        } else {
            textureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }*/

    private void closeCamera() {
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

}
