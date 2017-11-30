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
    var tgt = Http(url+authEndpoint).postForm(Seq("apikey"->apikey)).asString.toString()
    val pattern = """form action="https://utslogin.nlm.nih.gov/cas/v1/api-key/TGT-([0-9-A-Z-a-z]+)""".r
    val pattern2 = "https://utslogin.nlm.nih.gov/cas/v1/api-key/TGT-([0-9-A-Z-a-z]+)".r
    tgt = pattern2.findAllIn(pattern.findAllIn(tgt).mkString).mkString

    return tgt
  }

  def getST(tgt: String, apikey: String) : String = {
    var st = Http(tgt).postForm(Seq("apikey"->apikey,"service"->"http://umlsks.nlm.nih.gov")).asString.toString()
    val pattern = "ST-([0-9-A-Z-a-z]+)".r
    st = pattern.findAllIn(st).mkString
    return st
  }

}