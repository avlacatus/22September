package com.avneacsu.sept.parser

import com.avneacsu.sept.model.XMLResource

import scala.xml._

trait ResourceFormatter[T <: XMLResource] {

  def read(node: Node): T

  def write(resource: T): Node = {
    var output = Elem(null, resource.getRootElementName, scala.xml.Null, scala.xml.TopScope, true)
    for (attributeName <- resource.getAttributeList)
      output = output % Attribute(attributeName, Text(resource.getValueForAttribute(attributeName)), Null)


    output
  }

}
