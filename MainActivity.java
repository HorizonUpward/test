package com.john.myapplication;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NetWorkReceiver.IntAction {
    CheckBox checkset;
    Spinner modeset ;
    String filename,filename1;
    File currentParent;
    File[] currentFiles;

    byte[] g;
    boolean stopboolean=true;
    int start;//??????????????????
    //??????
    private Paint mPaint;
    //??????
    private Path mPath;
    public Canvas mCanvas;//??????
    private SurfaceHolder holder;

    float c= (float) 0.0,v= (float) 0.0;

    public boolean is_default = false;
    public final static int         MODE_TCP_SERVER = 2;
    public final static int         MODE_TCP_CLIENT = 1;
    public        int      ch_mode    = 0;

    FrameLayout frame,frame2,frame3,frame4;
    private MyAdapter adapter;
    private ListView listView;
    private NewAdapter adapter2;
    private ListView listView2;
    private ListView listfile;
    //int f=2;
    //private HandlerThread mHandler;
    TabHost tabHost;
    int  i=0;
    //SQLiteDatabase dp;
private int[] imageIds = new int[]{
        R.drawable.ic_x,
        R.drawable.ic_y,
        R.drawable.ic_v,
        R.drawable.ic_angle,
        R.drawable.ic_fxp,
        R.drawable.my_pc,
};
private int[] imageIds1 = new int[]{
            R.drawable.ic_link,
            R.drawable.ic_fx,
            R.drawable.ic_data,
            R.drawable.ic_ab,
};
private String[] Name1 = new String[]{"????????????","????????????","????????????","??????"};
private String[] Name = new String[]{"X??????:","Y??????:","V m/s:","????????:","????????:","??????m:"};
    public float[] gain = new float[]{0,0,0,0,0,0};
    private Byte[] Buf;
    Switch bSwitch,aSwitch;
    TextView Senddata,mReceiveDataShow,mSendCount,mReceiveCount,mDeAction,TXT,mstatus,mstates,mIpShow,laststatus,currentpath;
    Button Bsend,Blink,mlink,mdis,overdraw,loading,loadingback,choicefile,drawbutton,delete;
    ImageButton imagestop,imagezuo,imageyou,imageqian;
    EditText Eip,Eport,mSendData,mips,mports;
    ImageView hero;
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION = 1000;
    //private WIFIStateReceiver mWIFIStateReceiver;
    //private WIFIConnectionManager ConnectionManager;
    private long exitTime = 0;

    private             Tcp_service tcp_service     = null;
    private int ser_port;
    private              boolean    ser_islink                 = false;
    public final static  int        SERVER_STATE_CORRECT_READ  = 3;
    public final static  int        SERVER_STATE_CORRECT_WRITE = 4;               //??????????????????
    public final static  int        SERVER_STATE_ERROR         = 5;                 //????????????????????????
    public final static  int        SERVER_STATE_IOFO          = 6;                  //??????SOCKET??????
    //TCP????????????????????????

    private               Tcp_client tcp_client                 = null;
    public final static int        CLIENT_STATE_CORRECT_READ  = 7;
    public final static  int        CLIENT_STATE_CORRECT_WRITE = 8;               //??????????????????
    public final static  int        CLIENT_STATE_ERROR         = 9;                 //????????????????????????
    public final static  int        CLIENT_STATE_IOFO          = 10;                  //??????SOCKET??????
    public               boolean    client_islink              = false;
    //??????????????????
    private boolean Hex_show   = false;
    private boolean Hex_send   = false;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Fragment f1,f2,f3;
    //?????????
    private int     countin    = 0;
    private int countout = 0;
    int flag=0;
    int flag1=0;//???????????????????????????listview???????????????
    int flag2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();//?????????TabHost
        LayoutInflater.from(this).inflate(R.layout.tab3, tabHost.getTabContentView());
        LayoutInflater.from(this).inflate(R.layout.tab2, tabHost.getTabContentView());
        LayoutInflater.from(this).inflate(R.layout.tab1, tabHost.getTabContentView());
        //LayoutInflater.from(this).inflate(R.layout.tab4, tabHost.getTabContentView());
//        aSwitch = (Switch) findViewById(R.id.switch1);
//        bSwitch = (Switch) findViewById(R.id.switch2);
        // ???????????????Tab???
        /*TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1")
                .setIndicator("????????????") // ????????????
                .setContent(R.id.tab01); //????????????

        /* ???????????????????????????????????????????????????????????? */
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("????????????").setContent(R.id.tab3));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("??????").setContent(R.id.tab2));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("????????????").setContent(R.id.tab1));
        //tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("??????").setContent(R.id.tab4));
        updateTab(tabHost);
        //???????????????????????????setOnTabChangedListener
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            // tabId???newTabSpec????????????????????????tab??????????????????layout??????????????????id
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) {   //???????????????
                    updateTab(tabHost);
                }
                if (tabId.equals("tab2")) {
                    updateTab(tabHost);//???????????????
                }
                if (tabId.equals("tab3")) {   //???????????????
                    updateTab(tabHost);
                }
//                if (tabId.equals("tab4")) {   //???4?????????
//                    updateTab(tabHost);
//                }
            }
        });
       // aSwitch.setChecked(false);
       // bSwitch.setChecked(false);
//        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
//                //????????????????????????
//                if (ischecked) {
////                    if (client_islink == true) {
////                    String message = "1";
////                    sendmessage(message);
////                } else {
////                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
////                }
////                    Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
////                    f=1;
//                }else {
////                    if (client_islink == true) {
////                        String message = "2";
////                        sendmessage(message);
////                    } else {
////                        Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
////                    }
////                    Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
////                    f=2;
//                }
//            }
//        });
        InitCode();
        InitLisenner();
        //initlink();
