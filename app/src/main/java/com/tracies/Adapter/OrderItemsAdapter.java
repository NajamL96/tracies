package com.tracies.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tracies.R;
import com.tracies.model.Cart_Model;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter {

    ArrayList<Cart_Model> data;
    Context context;


    public OrderItemsAdapter(ArrayList<Cart_Model> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("zhkAAZ",""+ data.get(position).getSize());
        if (data.get(position).getSize() != null){
            return 1;
        }else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == 1) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.order_items, parent, false);
            return new OrderItemsViewHolder(view);
        }else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.wholesale_order, parent, false);
            return new WholeSaleViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cart_Model myordermodel = data.get(position);
        if (data.get(position).getSize() != null) {
            OrderItemsViewHolder orderItemsViewHolder = (OrderItemsViewHolder) holder;

            Glide.with(context).load(myordermodel.getImage()).into(orderItemsViewHolder.shoe);
            //Log.e("ModelCheck12345", "" + myordermodel.getImage());

            orderItemsViewHolder.t1.setText(myordermodel.getTitle());
            orderItemsViewHolder.t2.setText("$" + myordermodel.getPrice());
            orderItemsViewHolder.t3.setText(""+myordermodel.getTotalitem());
            orderItemsViewHolder.t4.setText(myordermodel.getSize());



        }else {
            OrderItemsAdapter.WholeSaleViewHolder wholeSaleViewHolder = (WholeSaleViewHolder) holder;
            wholeSaleViewHolder.b1.setText(myordermodel.getTitle());
            wholeSaleViewHolder.b2.setText("$"+myordermodel.getPrice());
            wholeSaleViewHolder.smallVlaue.setText(""+myordermodel.getSmall());
            wholeSaleViewHolder.mediumValu.setText(""+myordermodel.getMedium());
            wholeSaleViewHolder.largeValue.setText(""+myordermodel.getLarge());
            wholeSaleViewHolder.extraLargeValue.setText(""+myordermodel.getExtraLarge());

            Glide.with(context).load(myordermodel.getImage()).into(wholeSaleViewHolder.WimageView);
        }
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }


    public class OrderItemsViewHolder extends RecyclerView.ViewHolder{

        ImageView shoe;
        TextView t1,t2,t3,t4;
        public OrderItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            t1=(TextView) itemView.findViewById(R.id.order_title);
            t2=(TextView) itemView.findViewById(R.id.cart_price);
            shoe =(ImageView) itemView.findViewById(R.id.shoe);
            t3=(TextView) itemView.findViewById(R.id.cart_quantity);
            t4 = (TextView) itemView.findViewById(R.id.size);
//        t4=(TextView) itemView.findViewById(R.id.totalPrice);
        }
    }


    public class WholeSaleViewHolder extends RecyclerView.ViewHolder {
        ImageView WimageView,Wgreentick, Wgreytick,Wdel;
        TextView b1,b2,smallVlaue,mediumValu,largeValue,extraLargeValue;
        LinearLayout Wcart_item;
        public WholeSaleViewHolder(@NonNull View itemView) {
            super(itemView);

            WimageView = (ImageView) itemView.findViewById(R.id.Wshoe);
            Wcart_item = (LinearLayout) itemView.findViewById(R.id.Wcart_item);
//            Wgreentick = (ImageView) itemView.findViewById(R.id.WgreenTick);
//            Wgreytick = (ImageView) itemView.findViewById(R.id.WgreyTick);
            b1 = (TextView) itemView.findViewById(R.id.Wcart_title);
            b2 = (TextView) itemView.findViewById(R.id.Wcart_price);
            smallVlaue = (TextView) itemView.findViewById(R.id.smallValue);
            mediumValu = (TextView) itemView.findViewById(R.id.mediumValue);
            largeValue = (TextView) itemView.findViewById(R.id.largeValue);
            extraLargeValue = (TextView) itemView.findViewById(R.id.extraLargeValue);
//            Wdel = (ImageView) itemView.findViewById(R.id.Wdel);

        }
    }
}


