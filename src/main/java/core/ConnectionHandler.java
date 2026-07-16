package core;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.List;
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

        try {
            DataProcessor dataProcessor = new DataProcessor(clientSocket);
            Request request = dataProcessor.readCommon();
            Worker worker = this.getWorker(request);
            try {
                worker.execute(request, dataProcessor); // IOException 캐치 왜 안 함 ? ?
            } catch (IOException e ) {

            }

            dataProcessor.close();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException | IOException e) {
            e.printStackTrace();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("cannot close clientSocket.");
        }
    }

    private Worker getWorker(Request request) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String path = request.getPath();

        Class workerClass = Container.getWorker(path);
        if (workerClass == null) {
            System.out.println("worker not found....illegal path.");
            request.setResponseStatusCode(404);
            return new HTMLWorker();
        }

        List<Method> allowMethods = Container.getMethods(path);
        if (allowMethods == null || !allowMethods.contains(request.getMethod())) {
            System.out.println("method now allowed");
            request.setResponseStatusCode(405);
            return new HTMLWorker();
        }


        Object workerInstance = workerClass.getDeclaredConstructor().newInstance();
        return (Worker)workerInstance;
    }
}
