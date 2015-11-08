package com.avneacsu.sept.parser

import com.avneacsu.sept.model.{District, Church}

import scala.xml._

object DefaultDistrictFormatter extends ResourceFormatter[District] {

  override def read(node: Node): District = {
    val churchesList = node \ "church-list" \ "church"
    val membersList = node \ "member-list" \ "member"

    District(node \@ "district-name", PastorFormatter.read((node \ "pastor").head),
      churchesList.map(churchNode => (churchNode \@ "church-id").toInt).zip(churchesList.map(churchNode => DefaultChurchFormatter.read(churchNode))).toMap,
      membersList.map(memberNode => (memberNode \@ "member-id").toInt).zip(membersList.map(memberNode => DefaultMemberFormatter.read(memberNode))).toMap)
  }

  override def write(resource: District): Node = {
    val churchListObject = Elem(null, "church-list", Null, TopScope, true, resource.churches.values.map(DefaultChurchFormatter.write).toList:_*)
    val memberListObject = Elem(null, "member-list", Null, TopScope, true, resource.members.values.map(DefaultMemberFormatter.write).toList:_*)
    var output = Elem(null, resource.getRootElementName, scala.xml.Null, scala.xml.TopScope, true, PastorFormatter.write(resource.pastor), churchListObject, memberListObject)
    for (attributeName <- resource.getAttributeList)
    output = output % Attribute(attributeName, Text(resource.getValueForAttribute(attributeName)), Null)
    output
  }
}
