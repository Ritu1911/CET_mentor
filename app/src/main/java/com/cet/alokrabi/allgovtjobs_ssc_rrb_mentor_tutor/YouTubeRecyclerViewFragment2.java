package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.Video;
import com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor.model.PlaylistVideos2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * <p/>
 * YouTubeRecyclerViewFragment2 contains a RecyclerView that shows a list of YouTube video cards.
 * <p/>
 */
public class YouTubeRecyclerViewFragment2 extends Fragment {
    // the fragment initialization parameter
    private static final String ARG_YOUTUBE_PLAYLIST_IDS = "YOUTUBE_PLAYLIST_IDS";
    private static final int SPINNER_ITEM_LAYOUT_ID = android.R.layout.simple_spinner_item;
    private static final int SPINNER_ITEM_DROPDOWN_LAYOUT_ID = android.R.layout.simple_spinner_dropdown_item;

    private String[] mPlaylistIds;
    private ArrayList<String> mPlaylistTitles;
    private RecyclerView mRecyclerView;
    private PlaylistVideos2 mPlaylistVideos2;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner mPlaylistSpinner;
    private PlaylistCardAdapter2 mPlaylistCardAdapter2
            ;
    private YouTube mYouTubeDataApi;
    private ProgressDialog mProgressDialog;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param youTubeDataApi
     * @param playlistIds A String array of YouTube Playlist IDs
     * @return A new instance of fragment YouTubeRecyclerViewFragment2.
     */
    public static YouTubeRecyclerViewFragment2 newInstance(YouTube youTubeDataApi, String[] playlistIds) {
        YouTubeRecyclerViewFragment2 fragment = new YouTubeRecyclerViewFragment2();
        Bundle args = new Bundle();
        args.putStringArray(ARG_YOUTUBE_PLAYLIST_IDS, playlistIds);
        fragment.setArguments(args);
        fragment.setYouTubeDataApi(youTubeDataApi);
        return fragment;
    }

    public YouTubeRecyclerViewFragment2() {
        // Required empty public constructor
    }

    public void setYouTubeDataApi(YouTube api) {
        mYouTubeDataApi = api;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mPlaylistIds = getArguments().getStringArray(ARG_YOUTUBE_PLAYLIST_IDS);
        }

        // start fetching the playlist titles
        new GetPlaylistTitlesAsyncTask(mYouTubeDataApi) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(PlaylistListResponse playlistListResponse) {
                // if we didn't receive a response for the playlist titles, then there's nothing to update
                if (playlistListResponse == null)
                    return;

                mPlaylistTitles = new ArrayList();
                for (com.google.api.services.youtube.model.Playlist playlist : playlistListResponse.getItems()) {
                    mPlaylistTitles.add(playlist.getSnippet().getTitle());
                }
                // update the spinner adapter with the titles of the playlists
                ArrayAdapter<List<String>> spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, mPlaylistTitles);
                spinnerAdapter.setDropDownViewResource(SPINNER_ITEM_DROPDOWN_LAYOUT_ID);
                mPlaylistSpinner.setAdapter(spinnerAdapter);

                //mProgressDialog.hide();
            }
        }.execute(mPlaylistIds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // set the Picasso debug indicator only for debug builds
        Picasso.with(getActivity()).setIndicatorsEnabled(BuildConfig.DEBUG);



        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.you_tube_recycler_view_fragment2, container, false);

        final ProgressBar pbar = (ProgressBar) rootView.findViewById(R.id.progressBar2); // Final so we can access it from the other thread
        pbar.setVisibility(View.VISIBLE);

// Create a Handler instance on the main thread
        final Handler handler = new Handler();

