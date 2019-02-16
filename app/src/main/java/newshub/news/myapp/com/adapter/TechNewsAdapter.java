package newshub.news.myapp.com.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import newshub.news.myapp.com.newshub.R;
import newshub.news.myapp.com.newshub.Sources;

/**
 * Created by Sweety on 13-02-2019.
 */

public class TechNewsAdapter extends RecyclerView.Adapter<TechNewsAdapter.ViewHolder> {

    private List<Sources> sourcesList ;
    private  Sources sources;

    private ViewHolder view ;
    public TechNewsAdapter(List<Sources> sourcesList) {
        this.sourcesList = sourcesList;
    }


    @NonNull
    @Override
    public TechNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.response_tech, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        sources = sourcesList.get(i);
        viewHolder.desc.setText(sources.getDescription());
    }



    @Override
    public int getItemCount() {
        return sourcesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.description);

        }
    }
}
