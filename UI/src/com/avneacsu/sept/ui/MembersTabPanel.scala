package com.avneacsu.sept.ui

import com.avneacsu.sept.dao.DataHolder
import com.avneacsu.sept.model.Church

import scala.swing._
import scala.swing.event.ButtonClicked

/**
 * Created by Alexandra Neacsu on 11/5/2015.
 */
class MembersTabPanel(church: Church) extends BorderPanel {
  val members = if (church != null) DataHolder.getMembersForChurch(church) else Nil
  val membersListView = new ListView(members.map(_.name))
  layout(membersListView) = BorderPanel.Position.Center

  val buttonsPanel = new BoxPanel(Orientation.Vertical) {
    val buttonAdd = new Button("Adauga")
    val buttonEdit = new Button("Editeaza")
    contents += buttonAdd
    contents += buttonEdit
  }

  layout(buttonsPanel) = BorderPanel.Position.East
  buttonsPanel.contents.foreach(listenTo(_))

  reactions += {
    case ButtonClicked(button) => {
      println("button pressed")
      button.name match {
        case "buttonAdd" => println("add pressed")
        case _ => println(button.name)
      }
    }
  }
}
