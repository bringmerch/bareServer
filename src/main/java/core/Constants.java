package core;

/**
 *
 * Package Name: core
 * File Name: Constants
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */
public enum Constants {
    CRLF("\r\n"),
    SPACE(" "),
    EXECUTE("execute");

    private final String value;

    public String getValue() {
        return value;
    }

    Constants(String value) {
        this.value = value;
    }
}
