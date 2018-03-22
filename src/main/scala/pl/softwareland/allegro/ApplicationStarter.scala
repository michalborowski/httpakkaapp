package pl.softwareland.allegro

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, RequestEntity}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import pl.softwareland.allegro.restclient.RepositoryClientRest
import pl.softwareland.allegro.service.RepositoryService

import scala.io.StdIn
import scala.util.{Failure, Success}

object ApplicationStarter extends App {

  implicit val system = ActorSystem("allegro_system")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher

  val route = RepositoryService.getRepositoryService

  val bindingFuture = Http().bindAndHandle(route, config.getString("http.interface"), config.getInt("http.port"))

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