//        float kk = hero.getX();
//        int  ll = (int) kk;
//        TXT.setText(ll);
    }
    public void InitCode() {

        //t=(TextView)findViewById(R.id.T);
        //fragment ???????????????????????????-link
        checkset=(CheckBox)findViewById(R.id.checkset);
        mips=(EditText)findViewById(R.id.mips);
        mports=(EditText)findViewById(R.id.mports);
        mlink=(Button) findViewById(R.id.set);
        mdis=(Button) findViewById(R.id.dis);
        loading=(Button) findViewById(R.id.loading);
        //loadingback=(Button) findViewById(R.id.loadingback);
        mstatus=(TextView)findViewById(R.id.states) ;
        modeset=(Spinner)findViewById(R.id.modeselect);
        mstates=(TextView)findViewById(R.id.state) ;
        mIpShow=(TextView)findViewById(R.id.IpShow) ;
        //fragment -Directon  ?????????????????????

        overdraw=(Button)findViewById(R.id.overdraw) ;
        choicefile=(Button)findViewById(R.id.choicefile);
        drawbutton=(Button)findViewById(R.id.drawbutton);
        delete=(Button)findViewById(R.id.delete);

        frame=(FrameLayout) findViewById(R.id.frame);
        frame2=(FrameLayout) findViewById(R.id.frame2);
        frame3=(FrameLayout) findViewById(R.id.frame3);
        frame4=(FrameLayout) findViewById(R.id.frame4);

        imagestop=(ImageButton)findViewById(R.id.imagestop);
        imageqian=(ImageButton)findViewById(R.id.imageqian);
        imageyou=(ImageButton)findViewById(R.id.imageyou);
        imagezuo=(ImageButton)findViewById(R.id.imagezuo);

        Senddata = (TextView) findViewById(R.id.Senddata);
        mSendCount = (TextView) findViewById(R.id.Sentcount);
        mReceiveCount = (TextView) findViewById(R.id.Rdatacount);
        mDeAction = (TextView) findViewById(R.id.statuss);
        mReceiveDataShow = (TextView) findViewById(R.id.receivedata);
        currentpath = (TextView) findViewById(R.id.currentpath);
        TXT = (TextView) findViewById(R.id.TXT);

        Bsend = (Button) findViewById(R.id.bsend);

        mSendData = (EditText) findViewById(R.id.editdata);

        listView=(ListView)findViewById(R.id.listview) ;
        listView2=(ListView)findViewById(R.id.listview2) ;
        listfile=(ListView)findViewById(R.id.listfile) ;

        mips.setText(getLocalIpAddress());
        mports.setText("8080");

        laststatus=(TextView)findViewById(R.id.laststatus);

        getLocationAccessPermission();

        adapter = new MyAdapter(MainActivity.this,getdata());
        listView.setAdapter(adapter);//????????????????????????

        adapter2 = new NewAdapter(MainActivity.this,getdatas());
        listView2.setAdapter(adapter2);//????????????????????????

         fragmentManager = getFragmentManager();
         transaction = fragmentManager. beginTransaction();//??????fragment?????????

        frame2.setVisibility(View.INVISIBLE);
        frame3.setVisibility(View.INVISIBLE);
        frame.setVisibility(View.INVISIBLE);
        frame4.setVisibility(View.INVISIBLE);

        SurfaceView mySurface = (SurfaceView)findViewById(R.id.surfaceView);
        holder = mySurface.getHolder();
        //mySurface.surfaceCreated(holder);//??????????????????????????????


        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mPaint = new Paint();
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(2);
                mPaint.setAntiAlias(true);//?????????
                mPath = new Path();
                mPath.moveTo(100, 0);//????????????
                //new Thread(drawRunnable).start();
                try {
                    //??????canvas??????
                    //mPath.lineTo(100, 200);
                    mCanvas = holder.lockCanvas();
                    //????????????
                    mCanvas.drawColor(Color.GREEN);
                    //????????????
                    mCanvas.drawPath(mPath, mPaint);
                }catch (Exception e){

                }finally {
                    if (mCanvas != null){
                        //??????canvas?????????????????????
                        holder.unlockCanvasAndPost(mCanvas);
                    }
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });

        mReceiveDataShow.setMovementMethod(new ScrollingMovementMethod(){
        });
        applyNet();//????????????????????????

        if(getSharedPreferences("checkInfo",0).getBoolean("cbState",false)){
            checkset.setChecked(true);
            mips.setEnabled(false);
            mports.setEnabled(false);
            modeset.setEnabled(false);
            is_default = true;
        }
        handler1.postDelayed(runnable1, 1500);//???????????????????????????????????????

        //hero =(ImageView)findViewById(R.id.hero);

        Fileoperations.verifyStoragePermissions(MainActivity.this);
        //???????????????
        Fileoperations.makeRootDirectory("/storage/emulated/0/TransmitData/");
        //???????????????????????????
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy???MM???dd??? HH:mm:ss");// HH:mm:ss
//??????????????????
        Date date = new Date(System.currentTimeMillis());
        filename = simpleDateFormat.format(date);
        //filename1="";
        start = 0;
        File root = new File("/storage/emulated/0/TransmitData/");
        if(root.exists()){
            currentParent = root;
            currentFiles = root.listFiles();
            //?????????????????????????????????????????????????????????ListView
            inflateListView(currentFiles);
        }
        hero =(ImageView)findViewById(R.id.hero);
    }

