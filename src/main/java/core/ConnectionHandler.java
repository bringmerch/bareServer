package core;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler {
    public void handle(final Socket clientSocket) throws BareException {
        if (clientSocket == null)
            throw new BareException(500, "handle failed: clientSocket is empty");
        InputStream inputStream; // bufferedReader 닫을 때 같이 닫힘
        OutputStream outputStream = null; // bufferedWriter 닫을 때 같이 닫힘
        BufferedReader bufferedReader = null; // TODO bufferedReader,Writer만들 때 null 안 됨
        BufferedWriter bufferedWriter = null;

        try {
            if ((inputStream = clientSocket.getInputStream()) == null)
                throw new IOException("handle failed: inputStream is empty");
            if ((outputStream = clientSocket.getOutputStream()) == null)
                throw new IOException("handle failed: outputStream is empty");

            RequestReader requestReader = new RequestReader();
            // 경로에 맞는 워커를 찾기 위해 요청을 읽는다.
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
            ResourceCloser.close(bufferedWriter);
        }
    }
}
