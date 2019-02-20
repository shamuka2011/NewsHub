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
import java.util.List;

/**
 * Created by Sweety on 07-02-2019.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.TopViewHolder>  {



    private Context context;
    private ImageView imageView;
    private List<Article> articleArrayList;
    private AsyncTask mytask;
    private TopViewHolder view;
    Article articleModel ;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    // private View view;
    public NewsAdapter(List<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
    }


    public NewsAdapter(Context context) {
        this.context = context;
    }
    //private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


    @NonNull
    @Override
    public NewsAdapter.TopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.response_top, viewGroup, false);

        return new TopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder topViewHolder, int position) {
        view = topViewHolder;
        articleModel = articleArrayList.get(position);
        topViewHolder.title.setText(articleModel.getTitle());
        //  topViewHolder.desc.setText(articleModel.getDescription());
        // topViewHolder.author.setText("by "+articleModel.getAuthor());
        // topViewHolder.publishat.setText(articleModel.getPublishedAt());
        // String imageUrl = articleModel.getUrlToImage();
        String imageUrl = articleArrayList.get(position).getUrlToImage();
        // Log.d("imageurl ",imageUrl);
        // mytask = new DownloadTask().execute(stringToURL(imageUrl));
        //this.articleModel = articleModel;


        //  Picasso.with(context).load(imageUrl).resize(600, 400).fit().error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(view.imageView);
        Picasso.with(context).load(imageUrl).resize(600, 400).error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(view.imageView);


        // topViewHolder.content.setText(articleModel.getContent());
        // topViewHolder.artilceAdapterParentLinear.setTag(articleModel);

        // Picasso.with(context).load(imageUrl).placeholder(R.drawable.ic_mtrl_chip_close_circle).into(imageView);

    }



    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }


    // Custom method to convert string to url
    protected URL stringToURL(String urlString){
        try{
            URL url = new URL(urlString);
            return url;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }

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
   /* public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }*/





}
