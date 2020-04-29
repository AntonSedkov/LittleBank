package bank.chat.server;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {

    public static void main(String[] args) {
        ChatServer cs = new ChatServer();
        cs.go();
    }

    public final static int DEFAULT_PORT = 2000;
    public final static int DEFAULT_MAX_BACKLOG = 5;
    public final static int DEFAULT_MAX_CONNECTIONS = 20;
    public final static String DEFAULT_HOST_FILE = "host.txt";
    public final static String DEFAULT_SOUND_FILE = "file:gong.au";
    public final static String MAGIC = "Yippy Skippy";

    private String magic = MAGIC;

    private int port = DEFAULT_PORT;
    private int backlog = DEFAULT_MAX_BACKLOG;
    private int numConnections = 0;
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;
    private String hostFile = DEFAULT_HOST_FILE;
    private String soundFile = DEFAULT_SOUND_FILE;
    private List<Connection> connections = null;
    private AudioClip connectionSound = null;
    private Map<String, String> hostToClientMap = null;

    public String getMagicPassphrase() {
        return magic;
    }

    public String getClientName(String host) {
        return hostToClientMap.get(host);
    }

    public void sendToAllClients(String message) {
        for (Connection connection : connections) {
            connection.sendMessage(message);
        }
    }

    public void playMagicSound() {
        if (connectionSound != null) {
            connectionSound.play();
        }
    }

    public synchronized void closeConnection(Connection connection) {
        connections.remove(connection);
        numConnections--;
        notify();
    }

    private void go() {
        String portString = System.getProperty("port");
        if (portString != null) {
            port = Integer.parseInt(portString);
        }
        this.port = port;

        String backlogString = System.getProperty("backlog");
        if (backlogString != null) {
            backlog = Integer.parseInt(backlogString);
        }

        String hostFileString = System.getProperty("hostfile");
        if (hostFileString != null) {
            hostFile = hostFileString;
        }

        String soundFileString = System.getProperty("soundfile");
        if (soundFileString != null) {
            soundFile = soundFileString;
        }

        String magicString = System.getProperty("magic");
        if (magicString != null) {
            magic = magicString;
        }

        String connections = System.getProperty("connections");
        if (connections != null) {
            maxConnections = Integer.parseInt(connections);
        }

        this.connections = new ArrayList<>(maxConnections);
        this.hostToClientMap = new HashMap<>(15);


        System.out.println("Server settings:\n\tPort=" + port + "\n\tMax Backlog="
                + backlog + "\n\tMax Connections=" + maxConnections + "\n\tHost File=" + hostFile
                + "\n\tSound file=" + soundFile);

        //createHostList();

        try {
            URL sound = new URL(soundFile);
            connectionSound = Applet.newAudioClip(sound);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        processRequests();
    }

    private void createHostList() {
        File hostFile = new File("hostfile");
        try {
            System.out.println("Attempting to read hostfile hosts.txt: ");
            FileInputStream fis = new FileInputStream(hostFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String sLine = null;
            while ((sLine = br.readLine()) != null) {
                int spaceIndex = sLine.indexOf(' ');
                if (spaceIndex == -1) {
                    System.out.println("Invalid line in host file:" + sLine);
                    continue;
                }
                String host = sLine.substring(0, spaceIndex);
                String client = sLine.substring(spaceIndex + 1);
                System.out.println("Read: " + client + "@" + host);
                hostToClientMap.put(host, client);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void processRequests() {
        ServerSocket serverSocket = null;
        Socket communicationSocket;

        try {
            System.out.println("Attempting to start server...");
            serverSocket = new ServerSocket(port, backlog);
        } catch (IOException ex) {
            System.out.println("Error starting server: could not open port: " + port);
            ex.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Started server on port " + port);

        while (true) {
            try {
                communicationSocket = serverSocket.accept();
                handleConnection(communicationSocket);
            } catch (Exception ex) {
                System.out.println("Unable to spawn child socket.");
                ex.printStackTrace();
            }
        }
    }

    private synchronized void handleConnection(Socket connection) {
        while (numConnections == maxConnections) {
            try {
                wait();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        numConnections++;
        Connection con = new Connection(this, connection);
        Thread thread = new Thread(con);
        thread.start();
        connections.add(con);
    }

}