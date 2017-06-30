package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


case class RequestApi(userKey: String, messageType: String, content: String)

object RequestApi {
  implicit val format: Format[RequestApi] = (
    (__ \ "user_key").format[String] and
      (__ \ "type").format[String] and
      (__ \ "content").format[String]
  )(RequestApi.apply _, unlift(RequestApi.unapply))
}
