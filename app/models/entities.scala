package models


import play.api.db.slick.Config.driver.simple._

//import scala.slick.profile.RelationalTableComponent.Table

case class Facilities(id: Option[Int],
                      image: String,
                      name: String)


class FacilityTable(tag: Tag) extends Table[Facilities](tag, "Facility") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def image = column[String]("IMAGE", O.Default(""))

  def name = column[String]("NAME", O.Default(""))


  def * = (id.?, image,name) <> (Facilities.tupled, Facilities.unapply _)

}
