package studios.inninc.startupai;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class EventsGridViewAdapter extends RecyclerView.Adapter<EventsGridViewAdapter.MyViewHolder> {
    private static ClickListener clickListener;
    private Context mContext;
    private List<EventCategories> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
        @Override
        public void onClick(View v) {
            //clickListener.onItemClick(getAdapterPosition(), v);

            Long cat = getItemId();
            Log.e("cat =", cat.toString());

            Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
            intent.putExtra("category", cat);
            //startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        EventsGridViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public EventsGridViewAdapter(Context mContext, List<EventCategories> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_startup, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final EventCategories album = albumList.get(position);
        holder.title.setText(album.getEventName());
        holder.thumbnail.setImageResource(album.getThumbnail());

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        //Setting up onClick()
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Log.d("Test","onclick here" + position);

            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cat = album.getEventName();

                if(cat.equals("Food")){
                    cat = "food";
                }else if(cat.equals("Travel")){
                    cat = "travel";
                }else if(cat.equals("E-Commerce")){
                    cat = "ecommerce";
                }else if(cat.equals("Digital Wallet")){
                    cat = "digitalwallet";
                }else if(cat.equals("Medical")){
                    cat = "medical";
                }
                Log.d("cat =",cat);

                Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                intent.putExtra("category", cat);
                mContext.startActivity(intent);

            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cat = album.getEventName();

                if(cat.equals("Food")){
                    cat = "food";
                }else if(cat.equals("Travel")){
                    cat = "travel";
                }else if(cat.equals("E-Commerce")){
                    cat = "ecommerce";
                }else if(cat.equals("Digital Wallet")){
                    cat = "digitalwallet";
                }else if(cat.equals("Medical")){
                    cat = "medical";
                }
                Log.d("cat =",cat);

                Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                intent.putExtra("category", cat);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


}