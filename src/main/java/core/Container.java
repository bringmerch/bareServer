package core;

import java.lang.reflect.InvocationTargetException;

public enum Container {
    API_001("/index", Method.GET, "/static/html/index.html", StaticFileWorker.class, ContentType.TEXT_HTML),
    API_002("/hello", Method.GET, "/static/html/hello.html", StaticFileWorker.class, ContentType.TEXT_HTML),
    API_003("/balance", Method.GET, "/static/text/balance.txt", StaticFileWorker.class, ContentType.TEXT_PLAIN),
    API_004("/panda", Method.GET, "/static/images/jpeg/panda.jpeg", StaticFileWorker.class, ContentType.IMAGE_JPEG);

    private final String path;
    private final Method method;
    private final String resourcePath;
    private final Class<? extends Worker> worker;
    private final ContentType contentType;


    Container(String path, Method method, String resourcePath, Class<? extends Worker> worker, ContentType contentType) {
        this.path = path;
        this.method = method;
        this.resourcePath = resourcePath;
        this.worker = worker;
        this.contentType = contentType;
    }

    public static Container findByPathAndMethod(String path, Method method) throws BareException {
        if (path == null || path.isBlank())
            throw new BareException(404, "Container from() failed. path is empty.");

        for (Container container : Container.values()) {
            if (container.path.equalsIgnoreCase(path) && container.method.equals(method))
                return container;
        }

        throw new BareException(404, "Container from() failed. no such route.");
    }

    public Worker getWorkerInstance() throws BareException {
        try {
            return this.worker
                .getDeclaredConstructor()
                .newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BareException(500, "newWorker() failed.", e);
        }
    }

    public String getResourcePath() {
        return this.resourcePath;
    }

    public ContentType getContentType() {
        return this.contentType;
    }
}

