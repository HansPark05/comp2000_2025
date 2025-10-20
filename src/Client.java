import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://13.238.167.130/weather"))
                .header("Accept", "text/event-stream")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            // strip SSE "data:" prefix if present
                            if (line.startsWith("data:")) {
                                line = line.substring(5).trim();
                            }

                            // split on one or more whitespace characters
                            String[] parts = line.trim().split("\\s+");

                            // safe access with length checks
                            String timestamp = parts.length > 0 ? parts[0] : "(missing)";
                            String attribute = parts.length > 1 ? parts[1] : "(missing)";
                            String x = parts.length > 2 ? parts[2] : "(missing)";
                            String y = parts.length > 3 ? parts[3] : "(missing)";
                            String value = parts.length > 4 ? parts[4] : "(missing)";

                            System.out.println("Received: " + line);
                            System.out.println("Parsed fields:");
                            System.out.println("  Timestamp: " + timestamp);
                            System.out.println("  Attribute: " + attribute);
                            System.out.println("  X-Coordinate: " + x);
                            System.out.println("  Y-Coordinate: " + y);
                            System.out.println("  Value: " + value);
                            System.out.println();
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading Server Side Event (SSE) stream: " + e.getMessage());
                    }
                })
                .join(); // Wait for the async operation to complete
    }
}