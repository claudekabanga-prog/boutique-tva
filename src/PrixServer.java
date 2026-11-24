import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class PrixServer {

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        HttpServer server = start(port);
        System.out.println("Boutique TVA " + version() + " ecoute sur le port " + port);
    }

    static String version() {
        String v = System.getenv("APP_VERSION");
        return v == null ? "dev" : v;
    }

    // Demarre le serveur (utilise aussi par les tests HTTP)
    static HttpServer start(int port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            int status = 200;
            String body;
            try {
                String[] p = path.split("/");
                if (path.equals("/")) {
                    body = "Boutique TVA " + version() + " is running";
                } else if (path.equals("/health")) {
                    body = "OK";
                } else if (p.length == 4 && p[1].equals("ttc")) {
                    body = format(Prix.ttc(Double.parseDouble(p[2]), Double.parseDouble(p[3])));
                } else if (p.length == 4 && p[1].equals("remise")) {
                    body = format(Prix.remise(Double.parseDouble(p[2]), Double.parseDouble(p[3])));
                } else if (p.length == 4 && p[1].equals("total")) {
                    body = format(Prix.total(Double.parseDouble(p[2]), Integer.parseInt(p[3])));
                } else {
                    status = 404;
                    body = "not found";
                }
            } catch (IllegalArgumentException e) {
                status = 400;
                body = "erreur: " + e.getMessage();
            }
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
        server.start();
        return server;
    }

    private static String format(double v) {
        return String.format(Locale.US, "%.2f", v);
    }
}