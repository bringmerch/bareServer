package core;

import java.lang.reflect.InvocationTargetException;

public enum Route {
    API_001("/index", Method.GET, "/static/html/index.html", StaticFileWorker.class, ContentType.TEXT_HTML),
    API_002("/hello", Method.GET, "/static/html/hello.html", StaticFileWorker.class, ContentType.TEXT_HTML),
    API_003("/panda", Method.GET, "/static/images/jpeg/panda.jpeg", StaticFileWorker.class, ContentType.IMAGE_JPEG);

    private final String path;
    private final Method method;
    private final String resourcePath;
    private final Class<? extends Worker> worker;
    private final ContentType contentType;


    Route(String path, Method method, String resourcePath, Class<? extends Worker> worker, ContentType contentType) {
        this.path = path;
        this.method = method;
        this.resourcePath = resourcePath;
        this.worker = worker;
        this.contentType = contentType;
    }

    public Route findByPath(String path) {
        if (path == null || path.isBlank())
            throw new IllegalArgumentException("Route from() failed. path is empty.");

        for (Route route : Route.values()) {
            if (route.path.equalsIgnoreCase(path))
                return route;
        }

        throw new IllegalArgumentException("Route from() failed. no such route.");
    }

    public Worker newWorker() throws BareException {
        try {
            return this.worker
                .getDeclaredConstructor(String.class, ContentType.class)
                .newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BareException(500, "newWorker() failed.", e);
        }
    }
}