// Create and start a new Thread
        new Thread(new Runnable() {
            public void run() {
                try{
                    Thread.sleep(7000);
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

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.youtube_recycler_view2);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);





        Resources resources = getResources();
        if (resources.getBoolean(R.bool.isTablet)) {
            // use a staggered grid layout if we're on a large screen device
            mLayoutManager = new StaggeredGridLayoutManager(resources.getInteger(R.integer.columns), StaggeredGridLayoutManager.VERTICAL);
        } else {
            // use a linear layout on phone devices
            mLayoutManager = new LinearLayoutManager(getActivity());

        }

        mProgressDialog = new ProgressDialog(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);






        mPlaylistSpinner = (Spinner)rootView.findViewById(R.id.youtube_playlist_spinner);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // if we have a playlist in our retained fragment, use it to populate the UI
        if (mPlaylistVideos2 != null) {
            // reload the UI with the existing playlist.  No need to fetch it again
            reloadUi(mPlaylistVideos2, false);
        } else {
            // otherwise create an empty playlist using the first item in the playlist id's array
            mPlaylistVideos2 = new PlaylistVideos2(mPlaylistIds[0]);
            // and reload the UI with the selected playlist and kick off fetching the playlist content
            reloadUi(mPlaylistVideos2, true);
        }

        ArrayAdapter<List<String>> spinnerAdapter;
        // if we don't have the playlist titles yet
        if (mPlaylistTitles == null || mPlaylistTitles.isEmpty()) {
            // initialize the spinner with the playlist ID's so that there's something in the UI until the GetPlaylistTitlesAsyncTask finishes
            spinnerAdapter = new ArrayAdapter(getContext(), SPINNER_ITEM_LAYOUT_ID, Arrays.asList(mPlaylistIds));
        } else {
            // otherwise use the playlist titles for the spinner
            spinnerAdapter = new ArrayAdapter(getContext(), SPINNER_ITEM_LAYOUT_ID, mPlaylistTitles);
        }

        spinnerAdapter.setDropDownViewResource(SPINNER_ITEM_DROPDOWN_LAYOUT_ID);
        mPlaylistSpinner.setAdapter(spinnerAdapter);


        // set up the onItemSelectedListener for the spinner
        mPlaylistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // reload the UI with the playlist video list of the selected playlist
                mPlaylistVideos2 = new PlaylistVideos2(mPlaylistIds[position]);
                reloadUi(mPlaylistVideos2, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void reloadUi(final PlaylistVideos2 playlistVideos2, boolean fetchPlaylist) {
        // initialize the cards adapter
        initCardAdapter(playlistVideos2);

        if (fetchPlaylist) {
            // start fetching the selected playlistVideos2 contents
            new GetPlaylistAsyncTask(mYouTubeDataApi) {
                @Override
                public void onPostExecute(Pair<String, List<Video>> result) {
                    handleGetPlaylistResult(playlistVideos2, result);
                }
            }.execute(playlistVideos2.playlistId, playlistVideos2.getNextPageToken());
        }
    }

    private void initCardAdapter(final PlaylistVideos2 playlistVideos2) {
        // create the adapter with our playlistVideos2 and a callback to handle when we reached the last item
        mPlaylistCardAdapter2 = new PlaylistCardAdapter2(playlistVideos2, new LastItemReachedListener() {
            @Override
            public void onLastItem(int position, String nextPageToken) {
                new GetPlaylistAsyncTask(mYouTubeDataApi) {
                    @Override
                    public void onPostExecute(Pair<String, List<Video>> result) {
                        handleGetPlaylistResult(playlistVideos2, result);
                    }
                }.execute(playlistVideos2.playlistId, playlistVideos2.getNextPageToken());
            }
        });
        mRecyclerView.setAdapter(mPlaylistCardAdapter2);
    }

    private void handleGetPlaylistResult(PlaylistVideos2 playlistVideos2, Pair<String, List<Video>> result) {
        if (result == null) return;
        final int positionStart = playlistVideos2.size();
        playlistVideos2.setNextPageToken(result.first);
        playlistVideos2.addAll(result.second);
        mPlaylistCardAdapter2.notifyItemRangeInserted(positionStart, result.second.size());
    }


    /**
     * Interface used by the {@link PlaylistCardAdapter} to inform us that we reached the last item in the list.
     */
    public interface LastItemReachedListener {
        void onLastItem(int position, String nextPageToken);
    }
}
