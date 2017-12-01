package DBcon

import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.PostgresProfile.api._

import java.io._

object con_handler {

  class t1(tag: Tag) extends Table[(String, String, String)](tag, "t1") {
    def s_id = column[String]("s_id")
    def key = column[String]("key")
    def value = column[String]("value")
    def * = (s_id, key, value)
  }
  val t1 = TableQuery[t1]

  class t2(tag: Tag) extends Table[(String, String, String)](tag, "t2") {
    def s_id = column[String]("s_id")
    def key = column[String]("key")
    def value = column[String]("value")
    def * = (s_id, key, value)
  }

  class t3(tag: Tag) extends Table[(String, String, String)](tag, "t3") {
    def s_id = column[String]("s_id")
    def key = column[String]("key")
    def value = column[String]("value")
    def * = (s_id, key, value)
  }

  def TestQuery (): Boolean = {

    //val db = Database.forURL("jdbc:postgresql://geco:geco78@localhost/geco-test", "org.postgresql.Driver")

    val parsedConfig = ConfigFactory.parseFile(new File("src/main/scala/DBcon/application.conf"))
    val conf = ConfigFactory.load(parsedConfig)

    val db = Database.forConfig("postgre", conf)
    print(db.toString)

    db.run(t1.result).map(_.foreach {
      case (s_id, key, value) => println("  " + s_id + "\t" + key + "\t" + value)
    })

    return true
  }

}
