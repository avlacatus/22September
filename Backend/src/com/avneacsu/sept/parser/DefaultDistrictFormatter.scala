package com.avneacsu.sept.parser

import com.avneacsu.sept.model.{District, Church}

import scala.xml._

object DefaultDistrictFormatter extends ResourceFormatter[District] {

  override def read(node: Node): District = {
    val churchesList = node \ "church-list" \ "church"
    val membersList = node \ "member-list" \ "member"

    District(node \@ "district-name", node \ "pastor" \@ "pastor-name",
      churchesList.map(churchNode => DefaultChurchFormatter.read(churchNode)).toList,
      membersList.map(memberNode => DefaultMemberFormatter.read(memberNode)).toList)
  }

  override def write(resource: District): Node = {
    val churchListObject = Elem(null, "church-list", Null, TopScope, true, resource.churches.map(DefaultChurchFormatter.write):_*)
    val memberListObject = Elem(null, "member-list", Null, TopScope, true, resource.members.map(DefaultMemberFormatter.write):_*)
    var output = Elem(null, resource.getRootElementName, scala.xml.Null, scala.xml.TopScope, true, churchListObject, memberListObject)
    for (attributeName <- resource.getAttributeList)
    output = output % Attribute(attributeName, Text(resource.getValueForAttribute(attributeName)), Null)
    output
  }
}
