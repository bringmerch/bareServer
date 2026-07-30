package core.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * Package Name: core
 * File Name: HeaderMap
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
    public HeaderMap(){
        super();
    }

    HeaderMap(Map<String, String> initMap) {
        this();
        for (Map.Entry<String, String> entry : initMap.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
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
    public String put(String key, String value) {
        String newValue = value;
        String cleanKey = key.trim().toLowerCase();

        if (super.containsKey(cleanKey))
            // 키 이미 있으면 원래 값에 append한다.
            newValue = super.get(cleanKey) + "," + newValue;

        return super.put(cleanKey, newValue);
    }
}