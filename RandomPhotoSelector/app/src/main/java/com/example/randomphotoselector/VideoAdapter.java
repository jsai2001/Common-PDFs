package com.example.randomphotoselector;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final Context context;
    private final List<String> videoPaths;

    public VideoAdapter(Context context, List<String> videoPaths) {
        this.context = context;
        this.videoPaths = videoPaths;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("VideoAdapter", "onCreateViewHolder called");
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        String videoPath = videoPaths.get(position);
        Uri videoUri = Uri.parse(videoPath);
        Log.d("VideoAdapter", "onBindViewHolder called for position: " + position + ", videoPath: " + videoPath);
        holder.videoView.setVideoURI(videoUri);
        holder.videoView.setMediaController(new MediaController(context));
        holder.videoView.requestFocus();
        holder.videoView.setOnPreparedListener(mp -> {
            Log.d("VideoAdapter", "Video prepared: " + videoPath);
            holder.videoView.start();
        });
        holder.videoView.setOnErrorListener((mp, what, extra) -> {
            Log.e("VideoAdapter", "Error playing video: " + videoPath + ", what: " + what + ", extra: " + extra);
            return true;
        });
        holder.videoView.setOnCompletionListener(mp -> {
            Log.d("VideoAdapter", "Video completed: " + videoPath);
        });
    }

    @Override
    public int getItemCount() {
        Log.d("VideoAdapter", "getItemCount called, size: " + videoPaths.size());
        return videoPaths.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("VideoAdapter", "VideoViewHolder initialized");
            videoView = itemView.findViewById(R.id.videoView);
        }

        public void playVideo() {
            Log.d("VideoAdapter", "playVideo called");
            videoView.start();
        }

        public void pauseVideo() {
            Log.d("VideoAdapter", "pauseVideo called");
            videoView.pause();
        }
    }
}