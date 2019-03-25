package newshub.news.myapp.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import newshub.news.myapp.com.newshub.Article;
import newshub.news.myapp.com.newshub.R;
import newshub.news.myapp.com.newshub.ScrollingActivity;
import newshub.news.myapp.com.newshub.Sources;

/**
 * Created by Sweety on 13-02-2019.
 */

public class TechNewsAdapter extends RecyclerView.Adapter<TechNewsAdapter.ViewHolder> {

    private List<Article> sourcesList ;
    private Context context;

    private ViewHolder view ;
    public TechNewsAdapter(List<Article> sourcesList) {
        this.sourcesList = sourcesList;
    }


    @NonNull
    @Override
    public TechNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.response_top, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Article model = sourcesList.get(i);
        viewHolder.title.setText(model.getTitle());
        Picasso.with(context).load(sourcesList.get(i).getUrlToImage()).resize(600, 400).error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(viewHolder.imageView);

    }



    @Override
    public int getItemCount() {
        return sourcesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.res_title);
            imageView = itemView.findViewById(R.id.imageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    int position = getAdapterPosition();

                    intent = new Intent(itemView.getContext(),ScrollingActivity.class);
                    intent.putExtra("desc",sourcesList.get(position).getDescription());
                    intent.putExtra("urltoimage",sourcesList.get(position).getUrlToImage());
                    intent.putExtra("url",sourcesList.get(position).getUrl());
                    intent.putExtra("content",sourcesList.get(position).getContent());
                    intent.putExtra("title",sourcesList.get(position).getTitle());

                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
