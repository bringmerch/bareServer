package core;

import java.lang.reflect.InvocationTargetException;

public enum Resource {
    API_001("/index", Method.GET, "/static/html/index.html", StaticWorker.class, ContentType.TEXT_HTML),
    API_002("/hello", Method.GET, "/static/html/hello.html", StaticWorker.class, ContentType.TEXT_HTML),
    API_003("/balance", Method.GET, "/static/text/balance.txt", StaticWorker.class, ContentType.TEXT_PLAIN),
    API_004("/panda", Method.GET, "/static/images/jpeg/panda.jpeg", StaticWorker.class, ContentType.IMAGE_JPEG);

    private final String path;
    private final Method method;
    private final String resourcePath;
    private final Class<? extends Worker> worker;
    private final ContentType contentType;


    Resource(String path, Method method, String resourcePath, Class<? extends Worker> worker, ContentType contentType) {
        this.path = path;
        this.method = method;
        this.resourcePath = resourcePath;
        this.worker = worker;
        this.contentType = contentType;
    }

    public static Resource findByPathAndMethod(String path, Method method) throws BareException {
        if (path == null || path.isBlank())
            throw new BareException(404, "Resource from() failed. path is empty.");

        for (Resource resource : Resource.values()) {
            if (resource.path.equalsIgnoreCase(path) && resource.method.equals(method))
                return resource;
        }

        throw new BareException(404, "Resource from() failed. no such route.");
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

