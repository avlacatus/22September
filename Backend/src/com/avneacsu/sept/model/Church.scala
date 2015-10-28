package com.avneacsu.sept.model

case class Church(churchId: Int, name: String, address: String) extends AbstractResource with XMLResource {
  override def getAttributeList = List("church-id", "name", "address")

  override def getValueForAttribute(attributeName: String) = attributeName match {
    case "church-id" => churchId.toString
    case "name" => name
    case "address" => address
  }

  override def getRootElementName = "church"
}
