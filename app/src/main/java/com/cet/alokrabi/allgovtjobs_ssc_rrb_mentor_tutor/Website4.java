package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;


import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor.Common.Common;

import static android.content.Context.DOWNLOAD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Website4 extends Fragment {

    WebView view;

    ProgressBar mProgress;
    View myFragment;

    private final String googleDocs = "https://docs.google.com/viewer?url=";


    public Website4() {
        // Required empty public constructor
    }

    public static Website4 newInstance() {
        Website4 fragment = new Website4();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_website4, container, false);

        mProgress = (ProgressBar)myFragment.findViewById(R.id.mProgress);
        String url = "https://www.sscadda.com/";
        view = (WebView) myFragment.findViewById(R.id.webview);
        ((MainActivity)getActivity()).tabLayout.setVisibility(View.GONE);

        // Inflate the layout for this fragment

        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            // mDialog1.show();
            WebSettings webSettings = view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            view.getSettings().setBuiltInZoomControls(true);
            view.getSettings().setLoadWithOverviewMode(true);
            view.getSettings().setUseWideViewPort(true);
            view.getSettings().setDisplayZoomControls(false);
            view.loadUrl(url);
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                        if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                            if(view!=null)
                            {
                                if(view.canGoBack())
                                {
                                    view.goBack();
                                }
                            }
                        }
                    }
                    return true;
                }
            });
            view.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    mProgress.setVisibility(View.VISIBLE);
                    Log.i("progressBar", "Visible");
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    Log.i("pageFinished", "yesss");
                    mProgress.setVisibility(View.INVISIBLE);
                    Log.i("progressBar", "Gone");

                }
                @Override
                public void onPageCommitVisible(WebView view, String url) {

                    super.onPageCommitVisible(view, url);
                    mProgress.setVisibility(View.INVISIBLE);
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.endsWith(".pdf")){
                        String pdfUrl = googleDocs + url;
                        view.loadUrl(pdfUrl);
                    } else {
                        view.loadUrl(url);
                    }
                    return true;
                }
            });
            view.setDownloadListener(new DownloadListener() {

                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {
                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));
                    String fileName = url.substring(url.lastIndexOf('/') + 1);

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getActivity().getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                            Toast.LENGTH_LONG).show();

                }
            });

           /*
            mDialog1.getMax();
            view.getSettings().setJavaScriptEnabled(true);
            view.loadUrl(url);*/


        } else
        {
            Intent intent = new Intent(getActivity(), NoInternet.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Please check your internet connection!!", Toast.LENGTH_SHORT).show();

        }

        return myFragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        //totalQuestion = Common.mockExamQuestionList.size();
       /* mCountDowm = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long milisec) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }
            @Override
            public void onFinish() {
                mCountDowm.cancel();
                showQuestion(++index);
            }
        };*/
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (view.canGoBack()){
                            view.goBack();
                        }else {
                            ((MainActivity) getActivity()).toolbar.setTitle(R.string.app_name);
                            ((MainActivity)getActivity()).tabLayout.setVisibility(View.VISIBLE);
                            Fragment startFragment = new HomeFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fr_con, startFragment);
                            //viewPagerAdapter.notifyDataSetChanged();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.addToBackStack(null);
                            transaction.commit();                            //super.onBackPressed();
                        }
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
