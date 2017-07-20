package com.androsol.instantremedy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androsol.instantremedy.BodyPart;
import com.androsol.instantremedy.ProblemsActivity;
import com.androsol.instantremedy.R;

import java.util.ArrayList;

/**
 * Created by Dhruv on 18-07-2017.
 */

public class BodyPartAdapter extends RecyclerView.Adapter<BodyPartAdapter.MyViewHolder> {

    Context ctx;
    private ArrayList<BodyPart> bodyParts;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView bodyPartName;
        ArrayList<BodyPart> bodyParts = new ArrayList<BodyPart>();
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<BodyPart> bodyParts) {
            super(itemView);
            itemView.setOnClickListener(this);
            bodyPartName = (TextView) itemView.findViewById(R.id.body_part_name);
            this.bodyParts = bodyParts;
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            BodyPart bp = bodyParts.get(position);
            Intent i = new Intent(this.context, ProblemsActivity.class);
            i.putExtra("BodyPartName",bp.getName());
            i.putExtra("BodyPartId", bp.getId());
            this.context.startActivity(i);
        }
    }
    public BodyPartAdapter(ArrayList<BodyPart> bps, Context ctx){
        this.ctx = ctx;
        this.bodyParts = bps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.body_part_item,parent,false);
            return new MyViewHolder(itemView,ctx,bodyParts);
    }

    @Override
    public void onBindViewHolder(BodyPartAdapter.MyViewHolder holder, int position) {
        BodyPart bp = bodyParts.get(position);
        holder.bodyPartName.setText(bp.getName());
//        if(position % 2 == 0){
//            holder.itemView.setBackgroundColor(Color.parseColor("#E77471"));
//        }else{
//            holder.itemView.setBackgroundColor(Color.parseColor("#F75D59"));
//        }
        //holder.itemView.setBackgroundResource(position % 2 == 0? R.color.red1 : R.color.red2);
    }

    @Override
    public int getItemCount() {
        return bodyParts.size();
    }
}
