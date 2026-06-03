    import java.net.http.*;
import java.net.URI;

public class HttpClientDemo {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/repos/openjdk/jdk"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response body (truncated):\n" + response.body().substring(0, 200));
    }
}
/*
Status code: 200
Response body (truncated):
{"id":149121954,"node_id":"MDEwOlJlcG9zaXRvcnkxNDkxMjE5NTQ=","name":"jdk","full_name":"openjdk/jdk","private":false,"owner":{"login":"openjdk","id":41768318,"node_id":"MDEyOk9yZ2FuaXphdGlvbjQxNzY4MzE4
*/