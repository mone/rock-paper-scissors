package rockpaperscissors.models

sealed trait Result

/**
  * Simple case objects highlighting the result of a fight
  */
object Result {
  case object Win extends Result
  case object Lose extends Result
  case object Draw extends Result
}
