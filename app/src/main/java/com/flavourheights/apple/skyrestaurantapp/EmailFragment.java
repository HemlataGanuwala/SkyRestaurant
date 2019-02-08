package com.flavourheights.apple.skyrestaurantapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmailFragment extends Fragment {

    String path;
    EditText editTextemail;
    Button buttonsend;
    View view;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    private FirebaseAnalytics mFirebaseAnalytics;

    public EmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_email, container, false);

        editTextemail = (EditText)view.findViewById(R.id.etemail);
        buttonsend=(Button)view.findViewById(R.id.btnsend);

        auth=FirebaseAuth.getInstance();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        resetPassword();

        return view;
    }

    public void resetPassword(){

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email= editTextemail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog=new ProgressDialog(getActivity());
                progressDialog.setMessage("verifying..");
                progressDialog.show();

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "We have send you instruction to reset your password", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Failed to send reset email", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}
