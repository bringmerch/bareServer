package core;

import java.io.IOException;
import java.io.OutputStream;

public class ErrorWorker implements Worker {
    @Override
    public void process(WorkOrder workOrder, OutputStream outputStream) throws BareException, IOException {
        if (workOrder == null)
            throw new IllegalArgumentException("ErrorWorker failed: workOrder is empty.");

        sendResponse(ErrorResponse.create(workOrder.getStatusCode()), outputStream);
    }
}
