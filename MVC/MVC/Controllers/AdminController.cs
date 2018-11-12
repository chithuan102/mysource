using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using MVC.Models;
namespace MVC.Controllers
{
    public class AdminController : Controller
    {
        MyDataEnities db = new MyDataEnities();
        // GET: Admin
        public ActionResult Index()
        {
            if (Session["UserID"] != null)
            {
                return View();
            }
            else
            {
                return RedirectToAction("Login");
            }
        }
        public ActionResult Login()
        {
            Session["UserID"] = null;
            return View();
        }
        [HttpPost]
        public ActionResult Login(Admin admin)
        {
            var user = db.Admins.Where(u => u.adminId == admin.adminId && u.password == admin.password).FirstOrDefault();
            if (user != null)
            {
                Session["UserID"] = user.adminId.ToString();
                return RedirectToAction("Index");
            }
            else
            {
                ModelState.AddModelError("", "User name or Passwork incorrect.");
            }
            return View();
        }
    }
}