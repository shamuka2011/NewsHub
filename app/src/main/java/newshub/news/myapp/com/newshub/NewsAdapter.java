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
    public NewsAdapter.TopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.response_top, viewGroup, false);
        context = viewGroup.getContext();
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


    private class DownloadTask extends AsyncTask<URL,Void,Bitmap> {
        // Before the tasks execution
        protected void onPreExecute(){
            // Display the progress dialog on async task start

        }

        // Do the task in background/non UI thread
        protected Bitmap doInBackground(URL...urls){
            URL url = urls[0];
            HttpURLConnection connection = null;

            try{
                // Initialize a new http url connection
                connection = (HttpURLConnection) url.openConnection();

                // Connect the http url connection
                connection.connect();

                // Get the input stream from http url connection
                InputStream inputStream = connection.getInputStream();

                /*
                    BufferedInputStream
                        A BufferedInputStream adds functionality to another input stream-namely,
                        the ability to buffer the input and to support the mark and reset methods.
                */
                /*
                    BufferedInputStream(InputStream in)
                        Creates a BufferedInputStream and saves its argument,
                        the input stream in, for later use.
                */
                // Initialize a new BufferedInputStream from InputStream
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                /*
                    decodeStream
                        Bitmap decodeStream (InputStream is)
                            Decode an input stream into a bitmap. If the input stream is null, or
                            cannot be used to decode a bitmap, the function returns null. The stream's
                            position will be where ever it was after the encoded data was read.

                        Parameters
                            is InputStream : The input stream that holds the raw data
                                              to be decoded into a bitmap.
                        Returns
                            Bitmap : The decoded bitmap, or null if the image data could not be decoded.
                */
                // Convert BufferedInputStream to Bitmap object
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                // Return the downloaded bitmap
                return bmp;

            }catch(IOException e){
                e.printStackTrace();
            }finally{
                // Disconnect the http url connection
                connection.disconnect();
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result){
            // Hide the progress dialog

            if(result!=null){
                // Display the downloaded image into ImageView
                view.imageView.setImageBitmap(result);

               /* // Save bitmap to internal storage
                Uri imageInternalUri = saveImageToInternalStorage(result);
                // Set the ImageView image from internal storage
                mImageViewInternal.setImageURI(imageInternalUri);*/
            }else {
                // Notify user that an error occurred while downloading image
               // Snackbar.make(mCLayout,"Error",Snackbar.LENGTH_LONG).show();
            }
        }
    }



    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

           view. imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
           view. imageView.setImageDrawable(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
           view.imageView.setImageDrawable(placeHolderDrawable);
        }
    };
}
