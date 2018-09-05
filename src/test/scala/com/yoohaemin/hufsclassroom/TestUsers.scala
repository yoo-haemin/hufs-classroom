package com.yoohaemin.hufsclassroom

import com.yoohaemin.hufsclassroom.model.{Email, User, UserName}

object TestUsers {

  val users = List(
    User(UserName("gvolpe"), Email("gvolpe@github.com")),
    User(UserName("tpolecat"), Email("tpolecat@github.com")),
    User(UserName("msabin"), Email("msabin@github.com"))
  )

}
