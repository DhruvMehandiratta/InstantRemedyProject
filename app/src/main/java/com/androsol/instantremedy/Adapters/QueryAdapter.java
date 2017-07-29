package com.androsol.instantremedy.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androsol.instantremedy.Activities.SubmitRemedyActivity;
import com.androsol.instantremedy.Constants;
import com.androsol.instantremedy.Models.Query;
import com.androsol.instantremedy.R;

import java.util.ArrayList;

/**
 * Created by Dhruv on 21-07-2017.
 */

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.MyViewHolder> {
    Context ctx;
    ArrayList<Query> queries;
    String emailId = "";
    Boolean admin = false;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView uniqueSymptomTextView, otherSymptomTextView, optionalTextView, displayBodyName;
        public Button answerButton;
        ArrayList<Query> queries = new ArrayList<Query>();
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<Query> queries) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.context = context;
            this.queries = queries;
            uniqueSymptomTextView = (TextView) itemView.findViewById(R.id.display_query_name);
            displayBodyName = (TextView) itemView.findViewById(R.id.display_body_name);
            otherSymptomTextView = (TextView) itemView.findViewById(R.id.display_query_other_symptoms);
            optionalTextView = (TextView) itemView.findViewById(R.id.display_optional_info);
            answerButton = (Button) itemView.findViewById(R.id.display_answer_button);
            answerButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Query q = queries.get(position);
            if(view.getId() != R.id.display_answer_button){
                return;
            }
            //check if the user is admin
            String[] adminArray = Constants.adminArray;
            for(String x : adminArray){
                if(x.equals(emailId)){
                    admin = true;
                    break;
                }
            }
            if(!admin){
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage("Only health Experts can provide some remedy!" +
                        "We are sorry, but you are not authorized for the task!")
                        .setPositiveButton("I got it!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();
                return;
            }

            Intent i = new Intent(this.context, SubmitRemedyActivity.class);
            i.putExtra("QuerySymptom",q.getUniqueName());
            i.putExtra("QueryOther",q.getOtherSymotoms());
            i.putExtra("QueryBodyId",q.getBodypartId());
            i.putExtra("QueryOptional",q.getOptionalText());
            this.context.startActivity(i);
        }
    }
    public QueryAdapter(ArrayList<Query> queries, Context ctx, String emailId){
        this.ctx = ctx;
        this.queries = queries;
        this.emailId = emailId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_list_item, parent, false);
        return new MyViewHolder(itemView,ctx,queries);
    }


    @Override
    public void onBindViewHolder(QueryAdapter.MyViewHolder holder, int position) {
        Query q = queries.get(position);
        holder.uniqueSymptomTextView.setText(q.getUniqueName());
        holder.otherSymptomTextView.setText(q.getOtherSymotoms());
        holder.optionalTextView.setText(q.getOptionalText());
        holder.displayBodyName.setText(q.getBodypartName());
    }

    @Override
    public int getItemCount() {
        return queries.size();
    }
}
