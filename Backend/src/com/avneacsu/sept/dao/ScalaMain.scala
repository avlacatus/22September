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

    val xml = XML.loadFile("david.xml")

    DataHolder.readFromFile("david.xml")

    val memberList = xml \ "member-list" \ "member"

    for (churchNode <- memberList) {
      println(churchNode)
      val churchObject = DefaultMemberFormatter.read(churchNode)
//      println(churchObject)
    }

    println("hello from scala")

    DataHolder.registerChurch("fetestii noi", "strada principala")
    var m1 = new Member(1, "david", 1)
//    println(c1, m1)
//    println(DefaultChurchFormatter.write(c1))

    DataHolder.saveToFile("test.xml")
  }

  def function: Unit = {
    println("hello from function")
  }

}
