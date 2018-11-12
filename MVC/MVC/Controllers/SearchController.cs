using MVC.Models;
using PagedList;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace MVC.Controllers
{
    public class SearchResultController : Controller
    {
        // GET: SearchResult
        private MyDataEnities db = new MyDataEnities();
        [HttpPost]
        public ActionResult SearchResult(FormCollection f, int? page)
        {
            string sTuKhoa = f["txtSearch"].ToString();
            ViewBag.TuKhoa = sTuKhoa;
            List<Book> lstKQTK = db.Books.Where(n => n.Title.Contains(sTuKhoa)).ToList();
            //Phân trang
            int pageNumber = (page ?? 1);
            int pageSize = 9;
            if (lstKQTK.Count == 0)
            {
                ViewBag.ThongBao = "No result is found";
                return View(db.Books.OrderBy(n => n.Title).ToPagedList(pageNumber, pageSize));
            }
            ViewBag.ThongBao = "Had found " + lstKQTK.Count + " result!";
            return View(lstKQTK.OrderBy(n => n.Title).ToPagedList(pageNumber, pageSize));
        }
        [HttpGet]
        public ActionResult SearchResult(int? page, string sTuKhoa)
        {
            ViewBag.TuKhoa = sTuKhoa;
            List<Book> lstKQTK = db.Books.Where(n => n.Title.Contains(sTuKhoa)).ToList();
            //Phân trang
            int pageNumber = (page ?? 1);
            int pageSize = 9;
            if (lstKQTK.Count == 0)
            {
                ViewBag.ThongBao = "No result is found";
                return View(db.Books.OrderBy(n => n.Title).ToPagedList(pageNumber, pageSize));
            }
            ViewBag.ThongBao = "Found " + lstKQTK.Count + " items!";
            return View(lstKQTK.OrderBy(n => n.Title).ToPagedList(pageNumber, pageSize));
        }
    }
}