package core.body;

import core.type.ContentType;

import java.io.File;
import java.util.Map;

/**
 *
 * Package Name: core.body
 * File Name: TemplateBody
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.body
 * @since 2026-08-05
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-05        munke                   최초개정
 */
public class TemplateBody extends FileBody {
    private static final ContentType defaultContentType = ContentType.TEXT_HTML;
    private Map<String, String> model;

    public TemplateBody(File content, Map<String, String> model) {
        super(content, defaultContentType);
        this.model = model;
    }

    public Map<String, String> getModel() {
        return model;
    }
}
