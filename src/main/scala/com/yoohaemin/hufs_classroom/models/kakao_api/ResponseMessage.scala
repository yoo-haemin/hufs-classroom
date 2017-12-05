package com.yoohaemin.hufs_classroom.models.kakao_api

import ResponseMessage.{MessageButton, Photo}
import org.http4s.Uri

case class ResponseMessage private(text: Option[String], second: Option[Photo], third: Option[MessageButton]) {
  def this(first: String) = this(Some(first), None, None)
  def this(second: Photo) = this(None, Some(second), None)
  def this(third: MessageButton) = this(None, None, Some(third))

  def this(first: String, second: Photo) = this(Some(first), None, None)
  def this(second: Photo, third: MessageButton) = this(None, Some(second), Some(third))
  def this(first: String, third: MessageButton) = this(Some(first), None, Some(third))

  def this(first: String, second: Photo, third: MessageButton) = this(Some(first), Some(second), Some(third))
}

object ResponseMessage {
  case class Photo(url: Uri, width: Int, height: Int)
  case class MessageButton(label: String, url: Uri)
}