package newshub.news.myapp.com.newshub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class ScrollingActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private Menu menu;
    Article article;
    private AsyncTask asyncTask;
    ImageView imageView;
    private TextToSpeech textToSpeech;
    String result;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();

        /*article = getIntent().getParcelableExtra("parcel_data");
        Log.d("rcv article bundle",article.getTitle());*/

       // tv.setText(article.getDescription());
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View rootview = findViewById(R.id.includelayout);

         TextView tv = rootview.findViewById(R.id.desc);

         StringBuilder sb = new StringBuilder();
         if (!(getIntent().getStringExtra("title").equals("null")))
             sb.append(getIntent().getStringExtra("title"));
             sb.append(System.getProperty("line.separator"));
         if (getIntent().getStringExtra("desc").equals("null"))
             sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
             sb.append(getIntent().getStringExtra("desc"));
         if (!(getIntent().getStringExtra("content").equals("null")))
             sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
            sb.append(getIntent().getStringExtra("content"));
         String finalStrting = sb.toString();

         tv.setText(finalStrting);
        finalStrting = getIntent().getStringExtra("desc");
        textToSpeech = new TextToSpeech(this,this);

         imageView = findViewById(R.id.expandedImage);
        String imageUrl = getIntent().getStringExtra("urltoimage");



        asyncTask = new DownloadTask().execute(stringToURL(imageUrl));



        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_info);
                }
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        hideOption(R.id.action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            textToSpeech.speak(this.result,TextToSpeech.QUEUE_FLUSH, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(this.result,TextToSpeech.QUEUE_FLUSH,null,null);
            } else {
                textToSpeech.speak(this.result, TextToSpeech.QUEUE_FLUSH, null);
            }
            return true;
        } else if (id == R.id.action_share) {

            MenuItem searchMenuItem = menu.findItem(R.id.action_share);
            MenuItemCompat.expandActionView(searchMenuItem);

           // Uri imgUri = Uri.parse(pictureFile.getAbsolutePath());
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Read full story on "+intent.getStringExtra("url"));
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
               // this.startActivity(whatsappIntent,"share link");
                this.startActivity(Intent.createChooser(whatsappIntent, "Share link!"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
              // textToSpeech.speak(this.result,TextToSpeech.QUEUE_FLUSH, null);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }


    public class DownloadTask extends AsyncTask<URL,Void,Bitmap> {
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
                imageView.setImageBitmap(result);

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

    }

/* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/