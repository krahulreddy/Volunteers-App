package com.selab.volunteer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gallery extends AppCompatActivity {

    private RecyclerView recyclerView;

    private DatabaseReference databaseReference, dr1;
    private StorageReference storageReference;

    private ImageAdapter imageAdapter;

    private List<String> imgUploads;
    private List<String> imgNames;
    private List<ImgUpload> images;

    private Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        upload = findViewById(R.id.uploadPhoto);
        final EditText name = findViewById(R.id.event_name);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter Event Name!", Toast.LENGTH_SHORT).show();
                    name.setError("Please fill!");

                }
                else
                    ChooseImage();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imgUploads = new ArrayList<>();
        imgNames = new ArrayList<>();
        images = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");


//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                images.clear();
//                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
//                    images.add(postSnapshot.getValue(ImgUpload.class));
//                    imgUploads.add(postSnapshot.getKey());
//                    ImgUpload imgUpload = new ImgUpload(postSnapshot.getKey(), Objects.requireNonNull(postSnapshot.getValue(String.class)));
//                    images.add(imgUpload);
//                }
//                imageAdapter = new ImageAdapter(getApplicationContext(), images);
//                recyclerView.setAdapter(imageAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }
    private void ChooseImage(){

//        Intent intent = new Intent(getApplicationContext(), ImgUpload.class);
//        startActivity(intent);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            //Toast.makeText(getApplicationContext(), data.getData().toString(), Toast.LENGTH_LONG).show();
            final Uri mImageUri = data.getData();
            final ImgUpload[] imgUpload = new ImgUpload[1];
            final EditText eventName = findViewById(R.id.event_name);
            final String name = eventName.getText().toString();
            final String uploadName = name + "_" + System.currentTimeMillis() + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(mImageUri));
            final StorageReference sr = storageReference.child(uploadName);
            sr.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            ImgUpload imgUpload1 = new ImgUpload(name, uri.toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(imgUpload1).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                            Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Select Again", Toast.LENGTH_LONG).show();
        }
    }
}