//    private Runnable drawRunnable = new Runnable() {
//        @Override
//        public void run() {
//
//        }
//    };
//private void wtest() {
//
//    if (!Fileoperations.fileIsExists("/storage/emulated/0/OwlDatabase/" + i + "name" + ".txt")) {
//        //writeData??????????????????????????????????????????????????????
//        Fileoperations.writeData("/storage/emulated/0/OwlDatabase/", i + "name", "??????Flag?????????????????????????????????");
//    }
//}

    private List<Map<String,Object>> getdata(){
    //ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String, Object>>();
    List<Map<String,Object>> listItem = new ArrayList<Map<String,Object>>();
    for(int i=0;i<6;i++){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("icon",imageIds[i]);
        map.put("name",Name[i]);
        map.put("data",gain[i]);
        listItem.add(map);
    }
    return listItem;
}
    private List<Map<String,Object>> getdatas(){
        //ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String, Object>>();
        List<Map<String,Object>> listItem2 = new ArrayList<Map<String,Object>>();
        for(int i=0;i<4;i++){
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("icon",imageIds1[i]);
            map.put("name",Name1[i]);
            //map.put("data",gain[i]);
            listItem2.add(map);
        }
        return listItem2;
    }
    private void applyNet() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetWorkReceiver netWorkReceiver = new NetWorkReceiver();
        registerReceiver(netWorkReceiver, filter);
        netWorkReceiver.setInteractionListener(this);
    }

//    public void initlink(){
//        if (tcp_client == null) {
//            tcp_client = new Tcp_client(cli_handler);
//            try {
//                InetAddress ipAddress = InetAddress.getByName("192.168.1.106");
//                int port = 8080;//Integer.valueOf(Eport.getText().toString());//???????????????
//                tcp_client.setInetAddress(ipAddress);
//                tcp_client.setPort(port);
//
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
//            Eip.setEnabled(false);
//            Eport.setEnabled(false);
//            tcp_client.start();
//        }
//    }

    private void Draw(){

        try {
            //??????canvas??????
            mCanvas = holder.lockCanvas();
            //????????????
            mCanvas.drawColor(Color.GREEN);
            //????????????
            mCanvas.drawPath(mPath, mPaint);
        }catch (Exception e){

        }finally {
            if (mCanvas != null){
                //??????canvas?????????????????????
                holder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    public void InitLisenner(){
        checkset.setOnCheckedChangeListener(listener);//checkbox??????
        modeset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                ch_mode = pos;
                if (ch_mode == MODE_TCP_SERVER) {
                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                    mips.setText(getLocalIpAddress());
                    mports.setText("8080");
                }else if (ch_mode == MODE_TCP_CLIENT){
                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

//        Blink.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//
//    if (tcp_client == null) {
//        tcp_client = new Tcp_client(cli_handler);
//        try {
//            InetAddress ipAddress = InetAddress.getByName(Eip.getText().toString());
//            int port = Integer.valueOf(Eport.getText().toString());//???????????????
//            tcp_client.setInetAddress(ipAddress);
//            tcp_client.setPort(port);
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        Eip.setEnabled(false);
//        Eport.setEnabled(false);
//        tcp_client.start();
//    }
//    Bdisconnect.setEnabled(true);
//
//            }});
//        Bdisconnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class
//                if (tcp_client != null) {
//                    tcp_client.close();
//                    tcp_client = null;
//                }
//                //Ip_clear();
//                Eip.setEnabled(true);
//                Eport.setEnabled(true);
//                Bdisconnect.setEnabled(false);}
//
//
//
//        });
        Bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class

                   if (client_islink||ser_islink) {
                       String message = mSendData.getText().toString().replaceAll(" ", "");
                       if (message.equals("")) {
                           Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                       }
                       sendmessage(message);
                   } else {
                       Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                   }

            }
        });

        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//??????????????????
                frame4.setVisibility(View.VISIBLE);
            }
        });//?????????
