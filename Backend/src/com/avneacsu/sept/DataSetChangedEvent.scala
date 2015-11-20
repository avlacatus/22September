package com.avneacsu.sept

import scala.swing.event.Event

/**
 * Created by Alexandra Neacsu on 11/18/2015.
 */
class DataSetChangedEvent extends Event {

}

object DataSetChangedEvent {
  def unapply(event: Event): Option[DataSetChangedEvent] = Some(new DataSetChangedEvent)
}
