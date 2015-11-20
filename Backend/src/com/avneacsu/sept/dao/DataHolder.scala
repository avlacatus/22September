package com.avneacsu.sept.dao

import java.io.{File, PrintWriter}

import com.avneacsu.sept.DataSetChangedEvent
import com.avneacsu.sept.model.{Member, Church, District}
import com.avneacsu.sept.parser.DefaultDistrictFormatter

import scala.swing.Publisher
import scala.{Tuple2, Tuple1}
import scala.xml.XML

/**
 * Created by Beta on 10/29/2015.
 */
object DataHolder extends Publisher{

  private var district: District = _

  private var maxChurchId = 0

  private var maxMemberId = 100

  def readFromFile(fileName: String) = {
    district = DefaultDistrictFormatter.read(XML.loadFile(fileName))
    maxChurchId = district.churches.values.maxBy[Int](_.churchId).churchId
    maxMemberId = district.members.values.maxBy(_.memberId).memberId
    println(district)
  }

  def saveToFile(fileName: String) = {
    val pw = new PrintWriter(new File(fileName))
    pw.write(DefaultDistrictFormatter.write(district).toString())
    pw.close()
  }

  def clearData = {
    district = new District("", null, Map.empty, Map.empty)
    maxChurchId = 0
    maxMemberId = 100
    println(district)
  }

  def getDistrictName = district.districtName

  def getPastorInfo = district.pastor

  def getChurchesInfo = district.churches.values.toList

  def getChurchInfo(churchId: Int) = district.churches(churchId)

  def getMembersInfo = district.members

  def getMemberInfo(memberId: Int) = district.members(memberId)

  def getMembersForChurch(churchId: Int): List[Member] =
    district.members.values.toList.filter(member => member.churchId == churchId)

  def getMembersForChurch(church: Church): List[Member] = getMembersForChurch(church.churchId)

  def getMemberChurchInfo(member: Member): Church = district.churches(member.churchId)

  def getMemberChurchInfo(memberId: Int): Church = getMemberChurchInfo(district.members(memberId))

  def registerChurch(name: String, address: String) = {
    maxChurchId += 1
    district = district.copy(churches = district.churches.+((maxChurchId, Church(maxChurchId, name, address))))
  }

  def removeChurch(church: Church) =
    district = district.copy(churches = district.churches.-(church.churchId))


  def updateChurch(churchId: Int, name: String, address: String): Unit = {
    district = district.copy(churches = district.churches + ((churchId, Church(churchId, name, address))))
  }

  def registerMember(churchId: Int, name: String, address: String, email: String, phoneNo: String, birthday: String, baptismDate: String, description: String, notes: String): Option[Member] = {
    if (district != null) {
      maxMemberId += 1
      district = district.copy(members = district.members.+((maxMemberId, Member(maxMemberId, name, churchId, address, phoneNo, email, birthday, baptismDate, description, notes, ""))))
      println("register member called")
      publish(new DataSetChangedEvent)
      Some(district.members(maxMemberId))
    } else {
      None
    }
  }

  def updateMember(memberId: Int, churchId: Int, name: String, address: String, email: String, phoneNo: String, birthday: String, baptismDate: String, description: String, notes: String): Option[Member] = {
    if (district != null && district.members.contains(memberId)) {
      district = district.copy(members = district.members.+((memberId, Member(memberId, name, churchId, address, phoneNo, email, birthday, baptismDate, description, notes, ""))))
      println("update member called")
      publish(new DataSetChangedEvent)
      Some(district.members(memberId))
    } else {
      None
    }
  }
}
