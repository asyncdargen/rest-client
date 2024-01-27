import ru.dargen.rest.RestClientFactory;
import ru.dargen.rest.annotation.JsonQuery;
import ru.dargen.rest.annotation.RequestHeader;
import ru.dargen.rest.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class JsonTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(RestClientFactory.createHttpBuiltinClient().createController(Requester.class).request().whenComplete((r, t) ->{
            if (t != null) t.printStackTrace();
            System.out.println(r);
        }).get());
    }

    @RequestHeader(key = "User-Agent", value = "Test")
    @RequestMapping("https://api.starfarm.fun/realms")
    public static interface Requester {

        @JsonQuery("online")
        public CompletableFuture<Integer> request();

    }

}
