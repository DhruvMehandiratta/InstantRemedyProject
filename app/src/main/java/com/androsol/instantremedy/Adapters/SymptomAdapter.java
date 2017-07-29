package com.androsol.instantremedy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androsol.instantremedy.R;
import com.androsol.instantremedy.Activities.RemedyActivity;
import com.androsol.instantremedy.Models.Symptom;

import java.util.ArrayList;

/**
 * Created by Dhruv on 18-07-2017.
 */

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.MyViewHolder> {

    Context ctx;
    private ArrayList<Symptom> symptoms;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView symptomName;
        ArrayList<Symptom> symptoms = new ArrayList<Symptom>();
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<Symptom> symptoms) {
            super(itemView);
            itemView.setOnClickListener(this);
            symptomName = (TextView) itemView.findViewById(R.id.symptom_name);
            this.symptoms = symptoms;
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Symptom s = symptoms.get(position);
            Intent i = new Intent(this.context, RemedyActivity.class);
            i.putExtra("SymptomId", s.getId());
            i.putExtra("OtherSymptoms",s.getOtherSymps());
            i.putExtra("Description",s.getDescription());
            i.putExtra("Remedy",s.getRemedy());
            this.context.startActivity(i);
        }
    }
    public SymptomAdapter(ArrayList<Symptom> symptoms, Context ctx){
        this.ctx = ctx;
        this.symptoms = symptoms;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_item,parent,false);
        return new MyViewHolder(itemView,ctx,symptoms);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Symptom s = symptoms.get(position);
        holder.symptomName.setText(s.getName());
    }

    @Override
    public int getItemCount() {
        return symptoms.size();
    }
}
