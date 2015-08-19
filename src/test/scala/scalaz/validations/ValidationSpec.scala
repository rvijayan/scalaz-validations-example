package scalaz.validations

import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}
import scalaz._

import com.scalaz.model.{Destination, Trip}
import com.scalaz.validation._

class ValidationSpec extends FlatSpec with Matchers {

  private val fromDate = new DateTime("2015-02-03T14:15:00.000+08:00")
  private val toDate = new DateTime("2015-02-10T14:15:00.000+08:00")

  it should "return an error if wrong dateformat is entered" in {
    val Failure(result) = Rules.validateDate("12-10-2015")

    result.message shouldBe "Incorrect date format"
  }

  it should "return the validated date if the validation is success" in {
    val Success(result) = Rules.validateDate("12/10/2015")

    result.toString("dd/mm/yyyy") shouldBe "12/10/2015"
  }

  it should "return errors if validation fails" in {
    val trip = Trip(1, 1, fromDate, toDate, Seq.empty[Destination], "", DateTime.now)

    val Failure(result) = Validators.validateTrip(trip)

    result.size shouldBe 2
    result.head.message shouldBe "Empty description"
    result.last.message shouldBe "There should be atleast one destination"
  }

  it should "return a valid trip object is the validation is successful" in {
    val destinations = Seq(
      Destination(1, "Amsterdam", "AMS", "Netherlands"),
      Destination(2, "Barcelona", "BCN", "Spain")
    )
    val trip = Trip(1, 1, fromDate, toDate, destinations, "Summer holidays", DateTime.now)

    val Success(result) = Validators.validateTrip(trip)

    result shouldBe trip
  }
}
