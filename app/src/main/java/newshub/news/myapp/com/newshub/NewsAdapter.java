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
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import newshub.news.myapp.com.Utility.Utility;

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
    String fullstoryUrl ;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        articleModel = articleArrayList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:

                Log.d("article model",articleModel.getPublishedAt() + "title" +articleModel.getTitle() +"desc "+articleModel.getDescription() + "url" + articleModel.getUrl());
                final TopViewHolder topViewHolder = (TopViewHolder) viewHolder;
                topViewHolder.title.setText(articleModel.getTitle());
                topViewHolder.desc.setText(articleModel.getDescription());

                 Log.d("original",""+articleModel.getPublishedAt());
                 String original = articleArrayList.get(position).getPublishedAt();
                 String another = original.substring(12,19);
                 Log.d("aNOTHER",another);
                Log.d("article model:",articleModel.toString());


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                long time = 0;
                try {
                    time = sdf.parse(original).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String timeAgo = Utility.getTimeAgo(time);
                long now = System.currentTimeMillis();

                CharSequence ago =
                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);

                topViewHolder.publishat.setText(ago);
                topViewHolder.readstory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        Log.d("inside article  url",articleModel.getUrl());
                        browserIntent.setData(Uri.parse(articleArrayList.get(position).getUrl()));
                        context.startActivity(browserIntent);
                    }
                });

                topViewHolder.share_whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Read full story on "+articleArrayList.get(position).getUrl());
                        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        try {
                            context.startActivity(Intent.createChooser(whatsappIntent, "Share link!"));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                // String imageUrl = articleModel.getUrlToImage();
                String imageUrl = "";
                 imageUrl = articleArrayList.get(position).getUrlToImage();
                /*if (imageUrl!=null ||imageUrl.isEmpty()) {
                    topViewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
                }else*/

                    if (imageUrl != null)
                //  Picasso.with(context).load(imageUrl).resize(600, 400).fit().error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(view.imageView);
                Picasso.with(context).load(imageUrl).resize(1000, 400).error(R.mipmap.ic_launcher).networkPolicy(NetworkPolicy.NO_CACHE).into(topViewHolder.imageView);
                    else
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
        private TextView title,desc,publishat,readstory;
        public  ImageView imageView;
        private ImageButton share_whatsapp;
        private Context mContext;
        private LinearLayout artilceAdapterParentLinear;
        private TopViewHolder(@NonNull final  View view) {
            super(view);
            mContext = itemView.getContext();
              title = view.findViewById(R.id.res_title);
              desc = view.findViewById(R.id.res_desc);
              publishat = view.findViewById(R.id.publishAt);
              readstory = view.findViewById(R.id.readfullstory);
            imageView = view.findViewById(R.id.imageview);
            share_whatsapp = view.findViewById(R.id.whatsapp_share);



            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final Intent intent ;
                    int position = getAdapterPosition();
                    intent = new Intent(mContext,ScrollingActivity.class);
                    if (articleArrayList.get(position).getDescription() != null)
                    intent.putExtra("desc",articleArrayList.get(position).getDescription());
                    else
                        intent.putExtra("desc","null");
                    if (articleArrayList.get(position).getUrlToImage() != null)
                    intent.putExtra("urltoimage",articleArrayList.get(position).getUrlToImage());
                    else
                        intent.putExtra("urltoimage","null");
                    if (articleArrayList.get(position).getContent()!= null)
                    intent.putExtra("content",articleArrayList.get(position).getContent());
                    else
                        intent.putExtra("content","null");
                    if (articleArrayList.get(position).getTitle()!=null)
                    intent.putExtra("title",articleArrayList.get(position).getTitle());
                    else
                        intent.putExtra("title","null");

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
