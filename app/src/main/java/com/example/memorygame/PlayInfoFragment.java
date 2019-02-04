package com.example.memorygame;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayInfoFragment extends Fragment {

    private play mDisplayedPlay;


    public PlayInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_info, container, false);
    }


    public void displayPlay(play play){
        ((TextView)getActivity().findViewById(R.id.titlePlay)).setText(play.title);
        ((TextView)getActivity().findViewById(R.id.triesPlay)).setText(play.tries);
        ((TextView)getActivity().findViewById(R.id.timePlay)).setText(play.time);
        ((TextView)getActivity().findViewById(R.id.typePlay)).setText(play.type);

    }



    @Override
    public void onActivityCreated(Bundle savedInstancesState){
        super.onActivityCreated(savedInstancesState);

        Intent intent = getActivity().getIntent();

        play receivedPlay = intent.getParcelableExtra(GameHistory.playExtra);
        if(receivedPlay!=null)
            displayPlay(receivedPlay);
    }


}
