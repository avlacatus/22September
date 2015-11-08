package com.avneacsu.sept.model

/**
 * Created by Alexandra Neacsu on 20/10/2015.
 */
case class District(districtName: String,
                    pastor: Pastor,
                    churches: Map[Int, Church],
                    members: Map[Int, Member]) extends XMLResource {

  def copy(districtName: String = this.districtName,
           pastor: Pastor = this.pastor,
           churches: Map[Int, Church] = this.churches,
           members: Map[Int, Member] = this.members): District =
    District(districtName, pastor, churches, members)


  override def getAttributeList: List[String] = List("district-name")

  override def getRootElementName: String = "district"

  override def getValueForAttribute(attributeName: String): String = attributeName match {
    case "district-name" => districtName
  }
}
