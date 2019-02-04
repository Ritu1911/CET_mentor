package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.GoogleApiClient;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    private AdView mAdView;
    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    private InterstitialAd interstitialAd;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    private TextView nameTextView , emailTextView , idTextView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    // private View navHeader;
    private GoogleApiClient googleApiClient;
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    public static int navItemIndex = 0;
    //FrameLayout fragment_container;
    View background_view;
    BroadcastReceiver mRegBroadRec;
    Button btnSignUp, btnSignIn;

    private CircleImageView photoImageView;
    private Switch myswitch;
    SharePref sharePref;
    boolean doubleBackToExitPressedOnce = false;

    private int[] tabIcons = {
            R.drawable.ic_home_black_24dp,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharePref = new SharePref(this);
        //header = navigationView.getHeaderView(0);


        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            }


            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
            editor.commit();


        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }


        if (sharePref.loadNightModeState()==true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitleTextColor();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        toolbar.setTitle(R.string.app_name);
                        break;

                    case 1:
                        viewPager.setCurrentItem(1);
                        toolbar.setTitle("Quantitative Aptitude");
                        break;

                    case 2:
                        viewPager.setCurrentItem(2);
                        toolbar.setTitle("General Knowledge");
                        break;

                    case 3:
                        viewPager.setCurrentItem(3);
                        toolbar.setTitle("Logical Reasoning");
                        break;

                    case 4:
                        viewPager.setCurrentItem(4);
                        toolbar.setTitle("General English");
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

                viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new HomeFragment(), "");
        viewPagerAdapter.addFragments(new MakeUpFragment(), "Quantitative\nAptitude");
        viewPagerAdapter.addFragments(new VideoFragment(), "General\nKnowledge");
        viewPagerAdapter.addFragments(new MehendiFragment(), "Logical\nReasoning");
                viewPagerAdapter.addFragments(new NailArts(), "General\nEnglish");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // navigationView.setNavigationItemSelectedListener( MainActivity.this);
        View header = navigationView.getHeaderView(0);
        nameTextView = (TextView) header.findViewById(R.id.nameTextView);
        photoImageView = (CircleImageView) header.findViewById(R.id.photoImageView);
        myswitch = (Switch)header.findViewById(R.id.myswitch);

        //icon

        if (sharePref.loadNightModeState()==true){
            toolbar.setBackgroundColor(Color.parseColor("#015d8e"));
            toolbar.setTitleTextColor(Color.WHITE);
            navigationView.setBackgroundColor(Color.parseColor("#212121"));
            myswitch.setText("Disable Night Mode");

            navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
            navigationView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));

            tabLayout.setBackgroundColor(Color.parseColor("#015d8e"));
            tabLayout.setTabTextColors(getResources().getColor(R.color.nightMode),
                    getResources().getColor(R.color.colorPrimaryTextLight));
            toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);



        }else {
            toolbar.setBackgroundColor(Color.WHITE);
            toolbar.setTitleTextColor(Color.BLACK);
            tabLayout.setBackgroundColor(Color.WHITE);
            tabLayout.setTabTextColors(getResources().getColor(R.color.dayMode),
                    getResources().getColor(R.color.colorPrimaryTextDark));
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.dayMode));
            navigationView.setBackgroundColor(Color.WHITE);
            myswitch.setText("Enable Night Mode");
            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        }
        if (sharePref.loadNightModeState()==true){
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        }

        if (sharePref.loadNightModeState()==true) {
            myswitch.setChecked(true);
        }
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharePref.setNightModeState(true);
                                        restartApp();
                }else {
                    sharePref.setNightModeState(false);

                    restartApp();
                }
            }
        });

        setUpNavigationView();
    }

    private void restartApp() {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();

    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
    }
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Handle navigation view item clicks here.
                switch (menuItem.getItemId()) {
                    case R.id.nav_1:
                        toolbar.setTitle("QMath");
                        viewPager.setCurrentItem(0);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fr_con, Website1.newInstance())
                                .commit();
                        break;
                    case R.id.nav_2:
                        toolbar.setTitle("Knowledge Philic");
                        viewPager.setCurrentItem(0);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fr_con, Website2.newInstance())
                                .commit();
                        break;
                    case R.id.nav_3:
                        viewPager.setCurrentItem(0);
                        toolbar.setTitle("GradeUp");
                        fragmentManager.beginTransaction()
                                .replace(R.id.fr_con, Website3.newInstance())
                                .commit();
                        break;
                    case R.id.nav_4:
                        viewPager.setCurrentItem(0);
                        toolbar.setTitle("SSC Adda");
                        fragmentManager.beginTransaction()
                                .replace(R.id.fr_con, Website4.newInstance())
                                .commit();

                        break;
                    case R.id.nav_5:
                        viewPager.setCurrentItem(0);
                        toolbar.setTitle("KV Classes");
                        fragmentManager.beginTransaction()
                                .replace(R.id.fr_con, Website5.newInstance())
                                .commit();
                        break;
                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_share:
                        Intent i = new Intent(
                                android.content.Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(
                                android.content.Intent.EXTRA_TEXT, "I am preparing for my Government job exams using the CET Mentor mobile app \n" +
                                        "Download it by clicking\n" +
                                        "https://play.google.com/store/apps/details?id=com.cet.alokrabi.cet_ssc_rrb_mentor_tutor");
                        startActivity(Intent.createChooser(
                                i,
                                "Share Via"));
                        break;
                    case R.id.nav_rate:
                /*Intent intent1 = new Intent(MainActivity.this,RateApp.class);
                startActivity(intent1);*/
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor")));
                        break;
                    case R.id.nav_pri:
                        Intent intent4 = new Intent(MainActivity.this,PrivacyPolicy.class);
                        startActivity(intent4);
                        break;



                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();


                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        // Toast.makeText(getBaseContext(), "We got the Storage Permission", Toast.LENGTH_LONG).show();
    }
}


