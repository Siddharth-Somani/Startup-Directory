package studios.inninc.startupai;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EventDisplayCardAdapter extends RecyclerView.Adapter<EventDisplayCardAdapter.MyViewHolder> {
    private static ClickListener clickListener;
    private Context mContext;
    private List<EventContent> eventList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView eventname;
        public TextView eventdate;
        public TextView eventtime;
        public TextView eventloc;
        public ImageView eventimg;

        public ImageView likeBtn;
        public ImageView shareBtn;
        public ImageView favouriteBtn;

        public TextView interestedPeople;

        public RatingBar rb;
        public TextView rating;

        public MyViewHolder(View view) {
            super(view);
            eventname = (TextView) view.findViewById(R.id.event_nameC);
            eventdate=(TextView)view.findViewById(R.id.eventDetDate);
            eventtime=(TextView)view.findViewById(R.id.eventDetTime);
            eventloc=(TextView)view.findViewById(R.id.eventDetLoc);
            eventimg=(ImageView)view.findViewById(R.id.event_imgC);


            likeBtn = (ImageView)view.findViewById(R.id.likeEventBtn);
            shareBtn = (ImageView)view.findViewById(R.id.shareBtn);
            favouriteBtn = (ImageView)view.findViewById(R.id.favouriteEventBtn);

            interestedPeople=(TextView)view.findViewById(R.id.interestedPeople);

            rb = (RatingBar)view.findViewById(R.id.ratingBar);
            rating = (TextView)view.findViewById(R.id.ratingValue);

        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        EventDisplayCardAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public EventDisplayCardAdapter(Context mContext, List<EventContent> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_event, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final EventContent eventContent = eventList.get(position);

        holder.eventname.setText(eventContent.getEventName());
        holder.eventdate.setText(eventContent.getEventDate());
        holder.eventtime.setText(eventContent.getEventTime());
        holder.eventloc.setText(eventContent.getEventLoc());

        holder.interestedPeople.setText(eventContent.getEventLikes() + " people following");

        holder.rb.setRating(Float.parseFloat(eventContent.getRating()));
        holder.rating.setText(eventContent.getRating().toString()+"/5");

        LikeDBHandler db = new LikeDBHandler(mContext);
        Like like2 = db.getLike(eventContent.getEventId());

        if(like2==null){
            holder.likeBtn.setImageResource(R.drawable.like_button_small1);
        }
        else if(like2.get_liked()=="true") {
            holder.likeBtn.setImageResource(R.drawable.like_button_small1);
        }
        else {
            holder.likeBtn.setImageResource(R.drawable.like_button_small2);
        }

        Picasso.with(mContext)
                .load(eventContent.getEventImage())
                .fit()
                .placeholder(R.drawable.placeholder_3_small)
                    /*.override(500, 200)*/
                .into(holder.eventimg);

        // loading album cover using Glide library
        //Glide.with(mContext).load(eventContent.getEventImage()).into(holder.eventimg);

        //Setting up onClick()
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("Test", "onclick here" + position);

            }
        });

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsDisplay obj = new EventsDisplay();
                obj.setEventLikes(eventContent.getEventId(), eventContent.getEventLikes(), eventContent.getEventCategory());
                holder.likeBtn.setImageResource(R.drawable.like_button_small2);

                Like like = new Like(eventContent.getEventId(),"true");
                LikeDBHandler db = new LikeDBHandler(mContext);
                db.addLike(like);
            }
        });

        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String linkT = "https://play.google.com/store/apps/details?id=pradyumna.eventsea";

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT,
                        "Startup.ai App");
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey,\nCheck out this Startup:\n\n"+eventContent.getEventName()+"\nTime:"+eventContent.getEventTime()+"\n\nFor more information Download the Startup.ai App at:\n"+linkT);

                mContext.startActivity(sendIntent);
            }
        });

        holder.favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.favouriteBtn.setImageResource(R.drawable.favourite_small_3);

                Favourites fav = new Favourites(eventContent.getEventId(),eventContent.getEventCategory());
                FavouritesDBHandler db = new FavouritesDBHandler(mContext);
                db.addFavourites(fav);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


}
