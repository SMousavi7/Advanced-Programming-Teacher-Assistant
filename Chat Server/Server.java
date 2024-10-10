import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;








public class Server extends Thread{
    class clientHandler extends Thread
    {
        Socket socket;
        DataInputStream dataInputStream;
        clientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
        }


        public void run()
        {
            String message = null;
            while(true)
            {
                try {
                    message = dataInputStream.readUTF();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if(message.equals("1"))
                {
                    System.out.println("we just started with this thread and its name is " + Thread.currentThread().getName());
                }

                else if(message.equals("2"))
                {
                    System.out.println("now we do another operation with thread " + Thread.currentThread().getName() + "...");
                }
                else if(message.equals("finished")){
                    System.out.println("program for thread "+ Thread.currentThread().getName()+" finished.");
                    try {
                        socket.close();
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    System.out.println("random operation with " + Thread.currentThread().getName());
                }
            }
        }
    }



    ServerSocket ss;
    Socket s;
    DataInputStream dataInputStream;

    Server(int port) throws IOException {
        ss = new ServerSocket(port);
    }

    @Override
    public void run()
    {
        while(true){
            try {
                s = ss.accept();
                new clientHandler(s).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) throws IOException {
            Server server = new Server(12345);
            server.start();
        }
    }
