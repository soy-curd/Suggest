
import java.net.URLEncoder.encode
import scala.io.Source
import scala.xml.{Elem, XML, PrettyPrinter}

object Suggest {
  def main(args: Array[String]): Unit = {
    val appid = args(0)
    val sentence = args(1)
    proof(appid, sentence)
  }

  // 校正を行う
  def proof(appid:String, sentence:String) = {
    val yahooKouseiURL = "http://jlp.yahooapis.jp/KouseiService/V1/kousei?"
    val params = "appid=%s&sentence=%s".format(encutf(appid), encutf(sentence))
    val response = Source.fromURL(yahooKouseiURL + params)
    val xml = XML.loadString(response.getLines.mkString)
    val anotationList = analyzeXml(xml, sentence)
    println(sentence)
    anotationList.foreach(println)
  }

  // xml解析
  def analyzeXml(xml:Elem, sentence:String) = {
    val resultList = (xml \ "Result").toList
    val wrongConvert = resultList.filter(checkWrongConvert)
    wrongConvert.map(makeAnotation)
  }

  // 誤変換があるかチェック
  def checkWrongConvert(child: scala.xml.Node) = {
    (child \ "ShitekiInfo").text match {
      case "誤変換" => true
      case _ => false
    }
  }

  // 指摘文字列を生成
  def makeAnotation(child: scala.xml.Node): String = {
    val start = (child \ "StartPos").text
    val len = (child \ "Length").text
    val surface = (child \ "Surface").text
    val shiteki = (child \ "ShitekiWord").text
    "　" * start.toInt + "^" + shiteki
  }

  // xmlを整形して出力
  def ppXml(xml:Elem) = println(new PrettyPrinter(40, 2).format(xml))

  // utf-8にエンコードする
  def encutf(value: String) = encode(value, "UTF-8")

}
