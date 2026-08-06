package core.interceptor;

import core.routes.ResourceMapping;
import core.model.Request;
import core.model.Response;

/**
 *
 * Package Name: core.interceptor
 * File Name: StaticResourcePathInterceptor
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.interceptor
 * @since 2026-08-04
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-04        munke                   최초개정
 */
public class ResourceMappingInterceptor implements Interceptor {
    private final ResourceMapping resourceMapping;

    public ResourceMappingInterceptor(ResourceMapping resourceMapping) {
        this.resourceMapping = resourceMapping;
    }

    @Override
    public boolean preHandle(Request request, Response response) {
        String resourcePath = resourceMapping.get(request.getPath(), request.getMethodType());

        if (resourcePath != null)
            request.setResourcePath(resourcePath);

        return true;
    }
}
