package com.tracies.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tracies.R;
import com.tracies.ShopFragment;
import com.tracies.model.VediosModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VediosAdapter extends RecyclerView.Adapter<VediosAdapterViewHolder> {



    ArrayList<VediosModel> video_data ;



    public VediosAdapter(ArrayList<VediosModel> video_data, FragmentActivity activity) {
        this.video_data = video_data;
    }

    @NonNull
    @Override
    public VediosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.videoitem_activity,parent,false);
        return new VediosAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VediosAdapterViewHolder holder, int position) {
        VediosModel vediosModel = video_data.get(position);


        Log.e("vedioTitle",""+vediosModel.getVideoTitle());

        Glide.with(ShopFragment.context).load(vediosModel.getThumbnailUrl()).into(holder.ThumbNail);
        holder.videoTitle.setText(vediosModel.getVideoTitle());
        long time=Long.parseLong(vediosModel.getVideoID());
        String timeAgo = TimeAgo.getTimeAgo(time);
        holder.videoDate.setText(""+timeAgo);

        holder.Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(vediosModel.getVideoUrl()), "video/mp4");
                ShopFragment.context.startActivity(intent);

            }
        });
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    @Override
    public int getItemCount() {
        return (video_data == null) ? 0 : video_data.size();
    }



}

class VediosAdapterViewHolder extends RecyclerView.ViewHolder {

    ImageView ThumbNail,Play;
    TextView videoTitle, videoDate;
    public VediosAdapterViewHolder(@NonNull View itemView) {
        super(itemView);

        ThumbNail = (ImageView) itemView.findViewById(R.id.ThumbNail);
        videoTitle = (TextView) itemView.findViewById(R.id.videoTitle);
        videoDate = (TextView) itemView.findViewById(R.id.videoDate);
        Play = (ImageView) itemView.findViewById(R.id.Play);


    }
}
class TimeAgo {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }


        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}
