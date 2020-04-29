package bank.chat.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Connection implements Runnable {
    ChatServer server = null;
    private Socket communicationSocket = null;
    private OutputStreamWriter out = null;
    private BufferedReader in = null;

    public Connection(ChatServer server, Socket socket) {
        this.server = server;
        this.communicationSocket = socket;
    }

    public void sendMessage(String message) {
        try {
            out.write(message);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        OutputStream socketOutput = null;
        InputStream socketInput = null;
        String magic = server.getMagicPassphrase();

        try {
            socketOutput = communicationSocket.getOutputStream();
            out = new OutputStreamWriter(socketOutput);
            socketInput = communicationSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(socketInput));

            InetAddress address = communicationSocket.getInetAddress();
            String hostname = address.getHostName();

            String welcome = "Connection made from host: " + hostname + "\nEverybody say hello";
            String client = server.getClientName(hostname);
            if (client != null) {
                welcome += " to " + client;
            }
            welcome += "!\n";
            server.sendToAllClients(welcome);
            System.out.println("Connection made " + client + "@" + hostname);
            sendMessage("Welcome " + client + " the passphrase is " + magic + "\n");
            String input = null;

            while ((input = in.readLine()) != null) {
                if (input.indexOf(magic) != -1) {
                    server.playMagicSound();
                    sendMessage("Congratulations " + client + " you sent the passphrase!\n");
                    System.out.println(client + " sent the passphrase!");
                } else {
                    server.sendToAllClients(input + "\n");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                communicationSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            server.closeConnection(this);
        }
    }
}
