package newshub.news.myapp.com.newshub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sweety on 07-02-2019.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private Context context;
    private ImageView imageView;

    public List<Article> getArticleArrayList() {
        return articleArrayList;
    }

    public void setArticleArrayList(List<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
    }

    private List<Article> articleArrayList;
    private TopViewHolder view;
    Article articleModel ;
    private boolean isLoadingAdded = false;
    // private View view;
    public NewsAdapter(List<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
    }


    public NewsAdapter(Context context) {
        this.context = context;
        this.articleArrayList = new ArrayList<>();
    }
    //private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        /*View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.response_top, viewGroup, false);*/
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(viewGroup, layoutInflater);
                break;
            case LOADING:
                View view1 = layoutInflater.inflate(R.layout.item_progress,viewGroup,false);
                viewHolder = new LoadingVH(view1);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        articleModel = articleArrayList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final TopViewHolder topViewHolder = (TopViewHolder) viewHolder;
                topViewHolder.title.setText(articleModel.getTitle());
                //  topViewHolder.desc.setText(articleModel.getDescription());
                // topViewHolder.author.setText("by "+articleModel.getAuthor());
                // topViewHolder.publishat.setText(articleModel.getPublishedAt());
                // String imageUrl = articleModel.getUrlToImage();
                String imageUrl = articleArrayList.get(position).getUrlToImage();



                //  Picasso.with(context).load(imageUrl).resize(600, 400).fit().error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(view.imageView);
                Picasso.with(context).load(imageUrl).resize(1000, 400).error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(topViewHolder.imageView);

                break;
            case LOADING:
                //do nothing
                break;
        }
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.response_top, parent, false);
        viewHolder = new TopViewHolder(v1);
        return viewHolder;
    }

   /* @Override
    public void onBindViewHolder(@NonNull TopViewHolder topViewHolder, int position) {
        view = topViewHolder;
        articleModel = articleArrayList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                topViewHolder.title.setText(articleModel.getTitle());
                //  topViewHolder.desc.setText(articleModel.getDescription());
                // topViewHolder.author.setText("by "+articleModel.getAuthor());
                // topViewHolder.publishat.setText(articleModel.getPublishedAt());
                // String imageUrl = articleModel.getUrlToImage();
                String imageUrl = articleArrayList.get(position).getUrlToImage();



                //  Picasso.with(context).load(imageUrl).resize(600, 400).fit().error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(view.imageView);
                Picasso.with(context).load(imageUrl).resize(600, 400).error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(view.imageView);

                break;
            case LOADING:
                //do nothing
                break;
        }


    }*/



    @Override
    public int getItemCount() {
        return articleArrayList == null ?0:articleArrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return (position == articleArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    //Helper methods
    public void add(Article r) {
        articleArrayList.add(r);
        notifyItemInserted(articleArrayList.size() - 1);
    }

    public void addAll(List<Article> moveResults) {
        for (Article result : moveResults) {
            add(result);
        }
    }

    public void remove(Article r) {
        int position = articleArrayList.indexOf(r);
        if (position > -1) {
            articleArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Article());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = articleArrayList.size() - 1;
        Article result = getItem(position);

        if (result != null) {
            articleArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Article getItem(int position) {
        return articleArrayList.get(position);
    }



    //ViewHolder classes
    public class TopViewHolder extends RecyclerView.ViewHolder {
        private TextView title,desc,publishat,author;
        public  ImageView imageView;
        private Context mContext;
        private LinearLayout artilceAdapterParentLinear;
        private TopViewHolder(@NonNull final  View view) {
            super(view);
            mContext = itemView.getContext();
            title = view.findViewById(R.id.res_title);
            // desc = view.findViewById(R.id.res_desc);
            // author = view.findViewById(R.id.res_author);
            //  publishat = view.findViewById(R.id.res_publishAt);
            imageView = view.findViewById(R.id.imageview);


            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final Intent intent ;
                    int position = getAdapterPosition();

                    intent = new Intent(mContext,ScrollingActivity.class);
                    intent.putExtra("parcel_data",articleModel);
                    intent.putExtra("desc",articleArrayList.get(position).getDescription());
                    intent.putExtra("urltoimage",articleArrayList.get(position).getUrlToImage());
                    intent.putExtra("url",articleArrayList.get(position).getUrl());
                    intent.putExtra("content",articleArrayList.get(position).getContent());

                    Log.d("parcle obj sending",articleModel.toString());
                    mContext.startActivity(intent);

                    //Toast.makeText(view.getContext(),"" + articleArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }





}
