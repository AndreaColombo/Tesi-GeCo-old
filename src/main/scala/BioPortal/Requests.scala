package BioPortal

import Utils.Utils._
import scalaj.http._

object Requests {
  val apikey = "2338fb64-0246-4627-bf4d-4197bc8c9c64"
  val url = "http://data.bioontology.org/"

  def annotator (txt: String) : String = {
    var response = filterJSON(Http(url+"annotator").params(Seq("text" -> txt, "apikey" -> apikey)).asString.toString)
    return response
  }

  def recommender (txt: String): String = {
    var response = filterJSON(Http(url+"recommender").params(Seq("text" -> txt, "apikey" -> apikey)).asString.toString)
    return response
  }

  def recommender (keywords: List[String]): String = {
    var response = Http(url+"recommender").params(Seq("apikey" -> apikey)).asString.toString

    return response
  }

}
