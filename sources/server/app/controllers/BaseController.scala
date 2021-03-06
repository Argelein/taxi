package controllers

import play.api.db.DB
import play.api.mvc.Controller
import play.api.Play.current
import scala.slick.driver.PostgresDriver.simple._

trait DbAccessor {
  /**
   * Выполнение функции в контексте сессии к БД
   * @param f функция, требующая сессию
   * @tparam T тип возвращаемых данных
   * @return результат работы функции
   */
  protected def withDb[T](f: Session => T) = {
    Database.forDataSource(DB.getDataSource()) withSession { session =>
      f(session)
    }
  }

  /**
   * Выполнение действия, непребуюущего результата, в контексте сессии к БД
   * @param f действие
   */
  protected def withDbAction(f: Session => Unit) = {
    Database.forDataSource(DB.getDataSource()) withSession { session =>
      f(session)
    }
  }
}

/**
 * Базовый класс всех контроллеров приложения
 */
class BaseController extends Controller with DbAccessor{

}
