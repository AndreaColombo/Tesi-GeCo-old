package Utils

import java.io.{FileNotFoundException, FileWriter, IOException}

object Utils {
  def filterJSON(string: String) : String = {
    val s = "HttpResponse("
    return string.substring(string.indexOf(s)+s.length, string.indexOf(",200"))
  }

  def write_to_file (str: String, path: String) : Boolean = {

    var writer: FileWriter = null
    try {
      writer = new FileWriter(path)
    }
    catch {
      case e: FileNotFoundException => {
        e.printStackTrace()
        return false
      }
      case e1: IOException => {
        e1.printStackTrace()
        return false
      }
    }

    writer.write(str)
    writer.flush()
    writer.close()
    return true
  }
}
