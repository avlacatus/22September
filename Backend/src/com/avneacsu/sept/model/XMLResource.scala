package com.avneacsu.sept.model

trait XMLResource {

  def getAttributeList: List[String]

  def getValueForAttribute(attributeName: String): String

  def getRootElementName: String

}
