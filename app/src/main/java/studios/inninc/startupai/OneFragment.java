package studios.inninc.startupai;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventsGridViewAdapter adapter;
    private List<EventCategories> albumList;

    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new EventsGridViewAdapter(getContext(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new EventsGridViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);

                Long l = adapter.getItemId(position);
                String cat = l.toString();
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
                intent.putExtra("category",cat);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d(TAG, "onItemLongClick pos = " + position);
            }
        });

        prepareAlbums();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] events = new int[]{
                R.drawable.grid_food,
                R.drawable.grid_travel,
                R.drawable.grid_ecommerce,
                R.drawable.grid_digitalwallet,
                R.drawable.grid_medical,
                R.drawable.grid_courier,
                R.drawable.grid_insurance,
                R.drawable.grid_software,
                R.drawable.grid_grocery


        };



        EventCategories a = new EventCategories("Food", events[0]);
        albumList.add(a);

        a = new EventCategories("Travel", events[1]);
        albumList.add(a);

        a = new EventCategories("E-Commerce", events[2]);
        albumList.add(a);

        a = new EventCategories("Digital Wallet", events[3]);
        albumList.add(a);

        a = new EventCategories("Medical", events[4]);
        albumList.add(a);

        a = new EventCategories("Courier", events[5]);
        albumList.add(a);

        a = new EventCategories("Insurance", events[6]);
        albumList.add(a);

        a = new EventCategories("Technology", events[7]);
        albumList.add(a);

        a = new EventCategories("Grocery", events[8]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }



    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
