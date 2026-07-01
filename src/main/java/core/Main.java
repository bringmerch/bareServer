package core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import core.record.Constants;

/**
 *
 * Package Name: core
 * File Name: Main
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */

public class Main {
    public static void main(String[] args) {
        final String CRLF = Constants.CRLF.getValue();

        System.out.println("program started...");

        // 8080포트에 소켓 생성
        try (ServerSocket serverSocket = new ServerSocket(8080)) { // serverSocket = 8080 포트에서 연결 요청을 기다림
            while (!serverSocket.isClosed()) {
                System.out.println("listening started on port 8080...");

                // accept() : 대기하다가 요청 수신 시 backlog queue에서 연결 꺼내서 새로운 Socket 객체 반환
                Socket clientSocket = serverSocket.accept();

                // 10초동안 입력 없으면 SocketTimeoutException 발생
                clientSocket.setSoTimeout(10000);

                // 요청별로 쓰레드 & 클라이언트소켓 생성 (블로킹)
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (SocketTimeoutException e) {
            System.out.println("SocketTimeoutException occurred...");
        } catch (IOException e) {
            System.out.println("ServerSocket creation failed: " + e.getMessage());
        }
    }
}