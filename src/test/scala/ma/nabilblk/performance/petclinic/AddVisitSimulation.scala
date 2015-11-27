package ma.nabilblk.performance.petclinic

import ma.nabilblk.performance.petclinic.config.Config
import ma.nabilblk.performance.petclinic.request.{OwnerRequest, VisitRequest}
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AddVisitSimulation extends Simulation {

  val scn = scenario("AddVisitSimulation")
    .exec(OwnerRequest.ShowLanding)

    .pause(Config.THINK_TIME_DEFAULT_SECONDS)

    .repeat(Config.REPEAT_COUNT) {
      exec(OwnerRequest.ListAllByLastName)

        .pause(Config.THINK_TIME_DEFAULT_SECONDS)

        .exec(OwnerRequest.GetRandomOwner)

        .pause(Config.THINK_TIME_DEFAULT_SECONDS)

        .exec(VisitRequest.NavigateToNewVisitForm)

        .pause(Config.THINK_TIME_DEFAULT_SECONDS)

        .exec(VisitRequest.AddNewVisit)
    }

  setUp(scn.inject(constantUsersPerSec(Config.USER_PER_SEC) during (Config.DURATION seconds)))
    .protocols(Config.HTTP_PROTOCOL)
}