//        loadingback.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                try{
//                    if(!currentParent.getCanonicalPath().equals("/mnt/sdcard")){
//                        //??????????????????
//                        currentParent = currentParent.getParentFile();
//                        //?????????????????????????????????
//                        currentFiles = currentParent.listFiles();
//                        //????????????ListView
//                        inflateListView(currentFiles);
//                    }
//                }catch (Exception e) {
//                    // TODO: handle exception
//                    e.printStackTrace();
//                }
//            }
//        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//??????????????????
                showdialog1();
                delete.setEnabled(false);
            }
        });//?????????

        choicefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TXT.setText(ReadTxtFile("/storage/emulated/0/TransmitData/"+filename1));

                drawbutton.setEnabled(true);
                delete.setEnabled(true);
            }
        });

        drawbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g=ReadTxtFile("/storage/emulated/0/TransmitData/"+filename1).getBytes();
                Toast.makeText(MainActivity.this, "????????????,???"+g.length+"?????????,????????????", Toast.LENGTH_SHORT).show();
                handler2.postDelayed(runnable2, 100);//??????????????????
                drawbutton.setEnabled(false);
            }
        });

        imageqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (client_islink||ser_islink) {
                    String message ="qian";
                    sendmessage(message);
                    laststatus.setText("?????????????????????");
                } else {
                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imagestop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (client_islink||ser_islink) {
                    if(stopboolean){
                        String message ="stop";
                        sendmessage(message);
                        laststatus.setText("?????????????????????");
                    }
                    else {
                        String message ="start";

                        sendmessage(message);
                        laststatus.setText("?????????????????????");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imagezuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (client_islink||ser_islink) {
                    String message ="zuo";
                    sendmessage(message);
                    laststatus.setText("?????????????????????");
                } else {
                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (client_islink||ser_islink) {
                    String message ="you";
                    sendmessage(message);
                    laststatus.setText("?????????????????????");
                } else {
                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class
//                //database.searchSpecific(1);
//               //getLocationAccessPermission();
////                if (ConnectionManager.V().equals(SSID)){
////                t.append(ConnectionManager.V());}
////                else {
////                    Toast.makeText(MainActivity.this, SSID+" "+ConnectionManager.V(), Toast.LENGTH_SHORT).show();
////                }
//                //ConnectionManager.V();
////                    if (ConnectionManager.ScanWifiInfo()){
////                        Toast.makeText(MainActivity.this, "???????????????ESP8266", Toast.LENGTH_SHORT).show();
////
////                    }  else {Toast.makeText(MainActivity.this, "????????????ESP8266????????????ESP8266", Toast.LENGTH_SHORT).show();
////
////                    }
//
//
//              // t.append(ConnectionManager.V());
////                if (ConnectionManager.getWifiList1()){
////                        Toast.makeText(MainActivity.this, "???????????????ESP8266", Toast.LENGTH_SHORT).show();
////
////                    }  else {Toast.makeText(MainActivity.this, "????????????ESP8266????????????ESP8266", Toast.LENGTH_SHORT).show();
////
////                    }
//                gain=new float[]{1,2,3,4,5};
//                MyAdapter adapter = new MyAdapter(MainActivity.this,getdata());
//                listView.setAdapter(adapter);
//            }
//        });
//        open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class
//
//                if (client_islink == true) {
//                    String message = "11";
//                    sendmessage(message);
//                    Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class
//
//                if (client_islink == true) {
//                    String message = "22";
//                    sendmessage(message);
//                    Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//        door.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class
//
//                if (client_islink == true) {
//                    String message = "33";
//                    sendmessage(message);
//                    Toast.makeText(MainActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String result = adapterView.getItemAtPosition(i).toString();
                fragmentManager = getFragmentManager();
                transaction = fragmentManager.beginTransaction();
                if(result.equals("0")){

                    flag++;
                    if(flag==1) {
                        //flag1=0;
                        if(frame.getVisibility()==View.INVISIBLE){
                            frame.setVisibility(View.VISIBLE);}
                    }else if(flag==2){
                        flag=0;
                        frame.setVisibility(View.INVISIBLE);
                    }
                }else if(result.equals("1")){
                    flag1++;
                    if(flag1==1){
                        //flag=0;
                        if(frame2.getVisibility()==View.INVISIBLE){
                            frame2.setVisibility(View.VISIBLE);}

                    }else if(flag1==2){
                        flag1=0;
                        frame2.setVisibility(View.INVISIBLE);
                    }
                }else if(result.equals("2")){
                    flag2++;
                    if(flag2==1){
                        //flag=0;
                        if(frame3.getVisibility()==View.INVISIBLE){
                            frame3.setVisibility(View.VISIBLE);}

                    }else if(flag2==2){
                        flag2=0;
                        frame3.setVisibility(View.INVISIBLE);
                        frame4.setVisibility(View.INVISIBLE);
                        choicefile.setEnabled(false);
                        drawbutton.setEnabled(false);
                        delete.setEnabled(false);
                    }
                }
   //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }

        });
        mlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class
                if (mips.getText().toString().equals("") || mports.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "IP????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
//                else if(mips.getText().toString().equals("0.0.0.0")){
//                    Toast.makeText(MainActivity.this, "???????????????WiFi?????????", Toast.LENGTH_SHORT).show();
//                }
                else if(is_default) {
                    if (tcp_service == null) {
                        tcp_service = new Tcp_service(ser_handler, 8080);
                        tcp_service.start();
                        mstatus.setText("TCP????????? ??????");
                        mstates.setText("TCP????????? ??????");
                        Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                        mlink.setEnabled(false);
                        mdis.setEnabled(true);
                        modeset.setEnabled(false);
                        checkset.setEnabled(false);
                    } else {
                        mstatus.setText("TCP????????? ??????");
                        mstates.setText("TCP????????? ??????");
                    }
                    mdis.setEnabled(true);
                }else if (ch_mode == 0) {
                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                } else{

//                    if (is_default) {
//                        ch_mode = MODE_TCP_SERVER;
//                    }
                    if (ch_mode == MODE_TCP_SERVER) {
                        if (tcp_service == null) {
                            ser_port = Integer.valueOf(mports.getText().toString());    //???????????????????????? ??????8080
                            tcp_service = new Tcp_service(ser_handler, ser_port);
                            tcp_service.start();
                            mstatus.setText("TCP????????? ??????");
                            mstates.setText("TCP????????? ??????");
                            Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                            //mstop.setEnabled(true);
                            mips.setEnabled(false);
                            mports.setEnabled(false);
                        } else {
                            //Log.e(TAG, "?????????????????? ????????????");
                            mstatus.setText("TCP????????? ??????");
                            mstates.setText("TCP????????? ??????");
                            //mLl.setBackgroundResource(R.color.bg_error);
                        }
                    }else if (ch_mode == MODE_TCP_CLIENT) {
                        if (tcp_client == null) {
                            tcp_client = new Tcp_client(cli_handler);
                            try {
                                InetAddress ipAddress = InetAddress.getByName(mips.getText().toString());
                                int port = Integer.valueOf(mports.getText().toString());//???????????????
                                tcp_client.setInetAddress(ipAddress);
                                tcp_client.setPort(port);

                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            }
                            mips.setEnabled(false);
                            mports.setEnabled(false);
                            tcp_client.start();
                        }
                    }
                    mdis.setEnabled(true);
                    mlink.setEnabled(false);
                    modeset.setEnabled(false);
                    checkset.setEnabled(false);}
            }
        });
        mdis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class
               if(is_default){
                   tcp_service.setis_start(false);
                   if (tcp_service != null) {
                       tcp_service.close();
                       tcp_service = null;
                   }
                   //modeset.setEnabled(true);
                   mdis.setEnabled(false);
                   mlink.setEnabled(true);
                   checkset.setEnabled(true);
               }else {

                   if (ch_mode == MODE_TCP_SERVER) {
                       tcp_service.setis_start(false);
                       if (tcp_service != null) {
                           tcp_service.close();
                           tcp_service = null;
                       }

                       mips.setEnabled(true);
                       mports.setEnabled(true);
                   }else
                   if (ch_mode == MODE_TCP_CLIENT) {
                       if (tcp_client != null) {
                           tcp_client.close();
                           tcp_client = null;
                       }
                       mports.setEnabled(true);
                       mips.setEnabled(true);

                   }
                   mdis.setEnabled(false);
                   mlink.setEnabled(true);
                   modeset.setEnabled(true);
                   checkset.setEnabled(true);
               }
            }
        });
        overdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class

                if (start==0) {
                        Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                } else {

                    showdialog();
                    //Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                }

            }
        });
        listfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                //?????????????????????????????????????????????????????????
                if(currentFiles[arg2].isFile()){
                    filename1=currentFiles[arg2].getName();//??????????????????????????????
                    choicefile.setEnabled(true);//??????????????????????????????
                    currentpath.setText("????????????:"+filename1);
                    return;}
                //????????????????????????????????????????????????
                File[] tmp = currentFiles[arg2].listFiles();
                if(tmp == null ){
                    Toast.makeText(MainActivity.this, "???????????????????????????????????????????????????",Toast.LENGTH_SHORT).show();
                }
                else{
                    //??????????????????????????????????????????????????????????????????????????????
                    currentParent = currentFiles[arg2];
                    //?????????????????????????????????????????????????????????
                    currentFiles = tmp;
                    //????????????ListView
                    inflateListView(currentFiles);
                }
            }
        });
