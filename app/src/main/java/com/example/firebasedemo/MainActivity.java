package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton createNotesFab;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
   // FirestoreRecyclerAdapter<Notes,NotesViewHolder> notesAdapter;
    Query query;
    List<Notes> notes1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_notes);
        createNotesFab = findViewById(R.id.createNoteFab);
        recyclerView = findViewById(R.id.recycler_notes);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        createNotesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CreateNotesActivity.class));
            }
        });

        firebaseFirestore
                .collection("notes")
                .document(firebaseUser.getUid())
                .collection("mynotes")
                .orderBy("title",Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Notes notes = document.toObject(Notes.class);
                                notes1.add(notes);
                            }
                            NotesAdapter notesAdapter = new NotesAdapter(MainActivity.this,notes1);
                            recyclerView.setAdapter(notesAdapter);
                        }
                    }
                })
        ;

    /*   FirestoreRecyclerOptions<Notes> allusernotes = new FirestoreRecyclerOptions.Builder<Notes>().setQuery(query,Notes.class).build();
        notesAdapter = new FirestoreRecyclerAdapter<Notes, NotesViewHolder>(allusernotes) {


            @Override
            protected void onBindViewHolder(@NonNull NotesViewHolder holder, int position, @NonNull Notes notes) {
                holder.Title.setText(notes.getTitle());
                holder.Content.setText(notes.getContent());
            }

            @NonNull
            @Override
            public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_notesview,parent,false);
                return new NotesViewHolder(view);
            }
        };
        recyclerView.setAdapter(notesAdapter);*/

    }

     /*public class NotesViewHolder extends RecyclerView.ViewHolder{

        private TextView Title,Content;
         public NotesViewHolder(@NonNull View itemView) {
             super(itemView);
             Title = itemView.findViewById(R.id.tv_title);
             Content = itemView.findViewById(R.id.tv_content);
         }
     }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected  void onStart() {

        super.onStart();
       /* notesAdapter.startListening();
        notesAdapter.notifyDataSetChanged();*/
    }

    @Override
    protected  void onStop() {
        super.onStop();
       /* if(notesAdapter!= null) {
            notesAdapter.startListening();
            notesAdapter.notifyDataSetChanged();
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}