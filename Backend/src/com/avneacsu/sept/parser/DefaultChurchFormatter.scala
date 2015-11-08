package com.avneacsu.sept.parser

import com.avneacsu.sept.model.Church

import scala.xml._

object DefaultChurchFormatter extends ResourceFormatter[Church] {

  override def read(node: Node): Church = {
    Church((node \@ "church-id").toInt, (node \ "name").text, (node \ "address").text)
  }

  override def write(resource: Church): Node = {
    var childrenList: List[Node] = Nil
    for (childName <- resource.getChildrenNameList) {
      childrenList = childrenList :+ Elem(null, childName, Null, TopScope, true, Text(resource.getValueForChild(childName)))
    }

    var output = Elem(null, resource.getRootElementName, scala.xml.Null, scala.xml.TopScope, true, childrenList:_*)
    for (attributeName <- resource.getAttributeList)
      output = output % Attribute(attributeName, Text(resource.getValueForAttribute(attributeName)), Null)

    output
  }
}
