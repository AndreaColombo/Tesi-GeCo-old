package UMLS

import java.io._

import UMLS.main.path
import play.api.libs.json._
import Utils.Utils.write_to_file

import scala.collection.mutable.ListBuffer


object main extends  App {
  var line_count = 0

  def count_lines (str: String) = {
    var reader: FileReader = null
    var br: BufferedReader = null
    try {
      reader = new FileReader(path + "search/" + str)
      br = new BufferedReader(reader)
    }
    catch {
      case e: FileNotFoundException => e.printStackTrace()
      case e1: IOException => e1.printStackTrace()
    }
    line_count += br.lines().count().asInstanceOf[Int]
    br.close()
    reader.close()
  }

  def foo(str: String) = {
    new File(path + "search_results/"+str+"/").mkdirs()
    var reader: FileReader = null
    var br: BufferedReader = null
    try {
      reader = new FileReader(path + "search/" + str)
      br = new BufferedReader(reader)
    }
    catch {
      case e: FileNotFoundException => e.printStackTrace()
      case e1: IOException => e1.printStackTrace()
    }

    var line = br.readLine()

    while (line != null) {
      line = line.replace('\\', ' ')
      st = Auth.getST(tgt, apikey)
      new File(path + "search_results/" + str + "/" + line).mkdir()
      val r = Requests.getCUI(st, line)

      val json = Json.parse(r)
      val ui_lst = json \\ "ui"

      if (!ui_lst.head.toString.equals(""""NONE"""")) {
        var cnt = 0
        for (cnt <- ui_lst.indices) {
          val ui = ui_lst(cnt).toString().filterNot(_.equals('"'))
          //println(ui_lst(cnt))
          //println(ui)
          val cui  =  Json.parse(Requests.getCUIAtoms(Auth.getST(tgt, apikey), ui))

          //get atoms
          val atoms = Json.parse(Requests.getCUIAtoms(Auth.getST(tgt, apikey), ui + "/atoms"))

          //get definitions
          val definitions = cui \\ "definitions"
          if (!definitions.head.toString().equalsIgnoreCase(""""NONE"""")) {
            val Jdefinitions = Json.parse(Requests.getCUIAtoms(Auth.getST(tgt, apikey), ui + "/definitions"))
            write_to_file(Json.prettyPrint(Jdefinitions), path + "search_results/" + str + "/" + line + "/" + ui + "/" + ui + "-definitions" + ".json")
          }

          //get relations
          val relations = cui \\ "relations"
          if (!relations.head.toString().equalsIgnoreCase(""""NONE"""")) {
            val Jrelations= Json.parse(Requests.getCUIAtoms(Auth.getST(tgt, apikey), ui + "/relations"))
            write_to_file(Json.prettyPrint(Jrelations), path + "search_results/" + str + "/" + line + "/" + ui + "/" + ui + "-relations" + ".json")
          }

          //get semantics
          val semantic = cui \ "semanticTypes" \\ "uri"
          if (semantic.nonEmpty) {
            val JSemantic= Json.parse(Requests.getCUIAtoms(Auth.getST(tgt, apikey), ui + "/relations"))
            write_to_file(Json.prettyPrint(JSemantic), path + "search_results/" + str + "/" + line + "/" + ui + "/" + ui + "-semantic" + ".json")
          }

/*
          val atom_lst = atoms \\ "ui"
          val attributes = atoms \\ "attributes"
          val parents = atoms \\ "parents"
          val ancestors = atoms \\ "ancestors"
          val children = atoms \\ "children"
          val descendants = atoms \\ "descendants"
          val a_relations = atoms \\ "relations"

          var cnt2 = 0
          for (cnt2 <- atom_lst.indices){
            val aui = atom_lst(cnt)
            //get attributes parents ancestors children descendants and relations of each atom

            new File (path + "search_results/" + str + "/" + line + "/" + ui + "/" + aui).mkdir()
          }
*/


          //Write results in Json files
          new File(path + "search_results/" + str + "/" + line + "/" + ui).mkdir()
          write_to_file(Json.prettyPrint(cui), path + "search_results/" + str + "/" + line + "/" + ui + "/" + ui + ".json")
          write_to_file(Json.prettyPrint(atoms), path + "search_results/" + str + "/" + line + "/" + ui + "/" + ui + "-atoms" + ".json")
        }
      }
      write_to_file(Json.prettyPrint(json), path + "search_results/" + str + "/" + line + "/" + line + ".json")
      line = br.readLine()
      i += 1
      val per: Float = i * 100 / line_count
      print("\r"+per+"%")
    }
    br.close()
    reader.close()
  }

  val apikey = "2d2b04ad-959f-4408-895f-0cbec2cd2a4c"
  val path = "C:/Users/Andrea Colombo/IdeaProjects/UMLS/"
  val filenames = List("prova")
  val tgt = Auth.getTGT(apikey)
  var st = ""
  var i = 0

  filenames.foreach(count_lines(_))
  filenames.foreach(foo(_))
}

