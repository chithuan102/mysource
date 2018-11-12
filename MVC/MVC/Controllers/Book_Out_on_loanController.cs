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
    public class Book_Out_on_loanController : Controller
    {
        private MyDataEnities db = new MyDataEnities();

        // GET: Book_Out_on_loan
        public ActionResult History()
        {
            int id = Convert.ToInt32(Session["StudentId"]);
            var model = db.Book_Out_on_loan.Include(s => s.Book).Include(s => s.Student);
            model = model.Where(b => b.stuId == id);
            return View(model.ToList());
        }
        public ActionResult Index()
        {
            return View(db.Book_Out_on_loan.ToList());
        }

        // GET: Book_Out_on_loan/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Book_Out_on_loan book_Out_on_loan = db.Book_Out_on_loan.Find(id);
            if (book_Out_on_loan == null)
            {
                return HttpNotFound();
            }
            return View(book_Out_on_loan);
        }

        // GET: Book_Out_on_loan/Create
        public ActionResult Create()
        {
            ViewBag.ISBN = new SelectList(db.Books, "ISBN", "Title");
            ViewBag.stuId = new SelectList(db.Students, "stuId", "username");
            return View();
        }

        // POST: Book_Out_on_loan/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "book_borrowing_id,ISBN,stuId,date_issue,date_due_for_return,date_return,status")] Book_Out_on_loan book_Out_on_loan)
        {
            if (ModelState.IsValid)
            {
                db.Book_Out_on_loan.Add(book_Out_on_loan);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            ViewBag.ISBN = new SelectList(db.Books, "ISBN", "Title", book_Out_on_loan.ISBN);
            ViewBag.stuId = new SelectList(db.Students, "stuId", "username", book_Out_on_loan.stuId);
            return View(book_Out_on_loan);
        }

        // GET: Book_Out_on_loan/Edit/5
        public ActionResult Edit(int? xamlon, int? ISBN, int? stuId)
        {
            if (xamlon == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Book_Out_on_loan book_Out_on_loan = db.Book_Out_on_loan.Find(xamlon, ISBN, stuId);
            if (book_Out_on_loan == null)
            {
                return HttpNotFound();
            }
            ViewBag.ISBN = new SelectList(db.Books, "ISBN", "Title", book_Out_on_loan.ISBN);
            ViewBag.stuId = new SelectList(db.Students, "stuId", "username", book_Out_on_loan.stuId);
            return View(book_Out_on_loan);
        }

        // POST: Book_Out_on_loan/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "book_borrowing_id,ISBN,stuId,date_issue,date_due_for_return,date_return,status")] Book_Out_on_loan book_Out_on_loan)
        {
            if (ModelState.IsValid)
            {
                db.Entry(book_Out_on_loan).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.ISBN = new SelectList(db.Books, "ISBN", "Title", book_Out_on_loan.ISBN);
            ViewBag.stuId = new SelectList(db.Students, "stuId", "username", book_Out_on_loan.stuId);
            return View(book_Out_on_loan);
        }

        //// GET: Book_Out_on_loan/Delete/5
        //public ActionResult Delete(int? id)
        //{
        //    if (id == null)
        //    {
        //        return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
        //    }
        //    Book_Out_on_loan book_Out_on_loan = db.Book_Out_on_loan.Find(id);
        //    if (book_Out_on_loan == null)
        //    {
        //        return HttpNotFound();
        //    }
        //    return View(book_Out_on_loan);
        //}

        //// POST: Book_Out_on_loan/Delete/5
        //[HttpPost, ActionName("Delete")]
        //[ValidateAntiForgeryToken]
        public ActionResult Delete(int xamlon, int ISBN, int stuId)
        {
            Book_Out_on_loan book_Out_on_loan = db.Book_Out_on_loan.Find(xamlon, ISBN, stuId);
            db.Book_Out_on_loan.Remove(book_Out_on_loan);
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
