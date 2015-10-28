package com.avneacsu.sept.parser

import com.avneacsu.sept.model.Church

import scala.xml._

object DefaultChurchFormatter extends ResourceFormatter[Church] {

  override def read(node: Node): Church = {
    Church((node \@ "church-id").toInt, node \@ "name", node \@ "address")
  }
}
