package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


case class ResponseApi(userKey: String, messageType: String, content: String)

object ResponseApi {
  implicit val format: Format[ResponseApi] = (
    (__ \ "user_key").format[String] and
      (__ \ "type").format[String] and
      (__ \ "content").format[String]
  )(ResponseApi.apply _, unlift(ResponseApi.unapply))
}
