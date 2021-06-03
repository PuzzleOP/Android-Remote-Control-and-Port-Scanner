package com.example.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

public class SamsungRemote
{

    private final int PORT = 55000;
    private final int SO_TIMEOUT = 3 * 1000; // Socket connect and read timeout in milliseconds.
    private final int SO_AUTHENTICATE_TIMEOUT = 300 * 1000; // Socket read timeout while authenticating (waiting for user response) in milliseconds.
    private final String APP_STRING = "iphone.iapp.samsung";

    private final char[] ALLOWED = {0x64, 0x00, 0x01, 0x00}; // TV return payload.
    private final char[] DENIED = {0x64, 0x00, 0x00, 0x00};
    private final char[] TIMEOUT = {0x65, 0x00};


    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final boolean debug;
    private final ArrayList<String> log;


    public SamsungRemote(InetAddress host) throws IOException {
        this(host, false);
    }

    public SamsungRemote(InetAddress host, boolean debug) throws IOException {
        this.debug = debug;
        this.log = new ArrayList<>();
        this.socket = new Socket();
        socket.connect(new InetSocketAddress(host, PORT), SO_TIMEOUT);
        socket.setSoTimeout(SO_TIMEOUT);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public SamsungRemote(String host) throws IOException {
        this(host, false);
    }


    public SamsungRemote(String host, boolean debug) throws IOException {
        this.debug = debug;
        this.log = new ArrayList<>();
        this.socket = new Socket();
        socket.connect(new InetSocketAddress(host, PORT), SO_TIMEOUT);
        socket.setSoTimeout(SO_TIMEOUT);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public TVReply authenticate(String name) throws IOException {
        String hostAddress = socket.getLocalAddress().getHostAddress();

        return authenticate(hostAddress, hostAddress, name);
    }


    public TVReply authenticate(String id, String name) throws IOException {
        String hostAddress = socket.getLocalAddress().getHostAddress();

        return authenticate(hostAddress, id, name);
    }


    public TVReply authenticate(String ip, String id, String name)
            throws IOException {
        emptyReaderBuffer(in);

        log("Authenticating with ip: " + ip + ", id: " + id + ", name: " + name + ".");
        out.write(0x00);
        writeString(out, APP_STRING);
        writeString(out, getAuthenticationPayload(ip, id, name));
        out.flush(); // Send authentication.

        socket.setSoTimeout(SO_AUTHENTICATE_TIMEOUT);
        char[] payload = readRelevantMessage(in);
        socket.setSoTimeout(SO_TIMEOUT);

        if (Arrays.equals(payload, ALLOWED)) {
            log("Authentication response: access granted.");
            return TVReply.ALLOWED; // Access granted.
        } else if (Arrays.equals(payload, DENIED)) {
            log("Authentication response: access denied.");
            return TVReply.DENIED; // Access denied.
        } else if (Arrays.equals(payload, TIMEOUT)) {
            log("Authentication response: timeout.");
            return TVReply.TIMEOUT; // Timeout.
        }
        log("Authentication message is unknown.");
        throw new IOException("Got unknown response.");
    }


    public void keycode(Keycode keycode) throws IOException {
        keycode(keycode.name());
    }


    public void keycode(String keycode) throws IOException {
        emptyReaderBuffer(in);

        log("Sending keycode: " + keycode + ".");
        out.write(0x00);
        writeString(out, APP_STRING);
        writeString(out, getKeycodePayload(keycode));
        out.flush();

        readMessage(in);
    }

    public void keycodeAsync(Keycode keycode) throws IOException {
        keycodeAsync(keycode.name());
    }


    public void keycodeAsync(String keycode) throws IOException {
        log("Sending keycode without reading: " + keycode + ".");
        out.write(0x00);
        writeString(out, APP_STRING);
        writeString(out, getKeycodePayload(keycode));
        out.flush(); // Send key code.
    }


    public void checkConnection() throws IOException {
        keycode("PING");
    }


    private String getAuthenticationPayload(String ip, String id, String name)
            throws IOException {
        StringWriter writer = new StringWriter();
        writer.write(0x64);
        writer.write(0x00);
        writeBase64(writer, ip);
        writeBase64(writer, id);
        writeBase64(writer, name);
        writer.flush();
        return writer.toString();
    }


    private String getKeycodePayload(String keycode) throws IOException {
        StringWriter writer = new StringWriter();
        writer.write(0x00);
        writer.write(0x00);
        writer.write(0x00);
        writeBase64(writer, keycode);
        writer.flush();
        return writer.toString();
    }


    private char[] readRelevantMessage(Reader reader) throws IOException {
        char[] payload = readMessage(reader);
        while (payload[0] == 0x0a) {
            log("Message is not relevant, waiting for new message.");
            payload = readMessage(reader);
        }
        return payload;
    }


    private char[] readMessage(Reader reader) throws IOException {
        int first = reader.read();
        if (first == -1) {
            throw new IOException("End of stream has been reached (TV could have powered off).");
        }
        String response = readString(reader);
        char[] payload = readCharArray(reader);
        log("Message: first byte: " + Integer.toHexString(first) + ", response: " + response + ", payload: " + readable(payload));
        return payload;
    }


    private String readable(char[] charArray) {
        String readable = Integer.toHexString(charArray[0]);
        for (int i = 1; i < charArray.length; i++) {
            readable += " " + Integer.toHexString(charArray[i]);
        }
        return readable;
    }


    private void writeString(Writer writer, String string) throws IOException {
        writer.write(string.length());
        writer.write(0x00);
        writer.write(string);
    }


    private void writeBase64(Writer writer, String string) throws IOException {
        String base64 = new String(Base64.encodeBase64(string.getBytes()));
        writeString(writer, base64);
    }


    private String readString(Reader reader) throws IOException {
        return new String(readCharArray(reader));
    }


    private char[] readCharArray(Reader reader) throws IOException {
        int length = reader.read();
        reader.read();
        char[] charArray = new char[length];
        reader.read(charArray);
        return charArray;
    }


    private void emptyReaderBuffer(Reader reader) throws IOException {
        log("Emptying reader buffer.");
        while (reader.ready()) {
            readMessage(reader);
        }
    }


    public String[] getLog() {
        return log.toArray(new String[log.size()]);
    }


    private void log(String message) {
        if (debug) {
            String time = (System.currentTimeMillis() % 1000) + ""; // Time is current milliseconds between 0 and 1000.
            while (time.length() < 3) {
                time = " " + time;
            }
            log.add(time + ". " + message);
        }
    }


    public void close() {
        log("Closing socket connection.");
        try {
            socket.close();
        } catch (IOException e) {
            log("IOException when closing connection: " + e.getMessage());
        }
    }
}
