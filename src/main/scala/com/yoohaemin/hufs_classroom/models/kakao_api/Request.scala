package com.yoohaemin.hufs_classroom.models.kakao_api

import org.http4s.Uri

sealed abstract class Request(userKey: String)
final case class TextRequest(userKey: String, content: String) extends Request(userKey)
final case class PhotoRequest(userKey: String, content: Uri) extends Request(userKey)
