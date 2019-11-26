package typeclasses.example

object JSONApi {

  sealed trait JSONValue {
    def stringify: String
  }

  final case class JSONString(value: String) extends JSONValue {
    override def stringify: String = "\"" + value + "\""
  }

  final case class JSONNumber(value: Int) extends JSONValue {
    override def stringify: String = value.toString
  }

  final case class JSONArray(values: List[JSONValue]) extends JSONValue {
    override def stringify: String = values.map(_.stringify).mkString("[", ", ", "]")
  }

  final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue {
    override def stringify: String =  // -> "key" : value or "value" or ["","",""] or etc
      values.map {
        case (key: String, value: JSONValue) => "\"" + key + "\":" + value.stringify }
      .mkString("{", ",","}")
  }

}
