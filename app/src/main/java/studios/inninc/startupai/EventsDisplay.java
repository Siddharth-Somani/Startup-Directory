package studios.inninc.startupai;


import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EventsDisplay extends AppCompatActivity {


    private RecyclerView recyclerView;
    private EventDisplayCardAdapter adapter;
    private List<EventContent> eventContentList;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    Integer thrd = 0;
    String cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_event);

        eventContentList = new ArrayList<>();
        adapter = new EventDisplayCardAdapter(this, eventContentList);

        // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new EventDisplayCardAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

        mProgress.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);

                }

                catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (thrd == 0) {
                            // Toast.makeText(Cse_activity.this, "Not able connect ", Toast.LENGTH_SHORT).show();
                            Toast msg = Toast.makeText(EventsDisplay.this, "We're Sorry! Please check your Internet connection and try again.", Toast.LENGTH_LONG);

                            msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);

                            msg.show();
                            mProgress.setVisibility(View.GONE);
                        }
                    }
                });

            }
        }).start();

        Bundle bundle = getIntent().getExtras();
        cat = bundle.getString("category");
        cat = cat.toLowerCase();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(cat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Notices notices = dataSnapshot.getValue(Notices.class);

                //Log.d("Child 1:",dataSnapshot.getKey());
                //Log.d("Child 1:",dataSnapshot.child("name").getValue().toString());

                String idN = dataSnapshot.getKey().toString();
                String likesN = dataSnapshot.child("likes").getValue(String.class);

                String nameN = dataSnapshot.child("name").getValue(String.class);
                String shortDescN = dataSnapshot.child("shortdesc").getValue(String.class);
                String timeN = dataSnapshot.child("time").getValue(String.class);
                String locN = dataSnapshot.child("loc").getValue(String.class);
                String imgN = dataSnapshot.child("image").getValue(String.class);
                String rating = dataSnapshot.child("rating").getValue(String.class);

                EventContent event = new EventContent();
                event.setEventId(idN);
                event.setEventImage(imgN);
                event.setEventName(nameN);
                event.setEventDate(shortDescN);
                event.setEventTime(timeN);
                event.setEventLoc(locN);
                event.setEventLikes(likesN);
                event.setEventCategory(cat);
                event.setRating(rating);

                eventContentList.add(event);
                adapter.notifyDataSetChanged();

                thrd = 1;

                mProgress.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

   public void setEventLikes(String eventID, String nLikes, String nCat){


        Integer n;

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mCat = mDatabase.child(nCat);

        DatabaseReference mEvent = mCat.child(eventID);

        DatabaseReference mLikes = mEvent.child("likes");

        n = Integer.parseInt(nLikes);
        n++;
        String l = n.toString();

        mEvent.child("likes").setValue(l);

        mLikes.setValue(n.toString());


    }


}
