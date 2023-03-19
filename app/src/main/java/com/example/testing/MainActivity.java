package com.example.testing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.testing.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
int maxid;
Uri uri;
String ImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });

/*
        String rollnumber=binding.etRollno.getText().toString();
*/

        FirebaseDatabase.getInstance().getReference().child("School").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

     maxid =(int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       binding.buttonsend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               FirebaseStorage.getInstance().getReference().child("My Folder").child(String.valueOf(maxid+1)).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                       while (!task.isComplete());
                       ImageUrl=task.getResult().toString();

                       HashMap<String,Object> map=new HashMap<>();
                       map.put("key1",binding.etname.getText().toString());
                       map.put("key2",binding.etemail.getText().toString());
        /*
               map.put("key3",binding.rollno.getText().toString());
*/
                       map.put("imagekey",ImageUrl);

                       FirebaseDatabase.getInstance().getReference().child("School").child(String.valueOf(maxid+1)).setValue(map);
                       Toast.makeText(MainActivity.this, "Succeed", Toast.LENGTH_SHORT).show();

Intent intent=new Intent(MainActivity.this,MainActivity2.class);
startActivity(intent);
                   }
               });



           }
       });

        binding.move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);

            }
        });
/*binding.buttonFetch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String key=binding.etRollno.getText().toString();

      FirebaseDatabase.getInstance().getReference().child("School").child(key).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {

              Map<String,Object> fetchMap=(Map) snapshot.getValue();
if (snapshot.exists()){

String name=(String) fetchMap.get("key1");
String email=(String) fetchMap.get("key2");

String roll_no=(String) fetchMap.get("key3");

String imageurl=(String) fetchMap.get("imagekey") ;

binding.folder.setImageURI(uri);
binding.txtName.setText(name);
binding.txtEmail.setText(email);

binding.txtRoll.setText(roll_no);

Glide.with(MainActivity.this).load(imageurl).into(binding.folder);

}else {
    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
}
          }
          @Override
          public void onCancelled(@NonNull DatabaseError error) {
          }
      });
    }
});*/
      /*  binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=binding.etRollno.getText().toString();


                HashMap<String,Object> upMap=new HashMap<>();
                upMap.put("key1",binding.etname.getText().toString());
                upMap.put("key2",binding.etemail.getText().toString());
*//*
                upMap.put("key3",binding.etfname.getText().toString());
*//*

                FirebaseDatabase.getInstance().getReference().child("School").child(key).updateChildren(upMap);
            }
        });*/

      /*  binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    FirebaseDatabase.getInstance().getReference().child("School").removeValue();

                binding.txtName.setText("");
                binding.txtEmail.setText("");
*//*
                binding.txtFname.setText("");
*//*
            }
        });
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){

            uri=data.getData();
                    binding.folder.setImageURI(data.getData());

        }
    }
}
