package newshub.news.myapp.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import newshub.news.myapp.com.model.ScienceModel;
import newshub.news.myapp.com.newshub.Article;
import newshub.news.myapp.com.newshub.R;
import newshub.news.myapp.com.newshub.ScrollingActivity;

/**
 * Created by Sweety on 16-02-2019.
 */

public class ScienceAdapter  extends RecyclerView.Adapter<ScienceAdapter.ViewHolder>{
    public ScienceAdapter(List<Article> scienceModelList) {
        this.scienceModelList = scienceModelList;
    }

    private List<Article> scienceModelList;
    private Context context;




    @NonNull
    @Override
    public ScienceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.response_top,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Article model = scienceModelList.get(i);
        viewHolder.title.setText(model.getTitle());
        Picasso.with(context).load(scienceModelList.get(i).getUrlToImage()).resize(600, 400).error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return scienceModelList.size();
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
                    intent.putExtra("desc",scienceModelList.get(position).getDescription());
                    intent.putExtra("urltoimage",scienceModelList.get(position).getUrlToImage());
                    intent.putExtra("url",scienceModelList.get(position).getUrl());
                    intent.putExtra("content",scienceModelList.get(position).getContent());
                    intent.putExtra("title",scienceModelList.get(position).getTitle());

                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
