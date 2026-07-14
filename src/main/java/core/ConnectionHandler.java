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

        try {
            DataProcessor dataProcessor = new DataProcessor(clientSocket);
            Request request = dataProcessor.readCommon();
            Worker worker = this.getWorker(request);
            worker.execute(request, dataProcessor);
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
        Class workerClass = Container.get(request.getPath());
        if (workerClass == null) {
            System.out.println("worker not found....");
            request.setResponseStatusCode(404);
            return new HTMLWorker();
        }
        Object workerInstance = workerClass.getDeclaredConstructor().newInstance();
        return (Worker)workerInstance;
    }
}
