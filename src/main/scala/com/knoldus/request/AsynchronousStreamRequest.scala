package com.knoldus.request

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest, HttpResponse, StatusCodes, Uri}
import akka.http.scaladsl.model.headers.Location
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import com.knoldus.models.Models.User
import com.knoldus.models.Protocols
import spray.json._

class AsynchronousStreamRequest extends Protocols {
  implicit val system = ActorSystem("LowLevelServerAPI_Asynchronous_Stream_Request")
  implicit val materializer = ActorMaterializer()

  val user = User("Kuldeepak", 22, "kuldeepak.gupta@knoldus.com")

  def asynchronousRequest = {
    val handler: Flow[HttpRequest, HttpResponse, _] = Flow[HttpRequest].map{
      case HttpRequest(HttpMethods.GET, Uri.Path("/"), _, _, _) =>
        HttpResponse(
          StatusCodes.OK,
          entity = HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            "This is Techhub Template!"
          )
        )

      case HttpRequest(HttpMethods.GET, Uri.Path("/session"), _, _, _) =>
        HttpResponse(
          entity = HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            """
              |<html>
              | <body>
              |   Low Level Server Api
              | </body>
              |</html>
          """.stripMargin
          )
        )

      case HttpRequest(HttpMethods.GET, Uri.Path("/blogs"), _, _, _) =>
        HttpResponse(
          StatusCodes.Found,
          headers = List(Location("https://blog.knoldus.com/author/kuldeepakgupta/"))
        )

      case HttpRequest(HttpMethods.GET,Uri.Path("/user"),_,_,_) =>
        HttpResponse(
          StatusCodes.Found,
          entity = HttpEntity(
            ContentTypes.`application/json`,
            user.toJson.prettyPrint
          )
        )

      case HttpRequest(HttpMethods.GET, uri@Uri.Path("/users/user"),_,_,_) =>
        val query = uri.query()
        val userName = query.get("name").getOrElse("default")
        HttpResponse(
          entity = HttpEntity(
            ContentTypes.`application/json`,
            user.copy(name = userName).toJson.prettyPrint
          )
        )


      case request: HttpRequest =>
        request.discardEntityBytes()
        HttpResponse(
          StatusCodes.NotFound,
          entity = HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            "OOPS, Wrong Path !! Keep Trying"
          )
        )
    }

    Http().bindAndHandle(handler, "localhost", 8010)
  }
}
