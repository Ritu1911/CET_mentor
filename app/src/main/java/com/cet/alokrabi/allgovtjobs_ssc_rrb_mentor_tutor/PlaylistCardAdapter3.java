package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatistics;
import com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor.model.PlaylistVideos3;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

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
 * A RecyclerView.Adapter subclass which adapts {@link Video}'s to CardViews.
 */
public class PlaylistCardAdapter3 extends RecyclerView.Adapter<PlaylistCardAdapter3.ViewHolder> {

    private static final DecimalFormat sFormatter = new DecimalFormat("#,###,###");
    private final PlaylistVideos3 mPlaylistVideos3;
    private final YouTubeRecyclerViewFragment3.LastItemReachedListener mListener;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final TextView mTitleText;
        //  public final TextView mDescriptionText;
        public final ImageView mThumbnailImage;
        public final ImageView mShareIcon;
        public final TextView mShareText;
        public final TextView mDurationText;
        // public final TextView mViewCountText;
        public final TextView mLikeCountText;
        public final TextView mDislikeCountText;

        public ViewHolder(View v) {
            super(v);
            mContext = v.getContext();
            mTitleText = (TextView) v.findViewById(R.id.video_title3);
            //mDescriptionText = (TextView) v.findViewById(R.id.video_description);
            mThumbnailImage = (ImageView) v.findViewById(R.id.video_thumbnail3);
            mShareIcon = (ImageView) v.findViewById(R.id.video_share);
            mShareText = (TextView) v.findViewById(R.id.video_share_text);
            mDurationText = (TextView) v.findViewById(R.id.video_dutation_text3);
            // mViewCountText= (TextView) v.findViewById(R.id.video_view_count);
            mLikeCountText = (TextView) v.findViewById(R.id.video_like_count);
            mDislikeCountText = (TextView) v.findViewById(R.id.video_dislike_count);
        }
    }

    public PlaylistCardAdapter3(PlaylistVideos3 playlistVideos3, YouTubeRecyclerViewFragment3.LastItemReachedListener lastItemReachedListener) {
        mPlaylistVideos3 = playlistVideos3;
        mListener = lastItemReachedListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlaylistCardAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a card layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_video_card3, parent, false);
        // populate the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mPlaylistVideos3.size() == 0) {
            return;
        }

        final Video video = mPlaylistVideos3.get(position);
        final VideoSnippet videoSnippet = video.getSnippet();
        final VideoContentDetails videoContentDetails = video.getContentDetails();
        final VideoStatistics videoStatistics = video.getStatistics();

        holder.mTitleText.setText(videoSnippet.getTitle());
        holder.mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext() , YouTubeVideoPla.class);
                Bundle dataSend= new Bundle();
                String videoId = video.getId();
                Video pos =  mPlaylistVideos3.get(position);
                dataSend.putString("POS" , String.valueOf(pos));
                dataSend.putString("VIDEO", videoId);

                i.putExtras(dataSend);

                // i.putExtra("video.getId()" , String.valueOf(mPlaylistVideos.get(position)));

                v.getContext().startActivity(i);
            }
        });

        // holder.mDescriptionText.setText(videoSnippet.getDescription());

        // load the video thumbnail image
        Picasso.with(holder.mContext)
                .load(videoSnippet.getThumbnails().getHigh().getUrl())
                .placeholder(R.drawable.video_placeholder)
                .into(holder.mThumbnailImage);

        // set the click listener to play the video
        holder.mThumbnailImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext() , YouTubeVideoPla.class);
                Bundle dataSend= new Bundle();
                String videoId = video.getId();
                Video pos =  mPlaylistVideos3.get(position);
                dataSend.putString("POS" , String.valueOf(pos));
                dataSend.putString("VIDEO", videoId);

                i.putExtras(dataSend);

                // i.putExtra("video.getId()" , String.valueOf(mPlaylistVideos.get(position)));

                view.getContext().startActivity(i);
                // populate the viewholder

                // holder.mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + video.getId())));
            }
        });

        // create and set the click listener for both the share icon and share text
       /* View.OnClickListener shareClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch \"" + videoSnippet.getTitle() + "\" on YouTube");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + video.getId());
                sendIntent.setType("text/plain");
                holder.mContext.startActivity(sendIntent);
            }
        };
        holder.mShareIcon.setOnClickListener(shareClickListener);
        holder.mShareText.setOnClickListener(shareClickListener);*/

        // set the video duration text
        holder.mDurationText.setText(parseDuration(videoContentDetails.getDuration()));
        // set the video statistics
        //holder.mViewCountText.setText(sFormatter.format(videoStatistics.getViewCount()));
        // holder.mLikeCountText.setText(sFormatter.format(videoStatistics.getLikeCount()));
        // holder.mDislikeCountText.setText(sFormatter.format(videoStatistics.getDislikeCount()));

        if (mListener != null) {
            // get the next playlist page if we're at the end of the current page and we have another page to get
            final String nextPageToken = mPlaylistVideos3.getNextPageToken();
            if (!isEmpty(nextPageToken) && position == mPlaylistVideos3.size() - 1) {
                holder.itemView.post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onLastItem(position, nextPageToken);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPlaylistVideos3.size();
    }

    private boolean isEmpty(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        return false;
    }

    private String parseDuration(String in) {
        boolean hasSeconds = in.indexOf('S') > 0;
        boolean hasMinutes = in.indexOf('M') > 0;

        String s;
        if (hasSeconds) {
            s = in.substring(2, in.length() - 1);
        } else {
            s = in.substring(2, in.length());
        }

        String minutes = "0";
        String seconds = "00";

        if (hasMinutes && hasSeconds) {
            String[] split = s.split("M");
            minutes = split[0];
            seconds = split[1];
        } else if (hasMinutes) {
            minutes = s.substring(0, s.indexOf('M'));
        } else if (hasSeconds) {
            seconds = s;
        }

        // pad seconds with a 0 if less than 2 digits
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }

        return minutes + ":" + seconds;
    }
}
