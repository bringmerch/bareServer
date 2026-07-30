package core;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler {
    public void handle(final Socket clientSocket) throws BareException {
        if (clientSocket == null)
            throw new BareException(500, "handle failed: clientSocket is empty");

        InputStream inputStream; // bufferedReader 닫을 때 같이 닫힘
        OutputStream outputStream = null; // bufferedWriter 닫을 때 같이 닫힘
        BufferedReader bufferedReader = null;

        try {
            inputStream = clientSocket.getInputStream(); // null 리턴 안 함
            outputStream = clientSocket.getOutputStream(); // null 리턴 안 함
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // null 리턴 안 함

            RequestReader requestReader = new RequestReader();
            Request request = requestReader.readRequest(bufferedReader);
            Resource resource = Resource.findByPathAndMethod(request.getPath(), request.getMethod());
            Worker worker = resource.getWorkerInstance();
            WorkOrder workOrder = new WorkOrder(resource.getResourcePath(), resource.getContentType());
            worker.execute(workOrder, outputStream);

            //////// TODO 아래 한 줄처럼 줄이고 RequestReader에 다 넣기 or RequestHandler로 감싸서 그 안에서 하기
//            RequestReader.readRequestAndGetWorker(bufferedReader).execute();
        } catch (BareException e) {
            Worker.executeErrorWorker(e.getStatusCode(), outputStream);
        } catch (IllegalArgumentException e) {
            Worker.executeErrorWorker(400, outputStream);
        } catch (IOException e) {
            Worker.executeErrorWorker(500, outputStream);
        } finally {
            ResourceCloser.close(bufferedReader);
            ResourceCloser.close(outputStream);
        }
    }
}
