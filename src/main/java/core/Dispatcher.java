package core;

import core.interceptor.InterceptorRegistry;
import core.model.Request;
import core.type.Resource;
import core.worker.Worker;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * Package Name: core
 * File Name: Dispatcher
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-30
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-30        munke                   최초개정
 */
public class Dispatcher {
    public static void dispatch(Request request, OutputStream outputStream) throws BareException, IOException {
        InterceptorRegistry.runPreHandles();
        Resource resource = Resource.find(request.getPath(), request.getMethod());
        Worker worker = resource.createWorker();
        worker.execute(
            new WorkOrder(request.getHeaderMap(), request.getQueryMap(), resource.getResourcePath(), resource.getContentType()),
            outputStream
        );
    }
}
