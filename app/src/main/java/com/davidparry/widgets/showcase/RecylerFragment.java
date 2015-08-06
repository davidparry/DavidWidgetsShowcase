package com.davidparry.widgets.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2015 David Parry
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class RecylerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public RecylerFragment() {
    }

    public static RecylerFragment newInstance() {
        RecylerFragment fragment = new RecylerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recyler, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chatview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter _adapter = new RecyclerViewAdapter(loadRows());
        recyclerView.setAdapter(_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    private SingleRow[] loadRows() {
        SingleRow[] rows = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("exampledata.txt"), "UTF-8"));
            List<SingleRow> singleRows = new ArrayList<SingleRow>();
            String line = reader.readLine();
            while (line != null) {
                String[] items = line.split(",");
                if (items.length > 1) {
                    singleRows.add(new SingleRow(items[0], items[1]));
                }
                line = reader.readLine();
            }
            rows = singleRows.toArray(new SingleRow[singleRows.size()]);

        } catch (IOException e) {
            // the file lives in the assets
            Log.e("ERROR", "Problem hummm", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }


        return rows;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
