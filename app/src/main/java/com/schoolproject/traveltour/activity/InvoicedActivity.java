package com.schoolproject.traveltour.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setHomeBackButtonAndToolbarTitle(getString(R.string.invoiced_list));

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        progressDialog.show();
        FirebaseDatabase.getInstance()
                .getReference(Constants.TABLE_NAME_BOOKING)
                .addValueEventListener(new ValueEventListener() {
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
                                        // TODO: 4/2/2020 go to detail
                                    }

                                    @Override
                                    public void onLongClick(Booking booking) {
                                        // TODO: 4/2/2020 delete
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
}
