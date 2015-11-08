package com.avneacsu.sept.model

case class Church(churchId: Int, name: String, address: String) extends AbstractResource with XMLResource {

  override def getRootElementName = "church"

  override def getAttributeList = List("church-id")

  override def getChildrenNameList = List("name", "address")

  override def getValueForAttribute(attributeName: String) = attributeName match {
    case "church-id" => churchId.toString
    case _ => ???
  }

  override def getValueForChild(childName: String): String = childName match {
    case "name" => name
    case "address" => address
    case _ => ???
  }
}
