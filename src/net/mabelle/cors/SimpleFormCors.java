package net.mabelle.cors;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class SimpleFormCors {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server is running on port 8081");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                handleGetRequest(exchange);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                handlePostRequest(exchange);
            }
        }

        private void handleGetRequest(HttpExchange exchange) throws IOException {
            String response = "<html><head><title>Simple Form</title></head><body>" +
                    "<h1>Simple Form</h1>" +
                    "<form action=\"/submit\" method=\"post\">" +
                    "Name: <input type=\"text\" name=\"name\"><br>" +
                    "Email: <input type=\"text\" name=\"email\"><br>" +
                    "<input type=\"submit\" value=\"Submit\">" +
                    "</form>" +
                    "</body></html>";
            // 使用 exchange.getResponseHeaders().set 方法來設置 CORS 相關的 HTTP header，
            // 例如 Access-Control-Allow-Origin，Access-Control-Allow-Methods 等，
            // 以允許跨來源的請求。這樣就能夠實現 CORS 功能了。
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private void handlePostRequest(HttpExchange exchange) throws IOException {
            String response = "Successfully!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
