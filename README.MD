# Modulo
Java library to simple creating requestes with interface proxies
___
## Add to project
Connect to `jitpack.io` repository
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add dependency `com.github.asyncdargen:rest-client:VERSION`
```groovy
dependencies {
    implementation 'com.github.asyncdargen:rest-client:VERSION'
}
```
___

## Request examples
```java
@RequestHeader(key = "User-Agent", value = "Test")
@RequestMapping("https://example.com")
public static interface Requester {

    @JsonQuery("value")
    @ReqestMapping("test")
    public CompletableFuture<Integer> test();

}
```

```java
RestClient client = RestClientFactory.createHttpBuiltinClient(); 
Requester requester = client.createController(Requester.class);
Requester.test().thenAccept(value -> System.out.println("Response value: %s".formatted(value)));

```