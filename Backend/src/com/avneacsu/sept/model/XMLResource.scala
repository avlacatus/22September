package com.avneacsu.sept.model

trait XMLResource {

  def getRootElementName: String

  def getAttributeList: List[String] = Nil

  def getValueForAttribute(attributeName: String): String = ""

  def getChildrenNameList: List[String] = Nil

  def getValueForChild(childName: String): String = ""

}
