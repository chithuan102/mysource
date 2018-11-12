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
    public class vBookDetailsController : Controller
    {
        private MyDataEnities db = new MyDataEnities();

        // GET: vBookDetails
        public ActionResult Index()
        {
            //int id = Convert.ToInt32(Session["StudentId"]);
            //var model = db.vBookDetails.Include(s=>s.book).Include(s=>s.student);
            //model = model.Where(b => b.stuId == id);
            //return View(model.ToList());
            return View();
        }

        // GET: vBookDetails/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            vBookDetail vBookDetail = db.vBookDetails.Find(id);
            if (vBookDetail == null)
            {
                return HttpNotFound();
            }
            return View(vBookDetail);
        }

        // GET: vBookDetails/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: vBookDetails/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "stuId,ISBN,stuName,Title,date_issue,date_due_for_return,date_return,date_late,fine_paid")] vBookDetail vBookDetail)
        {
            if (ModelState.IsValid)
            {
                db.vBookDetails.Add(vBookDetail);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(vBookDetail);
        }

        // GET: vBookDetails/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            vBookDetail vBookDetail = db.vBookDetails.Find(id);
            if (vBookDetail == null)
            {
                return HttpNotFound();
            }
            return View(vBookDetail);
        }

        // POST: vBookDetails/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "stuId,ISBN,stuName,Title,date_issue,date_due_for_return,date_return,date_late,fine_paid")] vBookDetail vBookDetail)
        {
            if (ModelState.IsValid)
            {
                db.Entry(vBookDetail).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(vBookDetail);
        }

        // GET: vBookDetails/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            vBookDetail vBookDetail = db.vBookDetails.Find(id);
            if (vBookDetail == null)
            {
                return HttpNotFound();
            }
            return View(vBookDetail);
        }

        // POST: vBookDetails/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            vBookDetail vBookDetail = db.vBookDetails.Find(id);
            db.vBookDetails.Remove(vBookDetail);
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
