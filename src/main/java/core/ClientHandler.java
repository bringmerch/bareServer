package core;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    public void handle(final Socket clientSocket, final ApplicationContext applicationContext) throws BareException {
        if (clientSocket == null)
            throw new BareException(500, "handle failed: clientSocket is empty");
        if (applicationContext == null)
            throw new BareException(500, "handle failed: applicationContext is empty");

        InputStream inputStream = null; // bufferedReader 닫을 때 같이 닫힘
        OutputStream outputStream = null; // bufferedWriter 닫을 때 같이 닫힘

        try {
            inputStream = clientSocket.getInputStream(); // null 리턴 안 함
            outputStream = clientSocket.getOutputStream(); // null 리턴 안 함

            applicationContext.getDispatcher()
                              .dispatch(inputStream, outputStream);
        } catch (Exception e) {
            System.out.println("client handling failed: " + e.getMessage()); // 에러페이지 반환도 실패했으면 로그찍고 무시
        } finally {
            ResourceCloser.close(inputStream);
            ResourceCloser.close(outputStream);
        }
    }
}