//        mdis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????this???????????????????????????class
//
//
//
//            }
//        });
    }

    private void inflateListView(File[] files){
        //????????????List?????????List??????????????????Map
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < files.length; i++){
            Map<String, Object> listItem = new HashMap<String, Object>();
            //????????????File????????????,??????floder?????????????????????file??????
            if(files[i].isDirectory()){
                listItem.put("icon", R.drawable.my_file);
            }
            else{
                listItem.put("icon", R.drawable.my_wen);
            }
            listItem.put("fileName", files[i].getName());
            //??????List???
            listItems.add(listItem);
        }
        //????????????SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.line,
                new String[]{"icon","fileName"}, new int[]{R.id.fileimage, R.id.file_name});
        //???ListView??????Adapter
        listfile.setAdapter(simpleAdapter);
        try{
            currentpath.setText("???????????????: " + currentParent.getCanonicalPath());
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static String ReadTxtFile(String strFilePath)//??????TXT???????????????????????????????????????
    {
        String path = strFilePath;
        String content = ""; //?????????????????????
        //????????????
        File file = new File(path);
        //??????path????????????????????????????????????????????????????????????
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputStreamReader = null;
                try {
                    inputStreamReader = new InputStreamReader(instream, "GBK");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer("");
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        //sb.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                content = sb.toString();
            }
            catch (java.io.FileNotFoundException e)
            {
                Log.d("TestFile", "The File doesn't not exist.");
            }
        }
        return content;
    }


    public CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.checkset:
                    if (isChecked) {
                        Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                        getSharedPreferences("checkInfo",0).edit().putBoolean("cbState",true).apply();
                        mips.setEnabled(false);
                        mports.setEnabled(false);
                        modeset.setEnabled(false);
                        is_default = true;
                    } else {
                        getSharedPreferences("checkInfo",0).edit().putBoolean("cbState",false).apply();
                        is_default = false;
                        mips.setEnabled(true);
                        mports.setEnabled(true);
                        modeset.setEnabled(true);
                    }
                    break;
            }
        }

};

//    private void hideFragment(FragmentTransaction transaction) {
//        if ( f1!= null) {
//            transaction.hide(f1);//????????????????????????????????????????????????????????????????????????
//          // transaction.remove(f1);
//        }
//        if (f2 != null) {
//            transaction.hide(f2);
//            //transaction.remove(f2);
//        }
////        if (f3 != null) {
////            //transaction.hide(f3);
////            transaction.remove(f3);
////        }
//    }


    public void showdialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("?????????")
                .setMessage("??????????????????")
                .setIcon(R.mipmap.ic_launcher)
