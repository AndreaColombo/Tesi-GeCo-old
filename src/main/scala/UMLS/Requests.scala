package UMLS

import scalaj.http._

object Requests {
  val url = "https://uts-ws.nlm.nih.gov/rest"

  def filterJSON(string: String) : String = {
    val s = "HttpResponse("
    return string.substring(string.indexOf(s)+s.length, string.indexOf(",200"))

  }

  def getCUI (st: String, searchterm: String) : String = {
    val searchURL = "/search/current"
    var response = Http(url+searchURL).params(Seq("string"->searchterm,"ticket"->st)).asString.toString
    response = filterJSON(response)
    return response
  }

  def getCUIAtoms(st: String, uri: String) : String =  {
    val searchUrl = "/content/current/CUI/"+uri
    return filterJSON(Http(url+searchUrl).params(Seq("ticket"->st)).asString.toString)
  }

  def getSemantics(st: String, uri: String) : String = {
    return filterJSON(Http(uri).params(Seq("ticket"->st)).asString.toString)
  }
}
