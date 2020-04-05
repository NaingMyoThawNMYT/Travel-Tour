package com.schoolproject.traveltour.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.InvoicedAdapter;
import com.schoolproject.traveltour.model.Booking;
import com.schoolproject.traveltour.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoicedActivity extends BaseSecondActivity {
    private DatabaseReference myRef;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setHomeBackButtonAndToolbarTitle(getString(R.string.invoiced_list));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        myRef = FirebaseDatabase.getInstance()
                .getReference(Constants.TABLE_NAME_BOOKING);

        progressDialog.show();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                final List<Booking> invoicedList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Booking invoiced = new Booking();
                    invoiced.parse((Map<String, Object>) snapshot.getValue());
                    invoicedList.add(invoiced);
                }

                RecyclerView rv = findViewById(R.id.rv);
                InvoicedAdapter adapter = new InvoicedAdapter(invoicedList,
                        new InvoicedAdapter.OnClickListener() {
                            @Override
                            public void onClick(Booking booking) {
                                showInvoiceDetails(booking);
                            }

                            @Override
                            public void onLongClick(Booking booking) {
                                showDeleteConfirmDialog(InvoicedActivity.this, booking);
                            }
                        });
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    private void showInvoiceDetails(Booking booking) {
        Intent i = new Intent(this, InvoiceActivity.class);
        i.putExtra(InvoiceActivity.PARAM_BOOKING, booking);
        startActivity(i);
    }

    private void showDeleteConfirmDialog(Context context, final Booking booking) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?")
                .setMessage("Delete invoice")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteInvoiced(booking);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void deleteInvoiced(Booking booking) {
        progressDialog.show();
        myRef.child(booking.getId())
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(InvoicedActivity.this,
                                    "Deleted successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(InvoicedActivity.this,
                                    "Fail to delete",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
