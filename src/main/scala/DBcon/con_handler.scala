package DBcon

import com.typesafe.config.ConfigFactory

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.PostgresProfile.api._
import java.io._

import slick.jdbc.{ActionBasedSQLInterpolation, SQLActionBuilder}
import slick.sql.SqlStreamingAction

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration

object con_handler {

  var query = ""

  val q = sql"""select *
        from svr.T1
        where s_id like 'ENCFF%'
        and (key ilike '%scientific_name'
        or key ilike '%scientific_name'
        or key ilike '%sex'
        or key ilike '%ethnicity'
        or key ilike '%biosample_type'
        or key ilike '%biosample_term_name'
        or key ilike '%biosample_term_id'
        or key ilike '%health_status'
        or key ilike '%output_type'
        or key ilike '%file_type'
        or key ilike '%Assembly'
        or key ilike '%assay_term_name'
        or key ilike '%assay_term_id'
        or key ilike '%target__investigated_as');""".as[(String, String, String)]


  def runQuery (q: String) = {

  }

 def run (): List[String] = {
    val parsedConfig = ConfigFactory.parseFile(new File("src/main/scala/DBcon/application.conf"))
    val conf = ConfigFactory.load(parsedConfig)
    var resultsBuffer = ListBuffer[String]()

    val db = Database.forConfig("mydb", conf)
    try {
      val setup = DBIO.seq(
        //prova.schema.create,
        //prova += ("AAA", "AAAA", "AAAAA"),
        //prova += ("CCC", "AAAA", "AAAAA"),
        //prova += ("BBB", "AAAA", "AAAAA")
      )

      val setupFuture = db.run(setup)

      val resultFuture = setupFuture.flatMap { _ =>
        db.run(q).map(_.foreach {a =>
          resultsBuffer.append(a._3)
        })
      }
      Await.result(resultFuture, Duration.Inf)
      println("DB finito")
    }
    finally db.close()


    return resultsBuffer.toList.distinct
  }

}
