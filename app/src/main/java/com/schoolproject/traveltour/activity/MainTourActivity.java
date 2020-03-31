package com.schoolproject.traveltour.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.WishList;
import com.schoolproject.traveltour.utils.Constants;

public abstract class MainTourActivity extends BaseSecondActivity {
    boolean bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        if (!SignInActivity.isAdmin) {
            Button btnBookNow = findViewById(R.id.btn_book_now);
            btnBookNow.setVisibility(View.VISIBLE);
            btnBookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToBookingActivity();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_wishlist) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser == null) {
                Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
                return false;
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(Constants.TABLE_NAME_WISH_LIST)
                    .child(currentUser.getUid());

            bookMark(new BookMarkCallback() {
                @Override
                public void saveOrRemoveBookmark(WishList wishList) {
                    if (bookmark) {
                        myRef.child(wishList.getTourId())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            changeIcon(item);
                                        } else {
                                            showFailToRemoveToast();
                                        }
                                    }
                                });
                    } else {
                        myRef.child(wishList.getTourId())
                                .setValue(wishList)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            changeIcon(item);
                                        } else {
                                            showFailToSaveToast();
                                        }
                                    }
                                });
                    }
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeIcon(MenuItem item) {
        bookmark = !bookmark;

        item.setIcon(getResources().getDrawable(bookmark
                ? R.drawable.ic_bookmark_white_24dp
                : R.drawable.ic_bookmark_border_white_24dp));

        Toast.makeText(MainTourActivity.this,
                bookmark
                        ? "Added to wishlist"
                        : "Removed from wishlist",
                Toast.LENGTH_SHORT).show();
    }

    public com.schoolproject.traveltour.model.Menu getParamTourOrFinishActivity() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey(TourListActivity.PARAM_TOUR)) {
            showErrorToastAndFinishActivity();
            return null;
        }

        com.schoolproject.traveltour.model.Menu tour =
                (com.schoolproject.traveltour.model.Menu) bundle.get(TourListActivity.PARAM_TOUR);
        if (tour == null) {
            showErrorToastAndFinishActivity();
            return null;
        }

        return tour;
    }

    private void showErrorToastAndFinishActivity() {
        Toast.makeText(this, "Nothing to show!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public interface BookMarkCallback {
        void saveOrRemoveBookmark(WishList wishList);
    }

    void showFailToSaveToast() {
        Toast.makeText(this,
                "Fail to bookmark! Try again!",
                Toast.LENGTH_SHORT).show();
    }

    void showFailToRemoveToast() {
        Toast.makeText(this,
                "Fail to remove from bookmark! Try again!",
                Toast.LENGTH_SHORT).show();
    }

    abstract void goToBookingActivity();

    abstract void bookMark(BookMarkCallback callback);
}
