package core;

import core.model.Request;

import java.io.*;
import java.lang.management.BufferPoolMXBean;
import java.net.Socket;

public class ClientHandler {
    public void handle(final Socket clientSocket, final ApplicationContext applicationContext) throws BareException {
        if (clientSocket == null)
            throw new BareException(500, "handle failed: clientSocket is empty");
        if (applicationContext == null)
            throw new BareException(500, "handle failed: applicationContext is empty");

        BufferedReader bufferedReader = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream()); // null 리턴 안 함

            // 요청읽기
            Request request = RequestReader.readRequest(bufferedReader);
            // 수행
            applicationContext.getDispatcher()
                              .dispatch(request, bufferedOutputStream);
        } catch (Exception e) {
            System.out.println("client handling failed: " + e.getMessage()); // 에러페이지 반환도 실패했으면 로그찍고 무시
        } finally {
            ResourceCloser.close(bufferedReader);
            ResourceCloser.close(bufferedOutputStream);
        }
    }
}