//                .setPositiveButton("???????????????", new DialogInterface.OnClickListener() {//??????"Yes"??????
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        start=0;
//                        mCanvas.drawColor(Color.GREEN);
//                        Draw();
//                        Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
//                    }
//                })

                .setNegativeButton("??????", new DialogInterface.OnClickListener() {//????????????
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "??????", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("??????", new DialogInterface.OnClickListener() {//??????????????????
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        start=0;
                        clearDraw();
                        hero.setTranslationX(0);
                        hero.setTranslationY(0);
                        Toast.makeText(MainActivity.this, "??????", Toast.LENGTH_SHORT).show();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy???MM???dd??? HH:mm:ss");// HH:mm:ss
//??????????????????
                        Date date = new Date(System.currentTimeMillis());
                        filename = simpleDateFormat.format(date);
                        File root = new File("/storage/emulated/0/TransmitData/");
                        if(root.exists()){
                            currentParent = root;
                            currentFiles = root.listFiles();
                            //?????????????????????????????????????????????????????????ListView
                            inflateListView(currentFiles);
                        }
                    }
                })
                .create();

        alertDialog.show();
    }//??????????????????
    public void showdialog1(){//????????????

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("???????????????")
                .setMessage("????????????<"+filename1+">")
                .setIcon(R.mipmap.ic_launcher)

                .setNegativeButton("??????", new DialogInterface.OnClickListener() {//????????????
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "??????", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("??????", new DialogInterface.OnClickListener() {//??????????????????
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(deleteFile("/storage/emulated/0/TransmitData/"+filename1)) {
                            Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                            drawbutton.setEnabled(false);
                            File root = new File("/storage/emulated/0/TransmitData/");
                            if(root.exists()){
                                currentParent = root;
                                currentFiles = root.listFiles();
                                //?????????????????????????????????????????????????????????ListView
                                inflateListView(currentFiles);
                            }
                        }
                        else Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        alertDialog.show();
    }

    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }//????????????

    public void clearDraw() {
        //Canvas canvas = null;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);//?????????
        mPath = new Path();
        mPath.moveTo(0, 0);//????????????
        try {
            mCanvas = holder.lockCanvas(null);
            mCanvas.drawColor(Color.GREEN);
        }catch (Exception e) {
            // TODO: handle exception
        }finally {
            if(mCanvas != null) {
                holder.unlockCanvasAndPost(mCanvas);
            }
        }
    }//????????????

    private final MyHandler cli_handler = new MyHandler(this, "client");
    private final MyHandler ser_handler = new MyHandler(this, "service");
    //??????????????????
//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//
//            String message = mSendData.getText().toString();
//            sendmessage(message);
//        }
//    };
    static class MyHandler extends Handler {
        private WeakReference<MainActivity> mWeakActivity;
        private String                      mStr;
        public MyHandler(MainActivity activity, String string) {
            mWeakActivity = new WeakReference<MainActivity>(activity);
            mStr = string;
        }
        public void handleMessage(Message msg) {
            MainActivity activity = mWeakActivity.get();
            if (mStr.equals("service")) {
                if (msg.what == SERVER_STATE_ERROR) {
                    Toast.makeText(activity, "????????????", Toast.LENGTH_SHORT).show();
                    activity.mstatus.setText(" ????????????");
                    activity.mstates.setText("TCP????????? ????????????");
                    activity.mDeAction.setText("TCP????????? ????????????");
                    activity.mIpShow.setText(" ");
                    activity.ser_islink = false;
                }
                //????????????
                if (msg.what == SERVER_STATE_CORRECT_WRITE) {
                    activity.Handler_send(msg);
                }
                //????????????
                if (msg.what == SERVER_STATE_CORRECT_READ) {
                    activity.Handler_receive(msg);
                }
                if (msg.what == SERVER_STATE_IOFO) {
                    activity.ser_islink = true;
                    activity.mstatus.setText("????????????");
                    activity.mstates.setText("TCP????????? ????????????");
                    activity.mDeAction.setText("TCP????????? ????????????");
                    activity.mdis.setEnabled(true);
                    String[] strings = (String[]) msg.obj;
                    activity.mIpShow.append("IP:"+strings[0] + "\n");
                    Toast.makeText(activity, "?????????", Toast.LENGTH_SHORT).show();
                }
            } else {
                switch (msg.what) {
                    case CLIENT_STATE_ERROR:
                        Toast.makeText(activity, "?????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                        activity.mstatus.setText("????????????");
                        activity.mstates.setText("TCP????????? ????????????");
                        activity.mDeAction.setText("TCP????????? ????????????");
                        activity.mIpShow.setText("??????IP");
                        // activity.mNameShow.setHint("????????????");
                        activity.client_islink = false;
                        break;
                    case CLIENT_STATE_IOFO:
                        activity.client_islink = true;
                        activity.mstatus.setText("????????????");
                        activity.mstates.setText("TCP????????? ????????????");
                        activity.mDeAction.setText("TCP????????? ????????????");
                        String[] strings = (String[]) msg.obj;
                         activity.mIpShow.append(strings[0] + "\n");
                        //activity.mNameShow.append(strings[1] + "\n");
                        break;
                    //????????????
                    case CLIENT_STATE_CORRECT_READ:
                        activity.Handler_receive(msg);
                        break;
                    //????????????
                    case CLIENT_STATE_CORRECT_WRITE:
                        activity.Handler_send(msg);
                        break;
                }
            }
        }
    }

    final Handler handler1=new Handler();
    Runnable runnable1=new Runnable() {
        //private Socket socket = null;
        @Override
        public void run() {
            if(mstates.getText().toString().equals("TCP????????? ????????????")){
            String strings = " ";
            sendmessage(strings);
            }
            handler1.postDelayed(this, 1500);
        }
    };
    final Handler handler2=new Handler();
    Runnable runnable2=new Runnable() {
        //private Socket socket = null;
        @Override
        public void run() {

            try{
                if(i==0){
                   // mPath.moveTo(g[0], g[1]);
                    c = (float) g[0];
                    v = (float) g[1];
                    //Toast.makeText(MainActivity.this, "?????????:"+g.length, Toast.LENGTH_SHORT).show();
                }else{
                    mPath.moveTo(c, v);
                    mPath.lineTo((float)g[i*2], (float)g[i*2+1]);
                    hero.setTranslationX((float)g[i*2]);
                    hero.setTranslationY((float)g[i*2+1]);
                    c = (float)g[i*2];
                    v = (float)g[i*2+1];
                    Draw();
                }
                i++;
            }catch (Exception e){
                Toast.makeText(MainActivity.this, i, Toast.LENGTH_SHORT).show();
            }
                if(g.length%2==0){
                    if(i==(g.length/2)) {
                        hero.setTranslationX(c);
                        hero.setTranslationY(v);
                        i = 0;
                        handler2.removeCallbacks(runnable2);
                        start=1;
                        Toast.makeText(MainActivity.this, "????????????" + g.length, Toast.LENGTH_SHORT).show();
                    }else {handler2.postDelayed(this, 100);}
                }else{
                    if(i==(g.length-1)/2) {
                        hero.setTranslationX(c);
                        hero.setTranslationY(v);
                        i = 0;
                        handler2.removeCallbacks(runnable2);
                        start=1;
                        Toast.makeText(MainActivity.this, "????????????" + g.length, Toast.LENGTH_SHORT).show();
                    }else {handler2.postDelayed(this, 100);}
                }


        }
    };
    public void sendmessage(String message) {
        byte[] send = message.getBytes();
        if (ch_mode == MODE_TCP_SERVER||(checkset.isChecked())) {
            tcp_service.write(send);
        } else if (ch_mode == MODE_TCP_CLIENT) {
            tcp_client.sendmessage(send);
        }
    }

    private void Handler_send(Message msg) {
        byte[] writeBuf = (byte[]) msg.obj;
        //ser_handler.removeCallbacks(runnable);
            String writeMessage = null;
            try {
                writeMessage = new String(writeBuf, "GBK");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            Senddata.append(writeMessage);
            countout += writeMessage.length();
            mSendCount.setText("????????????" + countout + "???");

    }
    // ???????????????????????????????????????handler??????????????????????????????
    private void Handler_receive(Message msg) {//
        byte[] buffer = (byte[]) msg.obj;
        //int Bit=buffer.length;
            String readMessage = null;
            try {
                readMessage = new String(buffer, 0, msg.arg1, "GBK");

               // gain={B[0],B[1],B[2],B[3]};
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if((buffer[0]==0x55)&&(buffer[1]==0x51)){//?????????????????????STM32????????????
                int j=2;
                for(int i=0;i<6;i++){
                gain[i]=(float) buffer[j];//??????????????????????????????????????????????????????????????????????????????
                    j++;
                     }
                if(start==0){
                    start=1;
                    //mPath.moveTo(gain[1],gain[2]);
                    c = gain[0];
                    v = gain[1];
                }else {
                    mPath.moveTo(c, v);
                    mPath.lineTo(gain[0], gain[1]);
                    hero.setTranslationX(gain[0]);
                    hero.setTranslationY(gain[1]);
                    c = gain[0];
                    v = gain[1];
                    Draw();
                }
//                for(int i=0;i<2;i++){
//                    byte res1=buffer[i+2];
//                    Fileoperations.writeData2("/storage/emulated/0/TransmitData/", filename+"??????",res1);
//                }
                byte[] res1={buffer[2],buffer[3]};//??????xy????????????
                String res=null;
                res= new String(res1);//????????????????????????
                Fileoperations.writeData2("/storage/emulated/0/TransmitData/", filename+"??????",res);//???


            }else if((buffer[0]==0x55)&&(buffer[1]==0x01)){//????????????stm32????????????

                switch (buffer[2]){
                    case 0x11://??????????????????
                        Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case 0x22://??????????????????
                        Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case 0x33://??????????????????????????????
                        Toast.makeText(MainActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case 0x44://?????????????????????
                        if (stopboolean){
                        Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                        stopboolean=false;
                        imagestop.setImageDrawable(getResources().getDrawable(R.drawable.my_v));
                            imageyou.setEnabled(false);
                            imageqian.setEnabled(false);
                            imagezuo.setEnabled(false);
                        }else {
                            Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                            stopboolean=true;
                            imagestop.setImageDrawable(getResources().getDrawable(R.drawable.my_c));
                            imageyou.setEnabled(true);
                            imageqian.setEnabled(true);
                            imagezuo.setEnabled(true);
                        }
                        break;
                }

            }
           //B=readMessage.toCharArray();
//        for(int i=0;i<Bit;i++){
//            gain[i]=(float) buffer[i];
//        }
//        for(int i=0;i<Bit;i++){
//            Buf[i]=buffer[i];
//        }



        //???????????????TXT???
            //int i=B.length;
        MyAdapter adapter = new MyAdapter(MainActivity.this,getdata());
            listView.setAdapter(adapter);
            mReceiveDataShow.append(readMessage);
            countin += readMessage.length();  // ????????????????????????
            mReceiveCount.setText("????????????" + countin + "???");
//        if (!Fileoperations.fileIsExists("/storage/emulated/0/TransmitData/" + filename + ".txt")) {
            //writeData??????????????????????????????????????????????????????
            Fileoperations.writeData("/storage/emulated/0/TransmitData/", filename, "??????" + readMessage);


 //       }

    }//????????????-??????????????????
    public class Tcp_client extends Thread {

        private static final String TAG = "Tcp_client";
        private Handler      mhandler;
        private Socket       socket;
        private boolean      isruning;
        public  InputStream  inputStream;
        public  OutputStream outputStream;
        private InetAddress  inetAddress;                               //IP??????
        private int          port;                                      //?????????
        private              Tcp_client tcp_client                 = null;
        private final static int        CLIENT_STATE_CORRECT_READ  = 7;
        public final static  int        CLIENT_STATE_CORRECT_WRITE = 8;               //??????????????????
        public final static  int        CLIENT_STATE_ERROR         = 9;                 //????????????????????????
        public final static  int        CLIENT_STATE_IOFO          = 10;                  //??????SOCKET??????
        private              boolean    client_islink              = false;
        public Tcp_client(Handler mhandler) {
            this.mhandler = mhandler;
            isruning = true;
        }

        public void setInetAddress(InetAddress inetAddress) {
            this.inetAddress = inetAddress;
        }

        public void setPort(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            if (socket == null) {
                try {
                    Log.e(TAG, "??????????????????");
                    Log.e(TAG, "inetAddress   " + inetAddress);
                    Log.e(TAG, "port   " + port);

                    socket = new Socket(inetAddress, port);
                    new Receive_Thread(socket).start();
                    getadress();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "e.printStackTrace()   " + e);
                    senderror();
                }
            }
        }

        public void getadress() {
            String[] strings = new String[2];
            strings[0] = socket.getInetAddress().getHostAddress();
            strings[1] = socket.getInetAddress().getHostName();
            Message message = mhandler.obtainMessage(CLIENT_STATE_IOFO, -1, -1, strings);
            mhandler.sendMessage(message);
        }

        public void close() {
            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                    isruning = false;
                } catch (IOException e) {
                }
            } else if (socket == null) {
                Log.e(TAG, "???????????????");
            }
        }

        class Receive_Thread extends Thread {

            private Socket msocket;

            public Receive_Thread(Socket msocket) {
                this.msocket = msocket;
            }

            @Override
            public void run() {
                try {
                    while (isruning) {
                        inputStream = msocket.getInputStream();
                        while (inputStream.available() == 0) {
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        final byte[] buffer = new byte[1024];//?????????????????????

                        final int len = inputStream.read(buffer);//???????????????????????????????????????
                        mhandler.sendMessage(mhandler.obtainMessage(CLIENT_STATE_CORRECT_READ, len, -1, buffer));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "e  " + e);
                    senderror();

                } finally {
                    if (msocket != null) {
                        try {
                            msocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "???????????????????????????");
                }
            }
        }

        public void sendmessage(final byte[] message) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        outputStream = socket.getOutputStream();
                        mhandler.sendMessage(mhandler.
                                obtainMessage(CLIENT_STATE_CORRECT_WRITE, -1, -1, message));
                        outputStream.write(message);

                    } catch (IOException e) {
                        senderror();
                    }
                }
            }).start();

        }

        void senderror() {
            Log.e(TAG, "senderror()   ");
            mhandler.sendMessage(mhandler.obtainMessage(CLIENT_STATE_ERROR));
        }
    }
    public void setText(String d) {
        if (d != null) {
            Toast.makeText(this, d, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * ???????????????WIFI???IP  ??????
     * @param k
     */
    public void setInt(int k) {
        if (k != -1) {
            if (k == 2) {
                if(mips.isEnabled()) {
                    mips.setText(getLocalIpAddress());   //??????WIFI IP????????????}
                }
                Toast.makeText(MainActivity.this, "wifi", Toast.LENGTH_SHORT).show();
            } else if (k == 0) {
                if(mips.isEnabled()){
                mips.setText("0.0.0.0");
                }
                Toast.makeText(MainActivity.this, "??????", Toast.LENGTH_SHORT).show();
            } else if (k == 1) {
                mips.setText(getHostIP());//????????????IP????????????
                Toast.makeText(MainActivity.this, "??????", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static String getHostIP() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"192.168.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            //Log.i(TAG, "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }
    private void updateTab(final TabHost tabHost) {//tab????????????????????????
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(15);
            tv.setTypeface(Typeface.SERIF, 0); // ?????????????????????
            if (tabHost.getCurrentTab() == i) {//??????
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_grey));//??????????????????
                tv.setTextColor(this.getResources().getColorStateList(
                        android.R.color.black));
            } else {//?????????
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_white));//??????????????????
                tv.setTextColor(this.getResources().getColorStateList(
                        android.R.color.darker_gray));
            }
        }
    }
    private void getLocationAccessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION);
        }
    }
    private String getLocalIpAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // ??????32?????????IP??????
        int ipAddress = wifiInfo.getIpAddress();
        //Log.i(TAG, "ipAddress   " + ipAddress);
        //??????????????????????????????*.*.*.*?????????
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tcp_client != null) {
            tcp_client.close();
            tcp_client = null;
        }
        if (tcp_service != null) {
            tcp_service.setis_start(false);
            tcp_service.close();
            tcp_service = null;
        }
        //mBind.unbind();
    }
//    @Override
//    public void onBackPressed() {
//        exit();
//    }
//    public void exit() {
//        if (exit) {
//            this.finish();
//        }
//        exit = true;
//        Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "????????????????????????",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    public String getWIFISSID(Activity activity) {
//        String ssid = "unknown id";
//
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
//
//            WifiManager mWifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//
//            assert mWifiManager != null;
//            WifiInfo info = mWifiManager.getConnectionInfo();
//
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//                return info.getSSID();
//            } else {
//                return info.getSSID().replace("\"", "");
//            }
//        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.BASE) {
//
//            ConnectivityManager connManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//            assert connManager != null;
//            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
//            if (networkInfo.isConnected()) {
//                if (networkInfo.getExtraInfo() != null) {
//                    return networkInfo.getExtraInfo().replace("\"", "");
//                }
//            }
//        }
//        return ssid;
//    }

}




