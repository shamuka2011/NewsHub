package newshub.news.myapp.com.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import newshub.news.myapp.com.model.SportsModel;
import newshub.news.myapp.com.newshub.R;

/**
 * Created by Sweety on 14-02-2019.
 */

public class SportsNewsAdapter extends RecyclerView.Adapter<SportsNewsAdapter.ViewHolder> {


    private List<SportsModel> sportsModelList ;
    private  SportsModel sportsModel;
    public SportsNewsAdapter(List<SportsModel> sportsModelList) {
        this.sportsModelList = sportsModelList;
    }


    @NonNull
    @Override
    public SportsNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sportsadapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        sportsModel = sportsModelList.get(i) ;
        viewHolder.desc.setText(sportsModel.getDescription());
        viewHolder.url.setText(sportsModel.getUrl());
    }

    @Override
    public int getItemCount() {
        return sportsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc,url;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.sports_desc);
            url = itemView.findViewById(R.id.sports_url);
        }
    }
}
