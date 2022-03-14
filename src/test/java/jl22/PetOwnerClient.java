package jl22;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class PetOwnerClient extends Simulation {

  HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://116.203.207.10:8080/") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

  ScenarioBuilder scn = scenario("load simulation 2022") // A scenario is a chain of requests and pauses
    .exec(http("open_find")
      .get("/owners/find"))
    //.pause(7) // Note that Gatling has recorded real time pauses
    .exec(http("find_all")
      .get("/owners?lastName="))
    //.pause(2)
    .exec(http("find_page_2")
      .get("/owners/?page=2"))
    //.pause(3)
    .exec(http("read_owner")
      .get("/owners/2"))
    //.pause(2)
    .exec(http("edit_owner")
      .get("/owners/2/edit"))
    //.pause(Duration.ofMillis(670))
          .exec(http("post_owner")
                  .post("/owners/2/edit")
                  .formParam("firstName", "Betty")
                  .formParam("lastName", "Davis")
                  .formParam("address", "638 Cardinal Ave.")
                  .formParam("city", "Sun Prairie")
                  .formParam("telephone", "6085551749")
          )
    //.pause(Duration.ofMillis(629))
    .exec(http("list_vets_2")
      .get("/vets.html/?page=2"));
    //.pause(Duration.ofMillis(734))

    {
        setUp(scn.injectOpen(
                rampUsersPerSec(1).to(100).during(60)
        ).protocols(httpProtocol));
    }
}
