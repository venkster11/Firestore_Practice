package com.venkat.firestore_test.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.venkat.firestore_test.Main2Activity;
import com.venkat.firestore_test.MainActivity;
import com.venkat.firestore_test.Note;
import com.venkat.firestore_test.R;
import com.venkat.firestore_test.UIAuthfire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "Title";   //key to the data in firestore
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewData;
    private Button btn_mult_doc;
    private Button btn_signin;
    private String id= "123abc45";
    private Button save;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef=db.collection("Notebook1").document("My First Note");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        editTextTitle = (EditText) getView().findViewById(R.id.edit_text_title);
        editTextDescription =(EditText) getView().findViewById(R.id.edit_text_description);
        textViewData = (TextView)getView().findViewById(R.id.text_view_data);
        btn_mult_doc= (Button) getView().findViewById(R.id.btnmult);
        btn_signin=(Button) getView().findViewById(R.id.btn_gsign);
        save=(Button) getView().findViewById(R.id.savebtn);

        btn_mult_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Main2Activity.class);
                startActivity(intent);

            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), UIAuthfire.class);
                startActivity(intent1);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();


     /*   Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, title);  // key value mapping
        note.put(KEY_DESCRIPTION, description);*/    //non custom method

                Note note=new Note(title,description);  //custom java method
                //here u can also use noteRef.set(note);
                db.collection(id).document().set(note) //adding document my first note book to collection notebook1.
                        //here set is used to add data to firestore
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Note saved", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
            }
        });

        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        DocumentReference noteRef1=db.collection(id).document("2nd note");
        noteRef1.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(getActivity(), "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }

                if (documentSnapshot.exists()) {
                /*    String title = documentSnapshot.getString(KEY_TITLE);
                    String description = documentSnapshot.getString(KEY_DESCRIPTION);
                    textViewData.setText("Title: " + title + "\n" + "Description: " + description);*/ //non custom method

                    Note note = documentSnapshot.toObject(Note.class);
                    String title = note.getTitle();
                    String description = note.getDescription();
                    textViewData.setText("Title: " + title + "\n" + "Description: " + description); //custom method

                } else {
                    textViewData.setText("");}
            }
        });
    }


}
