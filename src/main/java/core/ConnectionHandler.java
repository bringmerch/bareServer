package core;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.function.Consumer;

/**
 *
 * Package Name: core
 * File Name: ConnectionHandler
 * Description:
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
public class ConnectionHandler implements Consumer<Socket> {
    @Override
    public void accept(Socket clientSocket) {
        System.out.println("run....");
        Request request = new Request();

        try {
            Class workerClass = Container.get(request.getPath());
            java.lang.reflect.Method method = workerClass.getMethod(Constants.EXECUTE.getValue());
            Object workerInstance = workerClass.getDeclaredConstructor().newInstance();
            method.invoke(workerInstance);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("cannot close clientSocket.");
        }
    }
}
