package typeclasses.example

import typeclasses.example.JSONApi.{JSONArray, JSONNumber, JSONObject, JSONString, JSONValue}

object JSONManager {

  trait JSONConverter[T] {
    def convert(value: T): JSONValue
  }

  // Conversion class
  implicit class JSONOps[T](value : T) {
    def toJSON(implicit converter: JSONConverter[T]): JSONValue = converter.convert(value)
  }

  // Instances

  // Existing types
  implicit object StringConverter extends JSONConverter[String] {
    def convert(value: String): JSONValue = JSONString(value)
  }

  implicit object NumberConverter extends JSONConverter[Int] {
    def convert(value: Int): JSONValue = JSONNumber(value)
  }

  // Custom data types
  implicit object UserConverter extends JSONConverter[User] {
    def convert(value: User): JSONValue = JSONObject(Map(
      "name" -> JSONString(value.name),
      "age" -> JSONNumber(value.age),
      "email" -> JSONString(value.email)
    ))
  }

  implicit object PostConverter extends JSONConverter[Post] {
    def convert(value: Post): JSONValue = JSONObject(Map(
      "content" -> JSONString(value.content),
      "createdAt" -> JSONString(value.createdAt.toString)
    ))
  }

  implicit object FeedConverter extends JSONConverter[Feed] {
    def convert(value: Feed): JSONValue = JSONObject(Map(
      //"user" -> UserConverter.convert(value.user),
      "user" -> value.user.toJSON,
      "posts" -> JSONArray(value.posts.map(_.toJSON))
    ))
  }
}


