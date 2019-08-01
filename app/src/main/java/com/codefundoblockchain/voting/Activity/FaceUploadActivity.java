package com.codefundoblockchain.voting.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FaceUploadActivity extends AppCompatActivity {

    Button getImage,uploadImage;
    ImageView userImageView;
    private int REQUEST_IMAGE_CAPTURE = 111;
    private Bitmap imageBitmap;
    private ByteArrayOutputStream baos;
    private byte[] compressedData;
    private StorageReference mountainsRef;
    private SessionManager sessionManager;
    private DatabaseReference mdatabase;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_upload);


        getImage = findViewById(R.id.getImage);
        uploadImage = findViewById(R.id.uploadImage);
        userImageView = findViewById(R.id.userImageView);
        sessionManager = new SessionManager(this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://votzure.appspot.com");
        mountainsRef = storageRef.child(sessionManager.getUSER_ID());
        StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");
        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child("profile").child(sessionManager.getUSER_ID());
        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchCamera();
            }
        });

        pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.setCancelable(false);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageBitmap!=null){
                    UploadToFirebase();
                }else{
                    Toast.makeText(FaceUploadActivity.this, "Capture Image First", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void UploadToFirebase() {
        pd.show();
        UploadTask uploadTask = mountainsRef.putBytes(compressedData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(FaceUploadActivity.this, "Failed to Upload Picture. Try Again Later", Toast.LENGTH_SHORT).show();// Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                pd.dismiss();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                HashMap<String,Object> map = new HashMap<>();
                map.put("profilePic",downloadUrl.toString());
                mdatabase.updateChildren(map);
                Toast.makeText(FaceUploadActivity.this, "Profile Pic Successfully added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FaceUploadActivity.this,Registration_Successful_activity.class);
                startActivity(intent);
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
