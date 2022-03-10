package com.john.myapplication;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 项目名称:    TCP_SC_Demo
 * 创建者:      Danniu
 * 创建时间:    2018/5/31 10:42
 * 描述:        TODO:
 * 包名:        com.danniu.myapp.thread
 * <p>
 * 更新者：     $$Author$$
 * 更新时间：   $$Date$$
 * 更新描述：   ${TODO}
 */
public class Tcp_service extends Thread {
    TextView T ;
   private static final String TAG = "Tcp_service";
   private Handler      mhandler;
   private ServerSocket serverSocket;
   public  InputStream  Ser_inputStream;
   public  OutputStream Ser_outputStream;
   private Socket       Ser_Socket;
   private int          mport;
   private       boolean is_start                   = true;
   public static int     SERVER_STATE_CORRECT_READ  = 3;
   public static int     SERVER_STATE_CORRECT_WRITE = 4;                 //正常通信信息
   public static int     SERVER_STATE_ERROR         = 5;                 //发生错误异常信息
   public static int     SERVER_STATE_IOFO          = 6;                 //发送SOCKET信息

   public Tcp_service(Handler mhandler, int mport) {
	this.mhandler = mhandler;
	this.mport = mport;
   }

   @Override
   public void run() {

	super.run();
	try {
	   serverSocket = new ServerSocket(mport);

	} catch (IOException e) {
	   e.printStackTrace();
	   send_Error();
	}
	try {
	   while (is_start) {
		Log.e(TAG, "等待客户端连接 is_start=" + is_start);
		Ser_Socket = serverSocket.accept();
		if (Ser_Socket != null) {
		   //启动接收线程
		   Receive_Thread receive_Thread = new Receive_Thread(Ser_Socket);
		   receive_Thread.start();


		}
	   }
	} catch (IOException e) {
	   e.printStackTrace();
	   send_Error();
	}
   }




    class Receive_Thread extends Thread {

	private Socket socket = null;

	public Receive_Thread(Socket socket) {
	   this.socket = socket;
	   getAddress();

	   try {
		Ser_inputStream = socket.getInputStream();
		Ser_outputStream = socket.getOutputStream();
	   } catch (IOException e) {
		e.printStackTrace();
		send_Error();
	   }
	}

	public void getAddress() {
	   InetAddress inetAddress = socket.getInetAddress();
	   String[] strings = new String[2];
	   strings[0] = inetAddress.getHostAddress();
	   strings[1] = inetAddress.getHostName();
	   Message message = mhandler.obtainMessage(SERVER_STATE_IOFO, strings);
	   mhandler.sendMessage(message);
	}

	@Override
	public void run() {
	   super.run();
	   while (is_start) {
		try {
		   while (Ser_inputStream.available() == 0) {
		   }
		   try {
			Thread.sleep(200);
		   } catch (InterruptedException e) {
			e.printStackTrace();
		   }
		   final byte[] buf = new byte[1024];
		   final int len = Ser_inputStream.read(buf);
		   Message message = mhandler.obtainMessage(SERVER_STATE_CORRECT_READ, len, 1, buf);
		   mhandler.sendMessage(message);
		} catch (IOException e) {
		   e.printStackTrace();
		   send_Error();
		}
	   }
	   try {
		if (Ser_inputStream != null) {
		   Ser_inputStream.close();
		}
		if (Ser_outputStream != null) {
		   Ser_outputStream.close();
		}
		if (socket != null) {
		   socket.close();
		   socket = null;
		}
		Log.e(TAG, "断开连接监听 释放资源");

	   } catch (IOException e) {
		e.printStackTrace();
	   }
	}

   }

   public void close() {
	try {
	   if (serverSocket != null) {
		serverSocket.close();
	   }
	   if (Ser_Socket != null) {
		Ser_Socket.close();
	   }
	   Log.e(TAG, "断开连接监听 关闭监听SOCKET");
	} catch (IOException e) {
	   e.printStackTrace();
	}
   }

   //数据写入函数
   public void write(final byte[] buffer) {
	new Thread(new Runnable() {
	   @Override
	   public void run() {
		try {
		   if (Ser_outputStream != null) {
			Ser_outputStream.write(buffer);
			Message er_message = mhandler.
				obtainMessage(SERVER_STATE_CORRECT_WRITE, -1, -1, buffer);
			mhandler.sendMessage(er_message);
		   } else {
			send_Error();
		   }
		} catch (IOException e) {
		   e.printStackTrace();
		   send_Error();
		}
	   }
	}).start();

   }

   public void send_Error() {
	Message er_message = mhandler.obtainMessage(SERVER_STATE_ERROR);
	mhandler.sendMessage(er_message);
   }

   public void setis_start(boolean is_start) {
	this.is_start = is_start;
   }

	/**
     * 项目名称:    TCP_SC_Demo
     * 创建者:      Danniu
     * 创建时间:    2018/5/31 9:42
     * 描述:        TODO:
     * 包名:        com.danniu.myapp.thread
     * <p>
     * 更新者：     $$Author$$
     * 更新时间：   $$Date$$
     * 更新描述：   ${TODO}
     */

    public static class Tcp_client extends Thread {

       private static final String TAG = "Tcp_client";
       private Handler      mhandler;
       private Socket       socket;
       private boolean      isruning;
       public  InputStream  inputStream;
       public  OutputStream outputStream;
       private InetAddress  inetAddress;                               //IP地址
       private int          port;                                      //端口号
       public static int CLIENT_STATE_CORRECT_READ  = 7;
       public static int CLIENT_STATE_CORRECT_WRITE = 8;               //正常通信信息
       public static int CLIENT_STATE_ERROR         = 9;               //发生错误异常信息
       public static int CLIENT_STATE_IOFO          = 10;              //发送SOCKET信息

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
            Log.e(TAG, "启动连接线程");
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
           Log.e(TAG, "未建立连接");
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
               final byte[] buffer = new byte[1024];//创建接收缓冲区

               final int len = inputStream.read(buffer);//数据读出来，并且数据的长度
               mhandler.sendMessage(mhandler.
                   obtainMessage(CLIENT_STATE_CORRECT_READ, len, -1, buffer));
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
            Log.e(TAG, "关闭连接，释放资源");
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
}
