import com.google.gson.JsonObject;
import ru.dargen.rest.RestClientFactory;
import ru.dargen.rest.annotation.RequestHeader;
import ru.dargen.rest.annotation.RequestMapping;
import ru.dargen.rest.annotation.RequestPath;
import ru.dargen.rest.annotation.parameter.Authorization;
import ru.dargen.rest.annotation.parameter.Path;
import ru.dargen.rest.client.RestClient;

public class Test {

    public static void main(String[] args) throws Throwable {
        RestClient client = RestClientFactory.createHttpBuiltinClient();
        DockerController controller = client.createController(DockerController.class);

        String token = System.getenv("BASE_AUTH_TOKEN");

        System.out.println(controller.ping(token)); //OUT: OK

        JsonObject container = controller.containers(token, "ea69ca739bad"); //Request nginx container info
        System.out.println(container
                .getAsJsonObject("HostConfig")
                .getAsJsonObject("PortBindings")
                .keySet().iterator().next()
        ); //OUT: 81/tcp

    }


    @RequestMapping("http://localhost:81")
    @RequestHeader(key = "User-Agent", value = "Rest-Test")
    static interface DockerController {

        @RequestPath("/_ping")
        String ping(@Authorization("Basic") String auth);

        @RequestPath("/containers/{container}/json")
        JsonObject containers(
                @Authorization("Basic") String auth,

                @Path("container") String containerId
        );

    }

}
