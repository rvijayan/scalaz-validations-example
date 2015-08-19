package com.scalaz.model

import org.joda.time.DateTime

case class Destination(
  id: Int,
  name: String,
  code: String,
  country: String)

case class Trip(
  id: Int,
  userId: Long,
  fromDate: DateTime,
  toDate: DateTime,
  destinations: Seq[Destination],
  description: String,
  amendedDate: DateTime)