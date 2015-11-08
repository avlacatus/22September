package com.avneacsu.sept.model

/**
 * Created by Beta on 10/29/2015.
 */
case class Pastor(name: String, address: String, phoneNo: String, email: String) extends AbstractResource with XMLResource {
  override def getRootElementName: String = "pastor"

  override def getChildrenNameList = List("name", "address", "phone-no", "email")

  override def getValueForChild(childName: String): String = childName match {
    case "name" => name
    case "address" => address
    case "phone-no" => phoneNo
    case "email" => email
  }
}
