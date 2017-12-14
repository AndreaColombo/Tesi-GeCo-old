package UMLS

import scalaj.http._

object Auth {

  val url = "https://utslogin.nlm.nih.gov/"
  var authEndpoint = "cas/v1/api-key"

  def SelectAuthMode (mode: String) = {
    val moder = "ApiKey"

    if (moder == "ApiKey") {
      authEndpoint = "cas/v1/api-key"
    } else {
      authEndpoint = "cas/v1/tickets"
    }
  }

  def getTGT (apikey: String) : String = {
    val r = Http(url+authEndpoint).postForm(Seq("apikey"->apikey)).asString.body
    val pattern = "https://utslogin.nlm.nih.gov/cas/v1/api-key/TGT-([0-9-A-Z-a-z]+)".r
    return pattern.findAllIn(r).mkString
  }

  def getST(tgt: String, apikey: String) : String = {
    var st = Http(tgt).postForm(Seq("apikey"->apikey,"service"->"http://umlsks.nlm.nih.gov")).asString.body
    val pattern = "ST-([0-9-A-Z-a-z]+)".r
    st = pattern.findAllIn(st).mkString
    return st
  }

}