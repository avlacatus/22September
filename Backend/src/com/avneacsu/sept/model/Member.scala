package com.avneacsu.sept.model


object Member {
  def apply(id: Int, name: String, churchId: Int) = new Member(id, name, churchId, "", "", "", "", "", "")
}

case class Member(id: Int, 
                  name: String, 
                  churchId: Int, 
                  address: String, 
                  dateOfBirth: String,
                  dateOfBaptism: String,
                  description: String,
                  notes: String,
                  lastVisited: String) extends AbstractResource with XMLResource {

  def this(id: Int, name: String, churchId: Int) {
    this(id, name, churchId, "", "", "", "", "", "")
  }

  override def getRootElementName: String = "member"

  override def getAttributeList: List[String] = List("member-id", "church-id")

  override def getValueForAttribute(attributeName: String): String = attributeName match {
    case "church-id" => churchId.toString
    case "member-id" => id.toString
  }

  def getChildrenNameList: List[String] = List("name", "address", "date-of-birth", "date-of-baptism", "description", "notes", "date-of-last-visit")

  def getValueForChild(childName: String): String = childName match {
    case "name" => name
    case "address" => address
    case "date-of-birth" => dateOfBirth
    case "date-of-baptism" => dateOfBaptism
    case "notes" => notes
    case "description" => description
    case "date-of-last-visit" => lastVisited
  }
}
