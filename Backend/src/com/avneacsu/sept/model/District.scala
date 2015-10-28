package com.avneacsu.sept.model

/**
 * Created by Alexandra Neacsu on 20/10/2015.
 */
case class District(districtName: String, pastorName: String, churches: List[Church], members: List[Member]) extends  XMLResource{
  override def getAttributeList: List[String] = List("district-name")

  override def getRootElementName: String = "district"

  override def getValueForAttribute(attributeName: String): String = attributeName match {
    case "district-name" => districtName
  }
}
