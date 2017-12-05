package com.yoohaemin.hufs_classroom.services

import com.yoohaemin.hufs_classroom.models.kakao_api.Friend
import cats.effect.IO
import doobie.implicits._

object FriendService {
  def add(key: String): IO[Unit] =
    IO {
      Friend(key)
    } flatMap { f =>
     sql"""
       INSERT INTO friends IF NOT EXISTS
       VALUES $f;
     """
      .query[Unit]
      .unique
      .transact(xa)
    }

    
  def remove(friend: Friend): IO[Unit] = 
    sql"""
      DELETE FROM friends
      WHERE id = ${friend.id};
    """
      .query[Unit]
      .unique
      .transact(xa)
}