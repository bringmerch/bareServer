package core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
public class Header extends HashMap<String, String> {
    Header(){}

    Header(Map<String, String> initMap) {
        super();
        for (Map.Entry<String, String> entry : initMap.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof String))
            throw new IllegalArgumentException("Header get failed: key isn't String type.");
        if (((String)key).isEmpty())
            throw new IllegalArgumentException("Header get failed: key is empty.");
        return super.get(((String)key).toLowerCase());
    }

    @Override
    public String put(String key, String value) {
        String newValue = "";
        if (super.containsKey(key))
            newValue = newValue + "," + super.get(key.toLowerCase(Locale.ROOT));
        else
            return super.put(key.toLowerCase(), value);
        return super.put(key.toLowerCase(), newValue);

    }
}