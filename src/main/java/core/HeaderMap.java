package core;

import java.util.HashMap;
import java.util.Locale;

/**
 *
 * Package Name: core
 * File Name: Header
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-02
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-02        munke                   최초개정
 */
public class HeaderMap extends HashMap<String, String> {
    HeaderMap(){}

    HeaderMap(HashMap<String, String> initMap) {
        super();
        super.putAll(initMap);
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof String))
            throw new IllegalArgumentException("HeaderMap get failed: key isn't String type.");
        if (((String)key).isEmpty())
            throw new IllegalArgumentException("HeaderMap get failed: key is empty.");
        return super.get(((String)key).toLowerCase());
    }

    @Override
    public String put(String fieldName, String fieldValue) {
        String newValue = "";
        if (super.containsKey(fieldName))
            newValue = newValue + "," + super.get(fieldName.toLowerCase(Locale.ROOT));
        else
            return super.put(fieldName.toLowerCase(), fieldValue);
        return super.put(fieldName.toLowerCase(), newValue);

    }
}