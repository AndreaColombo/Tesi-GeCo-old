import java.io.File

import BioPortal.Requests._
import DBcon.con_handler
import play.api.libs.json._
import Utils.Utils.write_to_file

object main extends App{

  val path = "C:/Users/Andrea Colombo/IdeaProjects/Tesi-GeCo/"

  con_handler.run().foreach(getResults(_))

  def getResults (str: String) {
    var json = Json.parse(annotator(str))
    new File(path + "bioportal_results/annotator").mkdirs()
    new File(path + "bioportal_results/recommender").mkdirs()
    write_to_file(Json.prettyPrint(json), path + "bioportal_results/" + "/annotator/", str + ".json")
    if (str.contains(";")){
      json = Json.parse(recommender(str.split(";").map(_.trim).toList))
    }
    else
      json = Json.parse(recommender(str))
    write_to_file(Json.prettyPrint(json), path + "bioportal_results/" + "/recommender/", str + ".json")
  }
}