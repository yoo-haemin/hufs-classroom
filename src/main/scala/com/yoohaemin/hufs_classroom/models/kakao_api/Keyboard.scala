package com.yoohaemin.hufs_classroom.models.kakao_api

import cats.data.NonEmptyList

sealed abstract class Keyboard
final case class Buttons(buttons: NonEmptyList[String]) extends Keyboard
case object Text extends Keyboard
