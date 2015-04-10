package controllers



import play.api.mvc._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import models._
import play.api.Logger

import scala.slick.lifted.TableQuery

//case class facility(id:Int,image:String,name:String)
case class rug(id:Int,image:String,name:String,res:Boolean,infor:String,pro:String)
object Ambulance extends Controller{

  val facilities = TableQuery[FacilityTable]
  var rugs=List(
    rug(1,"13.jpg","Taylol hot",true,"1 stakan qaynatilgan suvga solinadi va issiq holda ichiladi......","shamollash"),
    rug(2,"15.jpg","Coldrex",false,"....","shamollash"),
    rug(3,"17.jpg","Citramon",false,"...","Bosh og'riqi")
  )
  def show = DBAction { implicit rs =>
    Logger.info(s"SHOW_ALL = ${facilities.list}")
    Ok(views.html.facilities(facilities.list))
  }

  def showAddForm = Action {
    Ok(views.html.add())
  }

  def showrug=Action{
    Ok(views.html.rugs(rugs.toList.sortBy(_.id)))
  }
  def add = DBAction { implicit request =>
    val formParams = request.body.asFormUrlEncoded
    val image = formParams.get("image")(0)
    val name = formParams.get("name")(0)

    val facilityId = (facilities returning facilities.map(_.id)) += Facilities(None, image, name)
    Logger.info(s"LastId = $facilityId")
    Redirect(routes.Ambulance.show())
  }


  def remove(id: Int) = DBAction { implicit request =>
    facilities.filter(_.id === id).delete;
    Redirect(routes.Ambulance.show())
  }

}
