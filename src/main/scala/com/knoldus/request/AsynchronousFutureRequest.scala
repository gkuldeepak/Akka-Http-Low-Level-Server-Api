package com.knoldus.request

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest, HttpResponse, StatusCodes, Uri}
import akka.http.scaladsl.model.headers.Location
import akka.stream.ActorMaterializer
import com.knoldus.models.Models.User
import com.knoldus.models.Protocols
import spray.json._

import scala.concurrent.Future

class AsynchronousFutureRequest extends Protocols {
  implicit val system = ActorSystem("LowLevelServerAPI_Asynchronous_Future_Request")
  implicit val materializer = ActorMaterializer()

  val user = User("Kuldeepak", 22, "kuldeepak.gupta@knoldus.com")

 def asynchronousRequest = {
   val handler: HttpRequest => Future[HttpResponse] = {
     case HttpRequest(HttpMethods.GET, Uri.Path("/"), _, _, _) =>
       Future.successful(HttpResponse(
         StatusCodes.OK,
         entity = HttpEntity(
           ContentTypes.`text/html(UTF-8)`,
           "This is Techhub Template!"
         )
        )
       )


     case HttpRequest(HttpMethods.GET, Uri.Path("/session"), _, _, _) =>
       Future.successful(HttpResponse(
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
       )

     case HttpRequest(HttpMethods.GET, Uri.Path("/blogs"), _, _, _) =>
       Future.successful(HttpResponse(
         StatusCodes.Found,
         headers = List(Location("https://blog.knoldus.com/author/kuldeepakgupta/"))
       )
       )

     case HttpRequest(HttpMethods.GET,Uri.Path("/user"),_,_,_) =>
       Future.successful(HttpResponse(
         StatusCodes.Found,
         entity = HttpEntity(
           ContentTypes.`application/json`,
           user.toJson.prettyPrint
         )
       )
       )

     case HttpRequest(HttpMethods.GET, uri@Uri.Path("/users/user"),_,_,_) =>
       val query = uri.query()
       val userName = query.get("name").getOrElse("default")
       Future.successful(HttpResponse(
         entity = HttpEntity(
           ContentTypes.`application/json`,
           user.copy(name = userName).toJson.prettyPrint
         )
       )
       )


     case request: HttpRequest =>
       request.discardEntityBytes()
       Future.successful(HttpResponse(
         StatusCodes.NotFound,
         entity = HttpEntity(
           ContentTypes.`text/html(UTF-8)`,
           "OOPS, Wrong Path !! Keep Trying"
         )
       )
       )
   }

   Http().bindAndHandleAsync(handler, "localhost", 8009)
 }
}

