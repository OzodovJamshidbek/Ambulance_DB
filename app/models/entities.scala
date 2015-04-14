package models


import play.api.db.slick.Config.driver.simple._

//import scala.slick.profile.RelationalTableComponent.Table

case class Facilities(id: Option[Int],
                      image: String,
                      name: String,companyid:Int)
case class Company(id: Option[Int],name:String)


class FacilityTable(tag: Tag) extends Table[Facilities](tag, "Facility") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def image = column[String]("IMAGE", O.Default(""))

  def name = column[String]("NAME", O.Default(""))

  def companyid=column[Int]("COMPANYID",O.NotNull)

  def * = (id.?, image,name,companyid) <> (Facilities.tupled, Facilities.unapply _)

}
class CompanyTable(tag: Tag) extends Table[Company](tag,"Company"){

  def id=column[Int]("ID",O.PrimaryKey,O.AutoInc)

  def name=column[String]("NAME",O.Default(""))

  def * = (id.?, name) <> (Company.tupled,Company.unapply _)

}
