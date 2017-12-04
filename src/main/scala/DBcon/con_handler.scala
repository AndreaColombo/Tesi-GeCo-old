package DBcon

import com.typesafe.config.ConfigFactory

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.PostgresProfile.api._
import java.io._

import scala.concurrent.duration.Duration

object con_handler {

  class t1(tag: Tag) extends Table[(String, String, String)](tag, "t1") {
    def s_id = column[String]("s_id", O.PrimaryKey)
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

  class prova(tag: Tag) extends Table[(String, String, String)](tag, "svr.prova") {
    def s_id = column[String]("s_id")
    def key = column[String]("key")
    def value = column[String]("value")
    def * = (s_id, key, value)
  }
  val prova = TableQuery[prova]

  def TestQuery (): Boolean = {

    //val db = Database.forURL("jdbc:postgresql://131.175.120.18/geco-test", "geco", "geco78", null, "org.postresql.Driver")
    val parsedConfig = ConfigFactory.parseFile(new File("src/main/scala/DBcon/application.conf"))
    val conf = ConfigFactory.load(parsedConfig)

    val db = Database.forConfig("postgre", conf)
    try {
      val setup = DBIO.seq(
        prova.schema.create,
        prova += ("AAA", "AAAA", "AAAAA"),
        prova += ("CCC", "AAAA", "AAAAA"),
        prova += ("BBB", "AAAA", "AAAAA")
      )

      val setupFuture = db.run(setup)
      val resultFuture = setupFuture.flatMap { _ =>
        println("ALLORA::: ")
        db.run(t1.result).map(_.foreach {
          case (s_id, key, value) => println("kodio  " + s_id + "\t" + key + "\t" + value)
        })
      }

      Await.result(resultFuture, Duration.Inf)
    }
    finally db.close()


    return true
  }

}
