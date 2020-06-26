package hello.world;

import java.time.Duration;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.reactivex.Flowable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import reactor.core.publisher.Mono;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;

import javax.annotation.Nullable;

@Controller("/hello")
public class HelloController {

    @Get("/infinite")
    public Mono<MutableHttpResponse<Object>> infinite() {
        return Mono.fromSupplier(() -> HttpResponse.ok()).delayElement(Duration.ofSeconds(10L)).doOnError(Throwable::printStackTrace).doOnCancel(() -> System.out.println("CANCELLED")).doFinally(System.out::println);
        //return Flux.interval(Duration.ofSeconds(2L)).doOnError(Throwable::printStackTrace).doOnCancel(() -> System.out.println("CANCELLED")).doFinally(System.out::println);
    }

    @Post(uris = {"/{id}/load", "/load/{id}"})
    @Operation(security = {}, description = "Loads data: \n```json\n{\n  \"a\": \"tttt\"\n}\n```", summary = "Returns a Dummy")
    public HttpResponse<Void> loadData(final long id,
                                       @Parameter(description = "Flag to indicate whether or not to replace existing items")
                                       @QueryValue
                                       final Boolean replaceExisting) {
        return HttpResponse.accepted();
    }

    @Operation(description = "Returns A dummy", summary = "Returns a Dummy")
    @ApiResponse(responseCode = "200", description = "Returns a dummy", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class))))
    @ApiResponse(responseCode = "500", description = "Internal Error")
    public Flowable<String> dummyFlowable() {
        return Flowable.just("ffff");
    }

    @Get("/{id}{/language}")
    public String getContract(String id, @Nullable String language) {
      return null;
    }
}
