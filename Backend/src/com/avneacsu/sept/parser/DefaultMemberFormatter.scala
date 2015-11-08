package com.avneacsu.sept.parser

import com.avneacsu.sept.model.{Member, Church}

import scala.xml._

object DefaultMemberFormatter extends ResourceFormatter[Member] {

  override def read(node: Node): Member = {
    Member((node \@ "member-id").toInt,
      (node \ "name").text,
      (node \@ "church-id").toInt,
      (node \ "address").text,
      (node \ "phone-no").text,
      (node \ "email").text,
      (node \ "date-of-birth").text,
      (node \ "date-of-baptism").text,
      (node \ "description").text,
      (node \ "notes").text,
      (node \ "date-of-last-visit").text
    )
  }

  override def write(resource: Member): Node = {
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
