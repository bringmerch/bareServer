package core;

import core.model.*;

import java.io.*;

/**
 *
 * Package Name: core
 * File Name: Dispatcher
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-30
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-30        munke                   최초개정
 */
public class Dispatcher {
    ApplicationContext applicationContext;

    public Dispatcher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void dispatch(InputStream inputStream, OutputStream outputStream) throws IOException {
        Response response = new Response();
        BufferedReader bufferedReader = null;
        try {
            // 요청읽기
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Request request = RequestReader.readRequest(bufferedReader);
            // 인터셉터
            if (!this.applicationContext.getInterceptorRegistry().doInterceptors(request, response))
                throw new BareException(500, "intercept failed: interceptor returned true.");
            // 컨트롤러
            Handler.handle(this.applicationContext.getHandlerMapping(), request, response);
            // 응답
            new Writer().writeResponse(response, outputStream);
        } catch (BareException e) {
            System.out.println("dispatch failed: " + e.getMessage());
            new ErrorHandlerImpl().serveErrorPage(e.getStatusCode(), response);
        } catch (Exception e) {
            System.out.println("dispatch failed: " + e.getMessage());
            new ErrorHandlerImpl().serveErrorPage(500, response);
        } finally {
            ResourceCloser.close(bufferedReader);
        }
    }
}
