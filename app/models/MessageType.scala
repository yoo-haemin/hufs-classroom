/*
package models

import play.api.libs.json._
import scala.language.implicitConversions

sealed abstract class MessageType {
  override implicit def toString() = this match {
    case MessageType.Text  => "text"
    case MessageType.Photo => "photo"
  }
}

object MessageType {

  final object Text extends MessageType
  final object Photo extends MessageType

  implicit def fromString(s: String): MessageType = s match {
    case "text"  => MessageType.Text
    case "photo" => MessageType.Photo
    case _ => throw new NoSuchElementException
  }
}
 */
