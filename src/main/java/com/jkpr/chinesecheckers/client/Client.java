package com.jkpr.chinesecheckers.client;

import com.jkpr.chinesecheckers.client.boards.AbstractBoardClient;
import com.jkpr.chinesecheckers.client.boards.factory.CCBoardFactory;
import com.jkpr.chinesecheckers.client.boards.factory.YYBoardFactory;
import com.jkpr.chinesecheckers.server.gamelogic.Move;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class Client {
    private Socket socket;
    private Scanner scanner;
    private PrintWriter out;
    private Scanner in;
    private AbstractBoardClient board;
    private Consumer<AbstractBoardClient> onBoardGenerated;
    private Runnable onBoardUpdate;

    public Client() {
        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(socket.getInputStream());
            scanner = new Scanner(System.in); // do czytanie wpisu z konsoli klienta
            System.out.println("polaczono z serwerem");
        } catch (IOException e) {
            System.err.println("blad polaczenia z serwerem: " + e.getMessage());
        }
    }
    public static void main(String[] args){
        Client client = new Client();
        client.start();
    }
    public void start() {
        new Thread(this::receiveMessages).start();
        //handleUserInput();
    }
    public void sendMessage(String input){
        out.println(input);
        out.flush();
    }
    public void setOnBoardGenerated(Consumer<AbstractBoardClient> onBoardGenerated) {
        this.onBoardGenerated = onBoardGenerated;
    }
    public void setOnBoardUpdate(Runnable onBoardUpdate) {
        this.onBoardUpdate = onBoardUpdate;
    }


    private void receiveMessages() {
        while (in.hasNextLine()) {
            String linia = in.nextLine();
            System.out.println("odebrano: " + linia);
            String[] message=linia.split(" ");
            switch (message[0]){
                case "GEN":
                    switch (message[1])
                    {
                        case "CC":
                            board=new CCBoardFactory().generate(Integer.parseInt(message[3]),Integer.parseInt(message[2]));
                            break;
                        case "YY":
                            int enemy=Integer.parseInt(message[2]);
                            int id=Integer.parseInt(message[3]);
                            board=new YYBoardFactory().generate(id,enemy);
                            break;
                    }
                    if(onBoardGenerated!=null){
                        System.out.println("wygenerowano plansze w cliencie");
                        onBoardGenerated.accept(board);
                    }
                    break;
                case "UPDATE":
                    switch (message[1]){
                        case "SKIP":
                            board.processNext(Integer.parseInt(message[3]));
                            break;
                        case "FAIL":
                            System.out.println("niepoprawny ruch");
                            break;
                        default:
                            Move move=new Move(
                                    Integer.parseInt(message[1]),
                                    Integer.parseInt(message[2]),
                                    Integer.parseInt(message[3]),
                                    Integer.parseInt(message[4]));
                            board.makeMove(move);
                            board.processNext(Integer.parseInt(message[6]));
                            int i=8;
                            while(i<message.length){
                                board.processWin(Integer.parseInt(message[i]));
                                i++;
                            }
                    }
                    if(onBoardUpdate != null){
                        onBoardUpdate.run();
                    }
                    break;
            }
        }
    }
}
