package ru.dargen.rest;

import com.google.gson.JsonObject;
import ru.dargen.rest.annotation.RequestHeader;
import ru.dargen.rest.annotation.RequestMapping;
import ru.dargen.rest.annotation.RequestPath;
import ru.dargen.rest.annotation.parameter.Authorization;
import ru.dargen.rest.annotation.parameter.Path;
import ru.dargen.rest.client.RestClient;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Test {

    public static void main(String[] args) throws Throwable {
        RestClient client = RestClientFactory.createHttpBuiltinClient();
        testImage(client);
        testDocker(client);
    }

    public static void testImage(RestClient client) throws Throwable {
        GitHubController controller = client.createController(GitHubController.class);

        InputStream inputStream = controller.getResource("fluidicon.png");
        Files.copy(inputStream, Paths.get("fluidicon.png")); //save image to project dir
    }

    @RequestMapping("https://github.com/")
    interface GitHubController {

        InputStream getResource(@Path String resource);

    }

    public static void testDocker(RestClient client) {
        DockerController controller = client.createController(DockerController.class);

        String token = new String(Base64.getEncoder().encode(
                System.getenv("BASE_AUTH_TOKEN").getBytes(StandardCharsets.UTF_8)));

        System.out.println(controller.ping(token)); //OUT: OK

        JsonObject container = controller.containers(token, "ea69ca739bad"); //Request nginx container info
        System.out.println(container
                .getAsJsonObject("HostConfig")
                .getAsJsonObject("PortBindings")
                .keySet().iterator().next()
        ); //OUT: 81/tcp
    }


    @RequestMapping("http://localhost:81")
    @RequestHeader(key = "User-Agent", value = "Docker-API")
    interface DockerController {

        @RequestPath("/_ping")
        String ping(@Authorization("Basic") String auth);

        @RequestPath("/containers/{container}/json")
        JsonObject containers(
                @Authorization("Basic") String auth,

                @Path("container") String containerId
        );

    }


}
