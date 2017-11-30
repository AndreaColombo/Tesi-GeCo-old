package DBcon

import scala.slick.driver.PostgresDriver.simple


object con_handler {

  class t1(tag: Tag) extends Table[(Int, String)](tag, "users") {
    def s_id = column[String]("s_id")
    def key = column[String]("key")
    def value = column[String]("value")
    def * = (s_id, key, value)
  }

}
