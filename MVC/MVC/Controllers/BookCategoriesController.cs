using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using MVC.Models;

namespace MVC.Controllers
{
    public class BookCategoriesController : Controller
    {
        private MyDataEnities db = new MyDataEnities();

        // GET: BookCategories
        public ActionResult Index()
        {
            if (Session["UserID"] != null)
            {
                return View(db.BookCategories.ToList());
            }
            else
            {
                return RedirectToAction("Login","Admin");
            }
            
        }
        public ActionResult CategoryPartial()
        {

            return PartialView(db.BookCategories.ToList());
        }
        public ActionResult Category(int catId=0)
        {
            BookCategory bc = db.BookCategories.SingleOrDefault(n => n.catId == catId);
            if (bc == null)
            {
                Response.StatusCode = 404;
                return null;
            }
            List<Book> lstBook = db.Books.Where(n => n.Category == catId).OrderBy(n => n.Price).ToList();
            if (lstBook.Count == 0)
            {
                ViewBag.Sach = "No book belong to this category";
            }
            ViewBag.lstCategory = db.BookCategories.ToList();
            return View(lstBook);

        }


        // GET: BookCategories/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            BookCategory bookCategory = db.BookCategories.Find(id);
            if (bookCategory == null)
            {
                return HttpNotFound();
            }
            return View(bookCategory);
        }

        // GET: BookCategories/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: BookCategories/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "catId,catName")] BookCategory bookCategory)
        {
            if (ModelState.IsValid)
            {
                db.BookCategories.Add(bookCategory);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(bookCategory);
        }

        // GET: BookCategories/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            BookCategory bookCategory = db.BookCategories.Find(id);
            if (bookCategory == null)
            {
                return HttpNotFound();
            }
            return View(bookCategory);
        }

        // POST: BookCategories/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "catId,catName")] BookCategory bookCategory)
        {
            if (ModelState.IsValid)
            {
                db.Entry(bookCategory).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(bookCategory);
        }

        // GET: BookCategories/Delete/5
        //public ActionResult Delete(int? id)
        //{
        //    if (id == null)
        //    {
        //        return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
        //    }
        //    BookCategory bookCategory = db.BookCategories.Find(id);
        //    if (bookCategory == null)
        //    {
        //        return HttpNotFound();
        //    }
        //    return View(bookCategory);
        //}

        //// POST: BookCategories/Delete/5
        //[HttpPost, ActionName("Delete")]
        //[ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            BookCategory bookCategory = db.BookCategories.Find(id);
            db.BookCategories.Remove(bookCategory);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
