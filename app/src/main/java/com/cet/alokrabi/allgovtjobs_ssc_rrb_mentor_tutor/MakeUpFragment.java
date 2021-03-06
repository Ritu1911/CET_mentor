package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;


/**
 * A simple {@link Fragment} subclass.
 */
public class MakeUpFragment extends Fragment {

    View myFragment1;
    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    private InterstitialAd interstitialAd;
    String url = "https://www.youtube.com/user/androiddevelopers/playlists";
    private static final String[] YOUTUBE_PLAYLISTS1 = {
            //"PLAwxTw4SYaPnMwH5-FNkErnnq_aSy706S",
            "PLOwEFL8-OcSeVKmbirXjHn3kWK3lggh84",
            "PLOwEFL8-OcSfZPwKwy16616wyI8DybVDH",
            "PLOwEFL8-OcSdijP2GVxn8kdm08nA5m4w0",
            "PLOwEFL8-OcSfd4PzxlCkedlxaBBiExo59",
            "PLOwEFL8-OcScca8gq3EDrKZJxK7Q_U-Ef",
            "PLOwEFL8-OcSevIqbd3QOLeA7YbuIDPzH8",
            "PLOwEFL8-OcSe2wjXEQX5onRkv4W61aJ6x",
            "PLOwEFL8-OcSc56INZ2J1FmNK9UiTZRz4x",
            "PLOwEFL8-OcScmRA8vPuRz46x6eH9YoEK_",
            "PLOwEFL8-OcScFwPbqaezl9aEgL3gN4VIF",
            "PLOwEFL8-OcSe81aKAFZk-o2yb8tHL19Qp",
            "PLOwEFL8-OcScFRc9NSvKjWCZYRZCxGSgT",
            "PLOwEFL8-OcSc6HG6XZ0HNW1J2EJ1lxd-A",
            "PLOwEFL8-OcSfOOqJtujiJ_4IB-xX564SQ",
            "PLOwEFL8-OcSef0Bnqgow1NiA1ZBRey0pW",
            "PLOwEFL8-OcScL8rnMOUDLbHST69YypLNf",
            "PLOwEFL8-OcScbz3Ro_ly2Y6VI8qWw_vmV",
            "PLOwEFL8-OcSfuIHypGQRWVejEck8GDCgZ",
            "PLOwEFL8-OcSdL7w9j3mLgZI9mTm20XWi2",
            "PLOwEFL8-OcSegNvP8kdzx62Gmz41VBf60",
            "PLOwEFL8-OcSdU8alZFjUu-GZNuvexMDrs"





    };
    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();

    public MakeUpFragment() {

        // Required empty public constructor
    }
    public static MakeUpFragment newInstance() {
        MakeUpFragment MakeUpFragment = new MakeUpFragment();
        return MakeUpFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container4, Bundle savedInstanceState) {
        myFragment1 = inflater.inflate(R.layout.make_up_fragment, container4, false);
        final ProgressBar pbar = (ProgressBar) myFragment1.findViewById(R.id.progressBar2); // Final so we can access it from the other thread
        pbar.setVisibility(View.VISIBLE);

// Create a Handler instance on the main thread
        final Handler handler = new Handler();

// Create and start a new Thread
        new Thread(new Runnable() {
            public void run() {
                try{
                    Thread.sleep(10000);
                }
                catch (Exception e) { } // Just catch the InterruptedException

                // Now we use the Handler to post back to the main thread
                handler.post(new Runnable() {
                    public void run() {
                        // Set the View's visibility back on the main UI Thread
                        pbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();

        /*interstitialAd = new InterstitialAd(getActivity());
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                startActivity(new Intent(getActivity(),MainActivity.class));
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });*/

        if(!isConnected()){
            Toast.makeText(getActivity(),"No Internet Connection Detected", Toast.LENGTH_LONG).show();
        }

        if (ApiKey.YOUTUBE_API_KEY.startsWith("YOUR_API_KEY")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setMessage("Edit ApiKey.java and replace \"YOUR_API_KEY\" with your Applications Browser API Key")
                    .setTitle("Missing API Key")
                    .setNeutralButton("Ok, I got it!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finish();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (savedInstanceState == null) {
            mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                    .setApplicationName(getResources().getString(R.string.app_name))
                    .build();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container4, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS1))
                    .commit();
        }

        return myFragment1;

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.you_tube, menu);
        return true;
    }*/

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_recyclerview) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container4, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS1))
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        ((MainActivity) getActivity()).viewPager.setCurrentItem(0);

                      /* if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        }else {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }*/
                        // Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });
        //showQuestion(index);
    }


}



    /*webView = (VideoEnabledWebView)myFragment2.findViewById(R.id.webView);

        View nonVideoLayout = myFragment2.findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) myFragment2.findViewById(R.id.videoLayout); // Your own view, read class comments
        //noinspection all
        //View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null);

        // Your own view, read class comments
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // Your code...
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen) {
                    WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getActivity().getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                } else {
                    WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getActivity().getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            webView.setWebChromeClient(webChromeClient);
            // Call private class InsideWebViewClient
            webView.setWebViewClient(new InsideWebViewClient());

            // Navigate anywhere you want, but consider that this classes have only been tested on YouTube's mobile site
            webView.loadUrl(url);
        } else {
            Intent intent10 = new Intent(getActivity(),NoInternet.class);
            startActivity(intent10);
            Toast.makeText(getActivity(), "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
        }



        return myFragment2;
    }
    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

    }*/
