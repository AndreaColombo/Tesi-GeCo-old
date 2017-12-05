package DBcon

import com.typesafe.config.ConfigFactory

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.PostgresProfile.api._
import java.io._

import scala.concurrent.duration.Duration

object con_handler {

  class t1(tag: Tag) extends Table[(String, String, String)](tag, Some("svr"),"t1") {
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

  class prova(tag: Tag) extends Table[(String, String, String)](tag, Some("svr"), "prova") {
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

    val db = Database.forConfig("mydb", conf)
    try {
      val setup = DBIO.seq(
        prova.schema.create,
        prova += ("AAA", "AAAA", "AAAAA"),
        prova += ("CCC", "AAAA", "AAAAA"),
        prova += ("BBB", "AAAA", "AAAAA")
      )

      val setupFuture = db.run(setup)
      val q = for {
        t <- t1 if t.value === "ENCODE"
      } yield (t.key, t.value)
      val resultFuture = setupFuture.flatMap { _ =>
        println("Results: ")
        db.run(q.result).map(_.foreach {a =>
          println("  " + a._1 + "\t" + a._2)
        })
      }
      Await.result(resultFuture, Duration.Inf)
    }
    finally db.close()


    return true
  }

}
