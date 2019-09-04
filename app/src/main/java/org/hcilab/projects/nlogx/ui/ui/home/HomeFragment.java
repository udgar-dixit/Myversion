package org.hcilab.projects.nlogx.ui.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.hcilab.projects.nlogx.R;
import org.hcilab.projects.nlogx.ui.BrowseActivity;


public class HomeFragment extends Fragment  {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Intent Browse=new Intent(this, BrowseActivity.class);
     //  startActivity(Browse);
      //startActivity(new Intent(getActivity(), BrowseActivity.class));
    }
}