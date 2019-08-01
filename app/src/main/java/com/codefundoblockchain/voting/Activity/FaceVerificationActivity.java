package com.codefundoblockchain.voting.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.codefundoblockchain.voting.APIModels.CreateFaceIDBodyModel;
import com.codefundoblockchain.voting.APIModels.CreateFaceIDModel;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.retrofit.FaceApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaceVerificationActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private Button getImage,verifyImage;
    private ImageView userImageView;
    private SessionManager sessionManager;
    private StorageReference imageRef;
    private DatabaseReference mdatabase;
    private ProgressDialog pd;
    private Bitmap imageBitmap;
    private ByteArrayOutputStream baos;
    private byte[] compressedData;
    private String profilePicFaceId=null;
    private String responseString=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_verification);


        getImage = findViewById(R.id.getImage);
        verifyImage = findViewById(R.id.verifyImage);
        userImageView = findViewById(R.id.userImageView);
        sessionManager = new SessionManager(this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://votzure.appspot.com");
        imageRef = storageRef.child(sessionManager.getUSER_ID()+"v");
        StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");
        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child("profile").child(sessionManager.getUSER_ID());

        pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.setCancelable(false);

        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchCamera();
            }
        });

        verifyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageBitmap!=null){
                    UploadToFirebase();
                }else{
                    Toast.makeText(FaceVerificationActivity.this, "Capture Image First", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private String CreateProfilePicFaceId1(String url){
        CreateFaceIDBodyModel body = new CreateFaceIDBodyModel();
        body.setUrl(url);
        Call<List<CreateFaceIDModel>> call = FaceApiClient.getClient().createFaceId(body);
        call.enqueue(new Callback<List<CreateFaceIDModel>>() {
            @Override
            public void onResponse(Call<List<CreateFaceIDModel>> call, Response<List<CreateFaceIDModel>> response) {
                if(response.code()==200){
                    responseString = response.body().get(0).getFaceId();
                    Log.e("faceID",responseString);
                    Toast.makeText(FaceVerificationActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }else{
                    if (response.code() == 400) {
                        if(!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                String internalMessage = jsonObject.getString("code");
                                Log.e("message",userMessage);
                                Log.e("code",internalMessage);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CreateFaceIDModel>> call, Throwable t) {
                Log.e("faceID",t.toString());
                Toast.makeText(FaceVerificationActivity.this, "Failed to get your profile ID", Toast.LENGTH_SHORT).show();
            }
        });

        return responseString;
    }

    private void UploadToFirebase() {
        pd.show();
        UploadTask uploadTask = imageRef.putBytes(compressedData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(FaceVerificationActivity.this, "Failed to Upload Picture. Try Again Later", Toast.LENGTH_SHORT).show();// Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                pd.dismiss();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.e("faceId",sessionManager.getPROFILE_PIC_LINK());
                CreateProfilePicFaceId1(sessionManager.getPROFILE_PIC_LINK());


            }
        });
    }

    private void LaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == this.RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            userImageView.setImageBitmap(imageBitmap);
            baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            compressedData = baos.toByteArray();
        }
    }
}
