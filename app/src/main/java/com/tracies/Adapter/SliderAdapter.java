package com.tracies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tracies.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> mSliderItems = new ArrayList<>();

    public SliderAdapter(ArrayList<String> mSliderItems, Context context) {
        this.mSliderItems = mSliderItems;
        this.context = context;
    }

    public void renewItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        String sliderItem = mSliderItems.get(position);

//        if(position == 0)
//            viewHolder.previousImageView.setVisibility(View.GONE);
//        else
//            viewHolder.previousImageView.setVisibility(View.VISIBLE);
//
//
//        Log.e("positionCHeck", "Outisde: " + position + ", " + (mSliderItems.size() - 1));
//        if(mSliderItems.size() == 1 || position == mSliderItems.size() - 1)
//            viewHolder.nextImageView.setVisibility(View.GONE);
//        else
//            viewHolder.nextImageView.setVisibility(View.VISIBLE);


//        viewHolder.previousImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewHolder.nextImageView.setVisibility(View.VISIBLE);
//                int tempPosition = position - 1 ;
//                if(tempPosition >= 0) {
//                    ItemView.rcv.setCurrentPagePosition(tempPosition);
//                    Log.e("positionCHeck", "Inside: " + tempPosition + ", " + (mSliderItems.size() - 1));
//                }
//                if (position == 0){
//                    viewHolder.nextImageView.setVisibility(View.GONE);
//                    notifyDataSetChanged();
//                }
//
//            }
//        });
//
//        viewHolder.nextImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("pos", String.valueOf(position));
//                viewHolder.previousImageView.setVisibility(View.VISIBLE);
//                int tempPosition = position + 1;
//                if(tempPosition + 1 < mSliderItems.size()) {
//                    Log.e("posicheck", "inside " + tempPosition + "," + (mSliderItems.size()));
//                    ItemView.rcv.setCurrentPagePosition(tempPosition);
//                }
//                if(tempPosition >= mSliderItems.size() )
//                    viewHolder.previousImageView.setVisibility(View.GONE);
//                else
//                    viewHolder.previousImageView.setVisibility(View.VISIBLE);
//
//
//            }
//        });

        Glide.with(viewHolder.itemView)
                .load(sliderItem)
                .into(viewHolder.imageView);
        //viewHolder.imageCount.setText(position + 1 +"/"+ mSliderItems.size());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageView;
        ImageView nextImageView;
        ImageView previousImageView;
        TextView imageCount;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_show);
            nextImageView = (ImageView) itemView.findViewById(R.id.next);
            previousImageView = (ImageView) itemView.findViewById(R.id.previous);
            imageCount = (TextView) itemView.findViewById(R.id.imageCount);

            this.itemView = itemView;
        }
    }
}