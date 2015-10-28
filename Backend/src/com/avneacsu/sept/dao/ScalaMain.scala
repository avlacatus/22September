package com.avneacsu.sept.dao

import java.io.{File, PrintWriter}

import com.avneacsu.sept.model.{District, Church, Member}
import com.avneacsu.sept.parser.{DefaultDistrictFormatter, DefaultMemberFormatter, DefaultChurchFormatter}

import scala.xml.XML

/**
 * Created by Cosmin on 19/10/2015.
 */
object ScalaMain {

  def main(args: Array[String]): Unit = {

    val xml = XML.loadFile("david-fetesti.xml")
//    println(xml)

    val district = DefaultDistrictFormatter.read(xml)
    println(district)

    val churchList = xml \ "member-list" \ "member"

    for (churchNode <- churchList) {
      println(churchNode)
      val churchObject = DefaultMemberFormatter.read(churchNode)
      println(churchObject)
    }

    println("hello from scala")

    var c1 = new Church(1, "Fetesti", "strada Dropilor")
    var m1 = new Member(1, "david", 1)
    println(c1, m1)
    println(DefaultChurchFormatter.write(c1))

    saveToFile(district, "test.xml")
  }

  def function: Unit = {
    println("hello from function")
  }

  def readFromFile(fileName: String) = DefaultDistrictFormatter.read(XML.loadFile(fileName))

  def saveToFile(district: District, fileName: String) =  {
    val pw = new PrintWriter(new File(fileName))
    pw.write(DefaultDistrictFormatter.write(district).toString())
    pw.close()
  }

}
