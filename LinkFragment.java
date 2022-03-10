package com.john.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.john.myapplication.MainActivity.MODE_TCP_CLIENT;
import static com.john.myapplication.MainActivity.MODE_TCP_SERVER;
import static com.john.myapplication.R.id.mips;
import static com.john.myapplication.R.id.mports;

/**
 * Created by Administrator on 2021/1/8.
 */

public class LinkFragment extends Fragment {
    Button mlink,mdis;
    CheckBox checkset;
    EditText mips,mports;
    TextView mstatus;
    Boolean is_default;
    Spinner modeset;
    public int ch_mode;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        //View view = inflater.inflate(R.layout.linkfragment, container, false);
//        //return view;
//    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mlink = (Button) getActivity().findViewById(R.id.set);
//        mdis = (Button) getActivity().findViewById(R.id.dis);
//        checkset = (CheckBox) getActivity().findViewById(R.id.checkset);
//        modeset = (Spinner) getActivity().findViewById(R.id.modeselect);
//        mstatus=(TextView)getActivity().findViewById(R.id.state) ;
//       // mstop=(Button)getActivity().findViewById(R.id.dis)
//        mlink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
//            }
//        });

//        modeset.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int pos, long id) {
//                 ch_mode = pos;
//                if (ch_mode == MODE_TCP_SERVER) {
//                    Toast.makeText(getActivity(), "服务器模式", Toast.LENGTH_SHORT).show();
//                } else if (ch_mode == MODE_TCP_CLIENT) {
//                    Toast.makeText(getActivity(), "客户端模式", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        //checkset.setOnCheckedChangeListener(listener);
    }
//    public CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            switch (buttonView.getId()) {
//                case R.id.set:
//                    if (isChecked) {
//                       // Toast.makeText(MainActivity.this, "默认设置", Toast.LENGTH_SHORT).show();
//                        mips.setEnabled(false);
//                        mports.setEnabled(false);
//                        is_default = true;
//                    } else {
//                        is_default = false;
//                    }mips.setEnabled(true);
//                    mports.setEnabled(true);
//                    break;
//            }
//
//        }
//    };
}
