package DBcon

object main extends App{
  val results = con_handler.TestQuery()
  results.foreach(foo(_))

  def foo(str: String) = {
    //facio qualcosa

  }

}
