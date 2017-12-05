package com.yoohaemin.hufs_classroom.services

import com.yoohaemin.hufs_classroom.models.kakao_api.Friend
import cats.effect.IO
import doobie.implicits._

object FriendService {
  def add(friend: Friend): IO[Boolean] =
    sql"""
      INSERT INTO friends      
      VALUES $friend;
    """
      .query[Boolean]
      .unique
      .transact(xa)
    
  def remove(friend: Friend): IO[Unit] = 
    sql"""
      
    """
      .query[Unit]
      .unique
      .transact(xa)
}