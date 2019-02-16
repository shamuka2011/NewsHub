package newshub.news.myapp.com.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import newshub.news.myapp.com.model.EntertainmentModel;
import newshub.news.myapp.com.newshub.R;

/**
 * Created by Sweety on 16-02-2019.
 */

public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.ViewHolder> {
    public EntertainmentAdapter(List<EntertainmentModel> modelList) {
        this.modelList = modelList;
    }

    private List<EntertainmentModel> modelList;
    private EntertainmentModel model;

    @NonNull
    @Override
    public EntertainmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entertainmentadapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        model = modelList.get(i);
        viewHolder.desc.setText(model.getDescription());
        viewHolder.url.setText(model.getUrl());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc,url;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.enter_desc);
            url = itemView.findViewById(R.id.enter_url);
        }
    }
}
