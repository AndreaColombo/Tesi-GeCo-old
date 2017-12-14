package UMLS

import scalaj.http._

object Requests {
  val url = "https://uts-ws.nlm.nih.gov/rest"

  def getCUI (st: String, searchterm: String) : String = {
    val searchURL = "/search/current"
    return Http(url+searchURL).params(Seq("string"->searchterm,"ticket"->st)).asString.body
  }

  def getCUIAtoms(st: String, uri: String) : String =  {
    val searchUrl = "/content/current/CUI/"+uri
    return Http(url+searchUrl).params(Seq("ticket"->st)).asString.body
  }

  def getSemantics(st: String, uri: String) : String = {
    return Http(uri).params(Seq("ticket"->st)).asString.body
  }
}
