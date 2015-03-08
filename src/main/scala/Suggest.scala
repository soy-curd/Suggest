
import java.net.URLEncoder.encode
import scala.io.Source

object Suggest {
  def main(args: Array[String]): Unit = {
    val appid = args(0)
    val sentence = args(1)

    println(proof(appid, sentence))
  }

  def proof(appid:String, sentence:String) = {
    val yahooKouseiURL = "http://jlp.yahooapis.jp/KouseiService/V1/kousei?"
    val params = "appid=%s&sentence=%s".format(
      encutf(appid), encutf(sentence))

    val response = Source.fromURL(yahooKouseiURL + params)
    response.getLines.toList
  }

  def encutf(value: String) = {
    encode(value, "UTF-8")
  }
}
