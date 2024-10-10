import java.io.*;
import java.net.PortUnreachableException;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
    Socket socket;
    DataOutputStream dataOutputStream;

    Client(int port_number) throws IOException {
        this.socket = new Socket("localhost", port_number);
        dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
    }

    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.next();
            try {
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
                if(message.equals("finished")){
                    socket.close();
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client c1 = new Client(12345);
        Client c2 = new Client(12345);
        Client c3 = new Client(12345);
        c1.start();
        c2.start();
        c3.start();

    }
}
