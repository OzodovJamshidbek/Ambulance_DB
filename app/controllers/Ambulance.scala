package controllers



import play.api.mvc._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import models._
import play.api.Logger

import scala.slick.lifted.TableQuery

//case class facility(id:Int,image:String,name:String)
case class rug(id:Int,image:String,name:String,res:Boolean,infor:String,pro:String)

case class FacilityForDisplay(facility:Facilities,companyname:String)

object Ambulance extends Controller{

  val facilities = TableQuery[FacilityTable]

  val company = TableQuery[CompanyTable]

  var rugs=List(
    rug(1,"13.jpg","Taylol hot",true,"1 stakan qaynatilgan suvga solinadi va issiq holda ichiladi......","shamollash"),
    rug(2,"15.jpg","Coldrex",false,"....","shamollash"),
    rug(3,"17.jpg","Citramon",false,"...","Bosh og'riqi")
  )


  def show = DBAction { implicit rs =>
    val facResult = (for {
      (facility, company) <- facilities leftJoin company on (_.companyid === _.id)
    } yield (facility, company.name)).list
      .map { case (facility, companyName) => FacilityForDisplay(facility, companyName)}
    //Logger.info(s"SHOW_ALL = ${facilities.list}")
    Ok(views.html.facilities(facResult))
  }


  def listcompany=DBAction { implicit rs =>
    Ok(views.html.listcompany(company.list))

  }

  def update(id: Int) = DBAction { implicit rs =>
    val fs = rs.body.asFormUrlEncoded
    val image = fs.get("image")(0)
    val name = fs.get("name")(0)
    val companyid = fs.get("companyid")(0).toInt

    val facility=Facilities(Some(id),image,name,companyid)
    facilities.filter(_.id===id).update(facility)
    Redirect(routes.Ambulance.show())
  }

  def updatecom(id: Int) = DBAction { implicit rs =>
    val fs = rs.body.asFormUrlEncoded
    val name = fs.get("name")(0)

    val comp=Company(Some(id),name)
    company.filter(_.id===id).update(comp)
    Redirect(routes.Ambulance.listcompany())
  }

  def showeditform(id:Int)=DBAction{ implicit rs =>
    val byId = facilities.findBy(_.id)
    val facility = byId(id).list.head

    Ok(views.html.edit(facility,company.list))

  }
  def showeditformcompany(id:Int)=DBAction{ implicit rs =>
    val byId = company.findBy(_.id)
    val compan = byId(id).list.head

    Ok(views.html.editcompany(compan))
  }

  def showAddForm = DBAction {implicit rs =>
    Ok(views.html.add(company.list))
  }
  def showaddformcompany=DBAction {implicit rs =>
    Ok(views.html.addcompany())

  }

  def showrug=Action{
    Ok(views.html.rugs(rugs.toList.sortBy(_.id)))
  }


  def add = DBAction { implicit rs =>
    val fs = rs.body.asFormUrlEncoded
    val image = fs.get("image")(0)
    val name = fs.get("name")(0)
    val companyid = fs.get("companyid")(0).toInt

    val facilityId = (facilities returning facilities.map(_.id)) += Facilities(None, image, name, companyid)
    //Logger.info(s"LastId = $facilityId")
    Redirect(routes.Ambulance.show())
  }
  def addcompany = DBAction { implicit rs =>
    val fs = rs.body.asFormUrlEncoded
    val name = fs.get("name")(0)
    val facilityId = (company returning company.map(_.id)) += Company(None, name)
    //Logger.info(s"LastId = $facilityId")
    Redirect(routes.Ambulance.listcompany())
  }


  def remove(id: Int) = DBAction { implicit request =>
    facilities.filter(_.id === id).delete;
    Redirect(routes.Ambulance.show())
  }
  def removecom(id: Int) = DBAction { implicit request =>
    company.filter(_.id === id).delete;
    Redirect(routes.Ambulance.listcompany())
  }

}
