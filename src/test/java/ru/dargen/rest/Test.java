package ru.dargen.rest;

import ru.dargen.rest.annotation.RequestHeader;
import ru.dargen.rest.annotation.RequestMapping;
import ru.dargen.rest.annotation.RequestPath;
import ru.dargen.rest.annotation.util.RecomputeController;
import ru.dargen.rest.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Test {

    public static void main(String[] args) throws Throwable {
        RestClient client = RestClientFactory.createHttpBuiltinClient();
        testDocker(client);
    }

    public static void testDocker(RestClient client) {
        DockerController controller = client.createController(DockerController.class);

        try {
            System.out.println(controller.ping()); //cause exception with code 401
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        client.getBaseRequest().withHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode(
                System.getenv("BASE_AUTH_TOKEN").getBytes(StandardCharsets.UTF_8))));
        controller.recompute();

        System.out.println(controller.ping()); //successful - making authorization in base request and recompute
    }


    @RequestMapping("http://localhost:81")
    @RequestHeader(key = "User-Agent", value = "Docker-API")
    interface DockerController {

        @RequestPath("/_ping")
        String ping();

        @RecomputeController
        void recompute();

    }


}
