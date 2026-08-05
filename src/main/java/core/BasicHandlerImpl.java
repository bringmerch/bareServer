package core;

import core.body.FileBody;
import core.body.StringBody;
import core.body.TemplateBody;
import core.model.Request;
import core.model.Response;
import core.session.Session;
import core.type.ContentType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Package Name: core
 * File Name: BasicHandlerImpl
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-08-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-03        munke                   최초개정
 */
public class BasicHandlerImpl extends Handler {
    void getIndex(Request request, Response response) throws BareException, IOException {
        String resourcePath = request.getResourcePath();
        if (resourcePath.isBlank())
            throw new BareException(404, "getFile failed: empty resourcePath.");

        File page = getFile(RESOURCE_LOCATION + request.getResourcePath());

        Map<String, String> model = new HashMap<>();
        Session session = request.getSession();
        if (session != null)
            model.put("b-session-id", session.getSessionId());

        response.setBody(new TemplateBody(page, model));
        response.setStatusCode(200);
    }

    void getFile(Request request, Response response) throws BareException {
        String resourcePath = request.getResourcePath();
        if (resourcePath.isBlank())
            throw new BareException(404, "getFile failed: empty resourcePath.");

        File file = getFile(RESOURCE_LOCATION + request.getResourcePath());

        ContentType contentType = ContentType.getByExtension(resourcePath);

        if (contentType == null)
            throw new BareException(500, "serveHtml failed: cannot determine content type. resourcePath: " + resourcePath + "");

        response.setBody(new FileBody(file, contentType));
        response.setStatusCode(200);
    }

    void getBalance(Request request, Response response) {
        response.setBody(new StringBody("0원입니다.", ContentType.TEXT_PLAIN));
        response.setStatusCode(200);
    }
}
