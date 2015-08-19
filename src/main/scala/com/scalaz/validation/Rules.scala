package com.scalaz.validation

import org.joda.time.format.DateTimeFormat
import scala.util.{Try, Success, Failure}
import scalaz._, Scalaz._

case class Fail(message: String)

object Rules {

  type ValidationResult[A] = ValidationNel[Fail, A]

  def isEmpty(message: String)(item: String): ValidationResult[String] =
    if (!item.isEmpty)
      item.success
    else
      Fail(message).failureNel

  def condition[A](m: String)(f: A => Boolean)(item: A): ValidationResult[A] =
    if (!f(item))
      item.success
    else
      Fail(m).failureNel

  def exists[A](m: String)(i: A, items: Seq[A]): ValidationResult[A] =
    if (items.contains(i))
      i.success
    else
      Fail(m).failureNel

  private val DateFormat = DateTimeFormat.forPattern("dd/mm/yyyy")

  def validateDate(d: String) = Try(DateFormat.parseDateTime(d)) match {
    case Success(date) => date.success
    case Failure(_) => Fail("Incorrect date format").failure
  }
}

object Validators {

  import com.scalaz.model._
  import Rules._

  def validateTrip(trip: Trip): ValidationResult[Trip] = (
    isEmpty("Empty description")(trip.description) |@|
    condition[String]("Description should not exceed 250 chars.")(_.length >= 250)(trip.description) |@|
    condition[Seq[Destination]]("There should be atleast one destination")(_.isEmpty)(trip.destinations)
  ) { _ ++ _ ++ _ }.map(_ => trip)
}
