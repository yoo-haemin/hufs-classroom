package com.yoohaemin.hufsclassroom

import shapeless.tag.@@

package object model {

  type UserIdTag
  type UserId = String @@ UserIdTag

}
