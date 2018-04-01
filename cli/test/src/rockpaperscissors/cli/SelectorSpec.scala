package rockpaperscissors.cli

import rockpaperscissors.BaseSpec

import scala.util.{Failure, Success, Try}
import org.mockito.Mockito

class SelectorSpec extends BaseSpec {

  behavior of "ConsoleSelectorUtil"

  it should "keep trying until it pass validity check" in {

    val read = mock[() => Try[Int]]

    Mockito.when(read()).thenReturn(
      Success(2), // invalid
      Failure(new Exception("test")), //failure
      Success(1) //valid!
    )

    ConsoleSelectorUtil.readUntilValidSelection(
      read,
      Map(
        1 -> 1
      )
    ) should be (1)

    Mockito.verify(read, Mockito.times(3)).apply()

  }

}
