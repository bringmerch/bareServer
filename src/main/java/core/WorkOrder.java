package core;

import core.model.HeaderMap;
import core.type.ContentType;

import java.util.Map;

/**
 *
 * Package Name: core
 * File Name: WorkOrder
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-22
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-22        munke                   최초개정
 */
public class WorkOrder {
    private int statusCode;
    HeaderMap headerMap;
    Map<String, String> queryMap;
    String resourcePath;
    ContentType contentType;

    WorkOrder(HeaderMap headerMap, Map queryMap, String resourcePath, ContentType contentType) {
        this.headerMap = headerMap;
        this.queryMap = queryMap;
        this.resourcePath = resourcePath;
        this.contentType = contentType;
    }

    public WorkOrder(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getResourcePath(){
        return this.resourcePath;
    }

    public ContentType getContentType() {
        return this.contentType;
    }
}
