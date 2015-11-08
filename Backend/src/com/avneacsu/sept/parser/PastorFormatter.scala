package com.avneacsu.sept.parser

import com.avneacsu.sept.model.{Pastor, Church}

import scala.xml._

object PastorFormatter extends ResourceFormatter[Pastor] {

  override def read(node: Node): Pastor = {
    Pastor((node \ "name").text, (node \ "address").text, (node \ "phone-no").text, (node \ "email").text)
  }

  override def write(resource: Pastor): Node = {
    var childrenList: List[Node] = Nil
    for (childName <- resource.getChildrenNameList) {
      childrenList = childrenList :+ Elem(null, childName, Null, TopScope, true, Text(resource.getValueForChild(childName)))
    }

    var output = Elem(null, resource.getRootElementName, scala.xml.Null, scala.xml.TopScope, true, childrenList:_*)
    output
  }
}
