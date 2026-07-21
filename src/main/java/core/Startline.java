package core;

import java.util.HashMap;
import java.util.List;

/**
 *
 * Package Name: core
 * File Name: Startline
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-21
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-21        munke                   최초개정
 */
public record Startline (Method method,
                         HashMap<String, String> query,
                         String path){
}
