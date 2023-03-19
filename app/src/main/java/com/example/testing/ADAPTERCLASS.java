package com.example.testing;

import static com.example.testing.R.layout.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testing.databinding.ListItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ADAPTERCLASS extends FirebaseRecyclerAdapter<MODELCLASS,ADAPTERCLASS.ViewHolder> {

    public ADAPTERCLASS(@NonNull FirebaseRecyclerOptions<MODELCLASS> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MODELCLASS model) {

        holder.binding.textName.setText(model.key1);
        holder.binding.textEmail.setText(model.key2);

        Glide.with(holder.itemView.getContext()).load(model.getImagekey())
                .into(holder.binding.textimage);

        holder.binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
final DialogPlus dialogPlus=DialogPlus.newDialog(holder.binding.textimage.getContext())
        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialogbox))
                .setExpanded(true,600)
                .create();
                View myview=dialogPlus.getHolderView();
                final EditText imagekey=myview.findViewById(R.id.uimgurl);
                final EditText key1=myview.findViewById(R.id.uname);
                final EditText key2=myview.findViewById(R.id.uemail);
                Button submit=myview.findViewById(R.id.usubmit);

                imagekey.setText(model.getImagekey());
                key1.setText(model.getKey1());
                key2.setText(model.getKey2());

                dialogPlus.show();

submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Map<String,Object> map=new HashMap<>();
        map.put("imagekey",imagekey.getText().toString());
        map.put("key1",key1.getText().toString());
        map.put(" key2",key2.getText().toString());


        FirebaseDatabase.getInstance().getReference()
                .child("School").child(getRef(position).getKey())
                .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dialogPlus.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialogPlus.dismiss();
            }
        });

    }
});
            }
        });

        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(holder.binding.textimage.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("School")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

            builder.show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(list_item,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ListItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding= ListItemBinding.bind(itemView);


        }
    }

}
