package nl.loxia.raildocs.raildocs;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Tiemen on 24-6-2014.
 */
public class TestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        String requestString = httpRequest.getURI().toString();
        requestString = requestString.replace("%25%257B", "%7B").replace("%25%257D", "%7D");
        try {
            URI uri = new URI(requestString);
            HttpRequest newHttpRequest = new SimpleClientHttpRequestFactory().createRequest(uri, httpRequest.getMethod());
            newHttpRequest.getHeaders().setAll(httpRequest.getHeaders().toSingleValueMap());
            return execution.execute(newHttpRequest, bytes);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null; // error
    }
}
