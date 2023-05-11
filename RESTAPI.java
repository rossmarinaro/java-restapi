import java.io.*;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;


public class RESTAPI {


    public static void main(String[] args) throws IOException {

        int port = 8080;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler());

        server.createContext("/api/greeting", (exchange -> {

            if ("GET".equals(exchange.getRequestMethod())) {

                System.out.println("request at made /api/greeting");

                String responseText = "Sup Yo.\n";

                exchange.sendResponseHeaders(200, responseText.getBytes().length);

                OutputStream output = exchange.getResponseBody();

                output.write(responseText.getBytes());
                output.flush();

            } 
            else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));


        System.out.println("Server running on port " + port);

        server.setExecutor(null); // creates a default executor
        server.start();

    }

    public static class RootHandler implements HttpHandler {

        @Override 
        public void handle(HttpExchange t) throws IOException { 
            String root = "/var/www/"; 
           // URI uri = t.getRequestURI(); 
            File file = new File("index.html"/* root + uri.getPath() */).getCanonicalFile(); 
      /*       if (!file.getPath().startsWith(root)) { 
                // Suspected path traversal attack: reject with 403 error. 
                String response = "403 (Forbidden)\n"; 
                t.sendResponseHeaders(403, response.length()); 
                OutputStream os = t.getResponseBody(); 
                os.write(response.getBytes()); 
                os.close(); 
            } else if (!file.isFile()) { 
                // Object does not exist or is not a file: reject with 404 error. 
                String response = "404 (Not Found)\n"; 
                t.sendResponseHeaders(404, response.length());
                OutputStream os = t.getResponseBody(); 
                os.write(response.getBytes()); 
                os.close(); 
            } else { // Object exists and is a file: accept with response code 200.  */
                t.sendResponseHeaders(200, 0); 
                OutputStream os = t.getResponseBody(); 
                FileInputStream fs = new FileInputStream(file); 
                final byte[] buffer = new byte[0x10000]; 
                int count = 0; 
                while ((count = fs.read(buffer)) >= 0) { 
                    os.write(buffer,0,count); 
                } 
                fs.close(); 
                os.close(); 
            //} 
        }
    }

}


