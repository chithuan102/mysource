using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using MVC.Models;
using PagedList;

namespace MVC.Controllers
{
    public class HomeController : Controller
    {
        private MyDataEnities db = new MyDataEnities();
        // GET: Home
        public ActionResult Index(int? page)
        {
            int pageSize = 9;
            int pageNumber = (page ?? 1);
            return View(db.Books.ToList().OrderBy(n => n.ISBN).ToPagedList(pageNumber, pageSize));
        }
        public ViewResult NotFound()
        {
            Response.StatusCode = 404;
            return View();
        }
    }

    
}