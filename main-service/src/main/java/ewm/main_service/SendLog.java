package ewm.main_service;


import com.fasterxml.jackson.databind.ObjectMapper;
import ewm.Entityes.Log;
import ewm.Errors.InternalServerErrorException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

    public class SendLog {
        private final HttpClient client;
        private final ObjectMapper objectMapper;

        public SendLog() {
            this.client = HttpClient.newHttpClient();
            this.objectMapper = new ObjectMapper();
        }

        public void sendPost(Log log) {
            URI uri = URI.create("http://localhost:9090/hit");
            try {
                String json = objectMapper.writeValueAsString(log);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 201) {
                    System.out.println("Статистика сохранена");
                } else {
                    throw new InternalServerErrorException("Ошибка сервера статистики.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
