package com.knoldus.models

import com.knoldus.models.Models.User
import spray.json.DefaultJsonProtocol

object Models {

  final case class User (name: String, age: Int, email: String)

}

trait Protocols extends DefaultJsonProtocol {
  implicit val UserFormat = jsonFormat3(User)
}