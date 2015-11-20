package com.avneacsu.sept.ui

import java.text.SimpleDateFormat
import java.util.{Calendar, Properties}
import javax.swing.JFormattedTextField.AbstractFormatter
import javax.swing._

import com.avneacsu.sept.dao.DataHolder
import com.avneacsu.sept.DataSetChangedEvent
import com.avneacsu.sept.model.{Church, Member}
import org.jdatepicker.impl.{JDatePanelImpl, JDatePickerImpl, UtilDateModel}

import scala.swing.ListView.IntervalMode
import scala.swing._
import scala.swing.event.{Event, ListSelectionChanged, ButtonClicked}

/**
 * Created by Alexandra Neacsu on 11/5/2015.
 */
class MembersTabPanel(church: Church) extends BorderPanel {
  var members = if (church != null) DataHolder.getMembersForChurch(church) else Nil
  val membersListView = new ListView(members.map(_.name))
  membersListView.selection.intervalMode = IntervalMode.Single

  layout(membersListView) = BorderPanel.Position.Center

  val buttonsPanel = new BoxPanel(Orientation.Vertical) {
    val buttonAdd = new Button("Adauga")
    val buttonEdit = new Button("Editeaza")
    contents += buttonAdd
    contents += buttonEdit

    buttonAdd.name = "addButton"
    buttonEdit.name = "editButton"
  }

  layout(buttonsPanel) = BorderPanel.Position.East
  buttonsPanel.contents.foreach(listenTo(_))
  listenTo(membersListView.selection)
  listenTo(DataHolder)

  reactions += {
    case e: DataSetChangedEvent => {
      println("dataset changed event " + e)
    }
    case ButtonClicked(button) => {
      button.name match {
        case "addButton" => onAddButtonPressed
        case "editButton" => onEditButtonPressed(members(membersListView.selection.indices.head))
        case _ => println(button.name)
      }
    }
    case ListSelectionChanged(something) => {
      println("list selection changed " + something)
    }

  }

  def onAddButtonPressed = {
    assert(church != null)

    val values = AddEditMemberDialog.showAddMemberDialog

    if (values.isDefined && addMemberMethod(values.get.toArray).isDefined) {
      JOptionPane.showMessageDialog(null, "Membru adaugat cu succes!")
      refreshList
    }
  }

  def onEditButtonPressed(member: Member) = {
    assert(church != null)

    val result = AddEditMemberDialog.showEditMemberDialog(member)
    if (result.isDefined && editMemberMethod(member.memberId, result.get.toArray).isDefined) {
      JOptionPane.showMessageDialog(null, s"Membru ${member.memberId} editat cu succes!")
      refreshList
    }
  }

  def addMemberMethod(array: Array[String]): Option[Member] = {
    assert(array.length == 8)
    DataHolder.registerMember(church.churchId, array(0), array(1), array(2), array(3), array(4), array(5), array(6), array(7))
  }

  def editMemberMethod(memberId: Int, array: Array[String]): Option[Member] = {
    assert(array.length == 8)
    DataHolder.updateMember(memberId, church.churchId, array(0), array(1), array(2), array(3), array(4), array(5), array(6), array(7))
  }

  def refreshList = {
    members = if (church != null) DataHolder.getMembersForChurch(church) else Nil
    membersListView.listData = members.map(_.name)
  }



}


class AddEditMemberDialog {

  val formatter = new AbstractFormatter {
    override def valueToString(value: scala.Any): String = {
      value match {
        case c: Calendar => new SimpleDateFormat(Member.DATE_FORMAT).format(c.getTime)
        case _ => "--"
      }
    }

    override def stringToValue(text: String): AnyRef = text
  }

  val nameTF = new JTextField()
  val addressTF = new JTextField()
  val emailTF = new JTextField()
  val phoneTF = new JTextField()

  val birthdayDatePicker: JDatePickerImpl = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties), formatter)
  val baptismDatePicker: JDatePickerImpl = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties), formatter)
  val descTA = new JTextArea()
  val notesTA = new JTextArea()

  notesTA.setRows(2)
  descTA.setRows(2)
  val dialogComponents: Array[JComponent] = Array(
    new JLabel("Nume"),
    nameTF,
    new JLabel("Adresa"),
    addressTF,
    new JLabel("Adresa email"),
    emailTF,
    new JLabel("Numar de telefon"),
    phoneTF,
    new JLabel("Data nasterii"),
    birthdayDatePicker,
    new JLabel("Data botezului"),
    baptismDatePicker,
    new JLabel("Descriere"),
    new JScrollPane(descTA),
    new JLabel("Note"),
    new JScrollPane(notesTA)
  )
  val optionPane = new JOptionPane(dialogComponents, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, Array[Object] (AddEditMemberDialog.NEGATIVE_ANSWER, AddEditMemberDialog.POSITIVE_ANSWER))

  def createAddMemberDialog: JDialog = {
    optionPane.createDialog("Adauga Membru")
  }

  def createEditMemberDialog(member: Member): JDialog = {
    nameTF.setText(member.name)
    addressTF.setText(member.address)
    optionPane.createDialog("Editeaza membru")
  }

  def getFieldsValues: List[String] = List(
    nameTF.getText,
    addressTF.getText,
    emailTF.getText,
    phoneTF.getText,
    if (birthdayDatePicker.getModel.getValue != null) birthdayDatePicker.getModel.getValue.toString else "",
    if (baptismDatePicker.getModel.getValue != null) baptismDatePicker.getModel.getValue.toString else "",
    descTA.getText,
    notesTA.getText)
}

object AddEditMemberDialog {

  val POSITIVE_ANSWER = "Salveaza"
  val NEGATIVE_ANSWER = "Anuleaza"

  def showAddMemberDialog: Option[List[String]] = {
    val pDialog = new AddEditMemberDialog
    pDialog.createAddMemberDialog.setVisible(true)
    if (pDialog.optionPane.getValue == POSITIVE_ANSWER) {
      println(pDialog.getFieldsValues)
      Some(pDialog.getFieldsValues)
    } else {
      None
    }
  }

  def showEditMemberDialog(member: Member): Option[List[String]] = {
    val pDialog = new AddEditMemberDialog
    pDialog.createEditMemberDialog(member).setVisible(true)
    if (pDialog.optionPane.getValue == POSITIVE_ANSWER) {
      println(pDialog.getFieldsValues + " " + pDialog.optionPane.getValue)
      Some(pDialog.getFieldsValues)
    } else {
      None
    }
  }

}
