package core.handlerResult;

import java.io.File;

/**
 *
 * Package Name: core
 * File Name: FileResult
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
public record FileResult(File file) implements HandlerResult {}
