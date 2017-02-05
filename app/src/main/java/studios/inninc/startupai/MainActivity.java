package studios.inninc.startupai;

import android.app.Activity;
import android.app.Dialog;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
import static android.system.Os.remove;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    EditText editText;
    CollapsingToolbarLayout ctl;
    final Context context=this;
    ImageView imageView;

    private final int SPEECH_RECOGNITION_CODE = 1;
    private EditText txtOutput;
    private ImageButton btnMicrophone;

    Activity activity = new Activity();

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ctl=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ctl.setTitleEnabled(false);
        ctl.setContentScrimColor(getResources().getColor(R.color.colorPrimary));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        editText=(EditText)findViewById(R.id.text);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final ImageView mic = (ImageView)promptsView.findViewById(R.id.imageMic);

                txtOutput = (EditText)promptsView.findViewById(R.id.searchtext);

                mic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startSpeechToText();
                    }
                });

                alertDialogBuilder.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String text = txtOutput.getText().toString();
                        if(text.contains("food")){
                            str = "food";
                            Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                            intent.putExtra("category",str);
                            startActivity(intent);

                        }else if(text.contains("travel")){
                            str = "travel";
                            Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                            intent.putExtra("category",str);
                            startActivity(intent);

                        } else if(text.contains("ecommerce")){
                            str = "ecommerce";
                            Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                            intent.putExtra("category",str);
                            startActivity(intent);

                        } else if(text.contains("digitalwallet")){
                            str = "digitalwallet";
                            Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                            intent.putExtra("category",str);
                            startActivity(intent);

                        } else if(text.contains("medical")){
                            str = "medical";
                            Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                            intent.putExtra("category",str);
                            startActivity(intent);
                        }
                        else {
                            txtOutput.setText(text);

                            Toast.makeText(getApplicationContext(),
                                    "Sorry! We are not able to recognize your search!",
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                });
                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }

        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * Start speech to text intent. This opens up Google Speech Recognition API dialog box to listen the speech input.
     * */
    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Speech recognition is not supported in this device.",
                            Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
    /**
     * Callback for speech recognition activity
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    if(text.contains("food")){
                        str = "food";
                        Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                        intent.putExtra("category",str);
                        startActivity(intent);

                    }else if(text.contains("travel")){
                        str = "travel";
                        Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                        intent.putExtra("category",str);
                        startActivity(intent);

                    } else if(text.contains("ecommerce")){
                        str = "ecommerce";
                        Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                        intent.putExtra("category",str);
                        startActivity(intent);

                    } else if(text.contains("digitalwallet")){
                        str = "digitalwallet";
                        Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                        intent.putExtra("category",str);
                        startActivity(intent);

                    } else if(text.contains("medical")){
                        str = "medical";
                        Intent intent = new Intent("studios.inninc.startupai.EventsDisplay");
                        intent.putExtra("category",str);
                        startActivity(intent);
                    }
                    else {
                        txtOutput.setText(result.get(0));

                        Toast.makeText(getApplicationContext(),
                                "Sorry! We are not able to recognize your search!",
                                Toast.LENGTH_LONG).show();
                    }
                }

                break;
            }
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Categories");
        adapter.addFragment(new TwoFragment(), "Trending");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_profile) {
            // Handle the camera action

            Intent intent = new Intent("pradyumna.eventsea.Profile");
            startActivity(intent);

        } else if (id == R.id.nav_upload) {

            Intent intent = new Intent("pradyumna.eventsea.Upload");
            startActivity(intent);

        } else if (id == R.id.nav_join) {

            Intent intent = new Intent("pradyumna.eventsea.JoinUs");
            startActivity(intent);

        } else if (id == R.id.nav_marketing) {

            Intent intent = new Intent("pradyumna.eventsea.EventMarketing");
            startActivity(intent);

        } else if (id == R.id.nav_invite) {

            Intent intent = new Intent("pradyumna.eventsea.Invite");
            startActivity(intent);

        }else if (id == R.id.nav_chat) {

            Intent intent = new Intent("pradyumna.eventsea.Chat");
            startActivity(intent);

        }else if (id == R.id.nav_rate) {

            final String appPackageName = getPackageName(); // package name of the app
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }
}