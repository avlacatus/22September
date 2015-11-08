package com.avneacsu.sept.ui

/**
 * Created by Beta on 11/1/2015.
 */

import javax.swing.border.BevelBorder
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{KeyStroke, JLabel, UIManager}

import com.avneacsu.sept.dao.DataHolder
import com.avneacsu.sept.model.Church

import scala.swing.FileChooser.SelectionMode
import scala.swing.TabbedPane.Page
import scala.swing._
import scala.swing.event.{SelectionChanged, ButtonClicked}

object UIApp extends SimpleSwingApplication {

  private var loadedFileName: String = _
  private var topContainer: FlowPanel = _
  private var pastorNameField: Label = _
  private var churchesButtonGroup: ButtonGroup = _
  private var tabbedPane: TabbedPane = _
  private var selectedChurch: Church = _

  private def onFileLoaded = {
    pastorNameField.text = s"Bine ai venit, ${DataHolder.getPastorInfo.name}!"
    var radios = DataHolder.getChurchesInfo.map(church => new RadioButton(church.name))
    churchesButtonGroup.buttons ++= radios
    topContainer.contents ++= radios
    radios.foreach(topContainer.listenTo(_))
    radios.head.doClick()

  }

  private def onChurchSelectionChanged = {
    println("currently selected church: " + selectedChurch)
    tabbedPane.pages.clear()

    val tabPagesName = List("Detalii biserica", "Membri", "Proiecte", "Vizite")
    for (pageName <- tabPagesName) {
      tabbedPane.pages += new Page(pageName, new MembersTabPanel(selectedChurch))
    }

  }

  def top = new MainFrame {
    menuBar = new MenuBar {
      contents += new Menu("Fisier") {
        contents += new MenuItem(Action("Nou") {
          DataHolder.clearData
          onFileLoaded
        })

        contents += new MenuItem(Action("Deschide") {
          //            accelerator = new KeyStroke()
          val fc = new FileChooser {
            fileSelectionMode = SelectionMode.FilesOnly
            fileFilter = new FileNameExtensionFilter("XML Files", "xml")
          }
          fc.showOpenDialog(this)
          if (fc.selectedFile != null) {
            DataHolder.readFromFile(fc.selectedFile.toString)
            loadedFileName = fc.selectedFile.toString
            onFileLoaded
          }
        })

        contents += new MenuItem(Action("Salveaza") {
          if (loadedFileName != null) {
            DataHolder.saveToFile(loadedFileName)
            println("saved to file " + loadedFileName)
          } else {
            println("cannot save to file; filename is null")
          }
        })

        contents += new MenuItem(Action("Salveaza ca") {
          val fc = new FileChooser {
            fileSelectionMode = SelectionMode.FilesOnly
            fileFilter = new FileNameExtensionFilter("XML Files", "xml")
          }
          fc.showSaveDialog(this)
          if (fc.selectedFile != null) {
            var fileName = fc.selectedFile.toString
            if (!fileName.endsWith(".xml")) {
              fileName += ".xml"
            }
            DataHolder.saveToFile(fileName)
            loadedFileName = fileName
            println("saved to file " + loadedFileName)
          }
        })

        contents += new Separator
        contents += new MenuItem(Action("Iesire") {
          quit
        })
      }
      contents += new Menu("Optiuni") {

      }
    }

    topContainer = new FlowPanel() {
      peer.setBorder(new BevelBorder(BevelBorder.RAISED))
      pastorNameField = new Label("Bine ai venit!")
      churchesButtonGroup = new ButtonGroup()
      contents += pastorNameField

      reactions += {
        case ButtonClicked(button) => {
          selectedChurch = DataHolder.getChurchesInfo.find(church => church.name == button.text).orNull
          onChurchSelectionChanged
        }
        case SelectionChanged(buttonGroup) => {
          println("selection changed event received")
        }
      }
    }

    val textArea = new TextArea {
      reactions += { case ButtonClicked(_) => text = text.concat("Button Clicked!\n") }
    }

    tabbedPane = new TabbedPane {
      pages += new Page("Detalii biserica", new FlowPanel())
      pages += new Page("Membri", new MembersTabPanel(selectedChurch))

//      val tabPagesName = List("Detalii biserica", "Membri", "Proiecte", "Vizite")
//      for (pageName <- tabPagesName) {
//        pages += new Page(pageName, new MembersTabPanel(selectedChurch))
//      }
    }

    contents = new BorderPanel {
      layout(topContainer) = BorderPanel.Position.North
      layout(tabbedPane) = BorderPanel.Position.Center
    }

    title = "Program pt Cosmin"
    this.peer.setSize(600, 400)
    this.peer.setLocationRelativeTo(null)

  }

  override def startup(args: Array[String]): Unit = {
    DataHolder.readFromFile("david.xml")
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
    super.startup(args)
  }

}