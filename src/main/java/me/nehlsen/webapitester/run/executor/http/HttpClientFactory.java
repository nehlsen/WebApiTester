package me.nehlsen.webapitester.run.executor.http;

import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Component
public class HttpClientFactory {
    public HttpClient createClient() {
        return HttpClient.newBuilder().build();
    }
}
