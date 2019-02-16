package newshub.news.myapp.com.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import newshub.news.myapp.com.model.GeneralModel;
import newshub.news.myapp.com.newshub.R;

/**
 * Created by Sweety on 15-02-2019.
 */

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {

    public GeneralAdapter(List<GeneralModel> generalModelList) {
        this.generalModelList = generalModelList;
    }

    private List<GeneralModel> generalModelList;
    private GeneralModel model;

    @NonNull
    @Override
    public GeneralAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generaladapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        model = generalModelList.get(i);
        viewHolder.desc.setText(model.getDescription());
        viewHolder.url.setText(model.getUrl());
    }

    @Override
    public int getItemCount() {
        return generalModelList.size();
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
