package core.type;

import core.*;
import core.worker.StaticWorker;
import core.worker.Worker;
import java.util.function.Supplier;

public enum Resource {
    API_001("/index", Method.GET, "/static/html/index.html", StaticWorker::new, ContentType.TEXT_HTML),
    API_002("/hello", Method.GET, "/static/html/hello.html", StaticWorker::new, ContentType.TEXT_HTML),
    API_003("/balance", Method.GET, "/static/text/balance.txt", StaticWorker::new, ContentType.TEXT_PLAIN),
    API_004("/panda", Method.GET, "/static/images/jpeg/panda.jpeg", StaticWorker::new, ContentType.IMAGE_JPEG);

    private final String path;
    private final Method method;
    private final String resourcePath;
    private final Supplier<? extends Worker> workerSupplier;
    private final ContentType contentType;


    Resource(String path, Method method, String resourcePath, Supplier<? extends Worker> workerSupplier, ContentType contentType) {
        this.path = path;
        this.method = method;
        this.resourcePath = resourcePath;
        this.workerSupplier = workerSupplier;
        this.contentType = contentType;
    }

    public String getResourcePath() {
        return this.resourcePath;
    }

    public Worker createWorker() {
        return this.workerSupplier.get();
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public static Resource find(String path, Method method) throws BareException {
        if (path == null || path.isBlank())
            throw new BareException(404, "Resource from() failed. path is empty.");

        for (Resource resource : Resource.values()) {
            if (resource.path.equalsIgnoreCase(path) && resource.method.equals(method))
                return resource;
        }

        throw new BareException(404, "Resource from() failed. no such route.");
    }
}

