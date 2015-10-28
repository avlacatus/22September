package com.avneacsu.sept.parser

import com.avneacsu.sept.model.{Member, Church}

import scala.xml._

object DefaultMemberFormatter extends ResourceFormatter[Member] {

  override def read(node: Node): Member = {
    Member((node \@ "member-id").toInt, node \@ "name", (node \@ "church-id").toInt)
  }

  override def write(member: Member): Node = {
    var childrenList: List[Node] = Nil
    for (childName <- member.getChildrenNameList) {
      childrenList = childrenList :+ Elem(null, childName, Null, TopScope, true, Text(member.getValueForChild(childName)))
    }

    var output = Elem(null, member.getRootElementName, scala.xml.Null, scala.xml.TopScope, true, childrenList:_*)
    for (attributeName <- member.getAttributeList)
      output = output % Attribute(attributeName, Text(member.getValueForAttribute(attributeName)), Null)

    output
  }
}
