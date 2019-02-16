package newshub.news.myapp.com.newshub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import newshub.news.myapp.com.adapter.SportsNewsAdapter;
import newshub.news.myapp.com.adapter.TechNewsAdapter;
import newshub.news.myapp.com.model.SportsModel;
import newshub.news.myapp.com.model.SportsResponse;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SprtsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SprtsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String API_KEY ="3fc6be37ed584ba3aef203f2c9269c5d";

    public SprtsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sprts, container, false);
        final RequestInterface apireq = ApiClient.getClient().create(RequestInterface.class);
        final RecyclerView recyclerView = view.findViewById(R.id.sportsrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        Call<SportsResponse> call = apireq.getSportsNews("sports",API_KEY);

        call.enqueue(new Callback<SportsResponse>() {
            @Override
            public void onResponse(Call<SportsResponse> call, retrofit2.Response<SportsResponse> response) {
                if (response.body().getStatus().equals("ok")){
                    List<SportsModel> articleList = response.body().getSources();
                    // Log.d("response =",response.body().getSourcesList().toString());
                    if (articleList.size() >0) {
                        Log.d("response size =",""+articleList.size());
                        SportsNewsAdapter adapter1 = new SportsNewsAdapter(articleList);
                        recyclerView.setAdapter(adapter1);
                    }
                }
            }

            @Override
            public void onFailure(Call<SportsResponse> call, Throwable t) {
                Log.e("out", t.toString());
            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
