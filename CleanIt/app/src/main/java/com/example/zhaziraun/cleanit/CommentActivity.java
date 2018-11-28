package com.example.zhaziraun.cleanit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.CommonDataSource;

public class CommentActivity extends AppCompatActivity {

    EditText comment;
    Button postComment;
    ListView commentList;
    int companyId;
    int commentNumber;
    CommentAdapter adapter;
    ImageView back;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<CommentDataHeper> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        commentList = findViewById(R.id.commentList);
        comment = findViewById(R.id.commentEdit);
        postComment = findViewById(R.id.addComment);
        back = findViewById(R.id.back);
        final Intent intent = getIntent();
        companyId = intent.getIntExtra("id", -1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyId != -1){
                    if (!comment.getText().toString().equals("")){
                        CommentDataHeper commentDataHeper = new CommentDataHeper(mAuth.getCurrentUser().getUid(), companyId, comment.getText().toString());
                        databaseReference.child("comments").child(commentNumber + "").setValue(commentDataHeper);
                        adapter.notifyDataSetChanged();
                        comments.clear();
                        comment.setText("");
                    }else{
                        Toast.makeText(CommentActivity.this, "Enter some text into the edit text", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CommentActivity.this, "Error occured try later", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

        databaseReference.child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++){
                    if (dataSnapshot.child("" + i).getValue(CommentDataHeper.class).companyId == intent.getIntExtra("id", 0)){
                        comments.add(dataSnapshot.child("" + i).getValue(CommentDataHeper.class));
                    }
                }
                commentNumber = (int) dataSnapshot.getChildrenCount();
                adapter = new CommentAdapter(comments, CommentActivity.this);
                commentList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
