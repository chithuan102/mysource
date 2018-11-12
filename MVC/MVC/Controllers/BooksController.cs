using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using MVC.Models;
using System.IO;
using MVC.DAO;

namespace MVC.Controllers
{
    public class BooksController : Controller
    {
       
        private MyDataEnities db = new MyDataEnities();

        // GET: Books
        public ActionResult Index()
        {
            if (Session["UserID"] != null)
            {
                var books = db.Books.Include(b => b.BookCategory);
            return View(books.ToList());
            }
            else
            {
                return RedirectToAction("Login","Admin");
            }
           
        }

        // GET: Books/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Book book = db.Books.Find(id);
            if (book == null)
            {
                return HttpNotFound();
            }
            return View(book);
        }

        // GET: Books/Create
        public ActionResult Create()
        {
            ViewBag.Category = new SelectList(db.BookCategories, "catId", "catName");
            return PartialView("Create");
        }

        // POST: Books/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "ISBN,Title,Author,Image,Price,Description,Category")] Book book, HttpPostedFileBase fileUpload)
        {
            if (fileUpload == null)
            {
                ViewBag.ThongBao = "Please choose image";
                return View();
            }
            if (ModelState.IsValid)
            {
                var fileName = Path.GetFileName(fileUpload.FileName);
                book.Image = "~/Images/" + fileName;
                db.Books.Add(book);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return PartialView("Create", book);
        }

        // GET: Books/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Book book = db.Books.Find(id);
            if (book == null)
            {
                return HttpNotFound();
            }
            ViewBag.Category = new SelectList(db.BookCategories, "catId", "catName", book.Category);
            return View(book);
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "ISBN,Title,Author,Image,Price,Description,Category")] Book book, HttpPostedFileBase fileUpload)
        {
            if (fileUpload == null)
            {
                ViewBag.ThongBao = "Please choose image";
                return View();
            }
            if (ModelState.IsValid)
            {
                var fileName = Path.GetFileName(fileUpload.FileName);
                string folder = Server.MapPath("~/Images/");
                book.Image = "~/Images/" + fileName;
                db.Entry(book).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.Category = new SelectList(db.BookCategories, "catId", "catName", book.Category);
            return View(book);
        }

        // GET: Books/Delete/5
        public ActionResult Delete(int id)
        {
            Book book = db.Books.Find(id);
            db.Books.Remove(book);
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
        //public ViewResult BookDetail()
        //{
        //    Book book= db.Books.SingleOrDefault(n => n.ISBN == bookId);
        //    if (book == null)
        //    {
        //        //Trả về trang báo lỗi 
        //        Response.StatusCode = 404;
        //        return null;
        //    }
        //    //ChuDe cd = db.ChuDes.Single(n => n.MaChuDe == sach.MaChuDe);
        //    //ViewBag.TenCD = cd.TenChuDe;
        //    ViewBag.Category = db.BookCategories.Single(n => n.catId == book.Category).catName;
        //    ViewData["BookDetails"] = book;
        //    return View(book);
        //}
        public ActionResult BookDetail(Book_Out_on_loan book_Out_on_loan,int ISBN = 0)
        {
            Book book = db.Books.SingleOrDefault(n => n.ISBN == ISBN);
            if (book == null)
            {
                //Trả về trang báo lỗi 
                Response.StatusCode = 404;
                return null;
            }
            //ChuDe cd = db.ChuDes.Single(n => n.MaChuDe == sach.MaChuDe);
            //ViewBag.TenCD = cd.TenChuDe;
            ViewBag.Category = db.BookCategories.Single(n => n.catId == book.Category).catName;
            ViewBag.ISBN = new SelectList(db.Books, "ISBN", "Title", book_Out_on_loan.ISBN);
            ViewData["BookDetails"] = book;
  
            return View();
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult BookDetail(Book book, Book_Out_on_loan stBook)
        {
           
            if (Session["StudentName"] == null)
            {
                return RedirectToAction("StudentLogin", "Students");
            }
            stBook.ISBN = book.ISBN;
            stBook.status = "Working";
            stBook.stuId = Convert.ToInt32(Session["StudentId"]);
            if (ModelState.IsValid)
            {
                db.Book_Out_on_loan.Add(stBook);
                db.SaveChanges();
                return RedirectToAction("Index","Home");
            }
            
            ViewData["BookDetails"] = book;
            return View(stBook);
        }
        public JsonResult ListName(string q)
        {
            var data = new BookDAO().ListName(q);
            return Json(new {
                data = data,
                 status = true
            },JsonRequestBehavior.AllowGet);
           
        }
        public ActionResult Search(string keyword, int page =1,int pageSize = 1)
        {
            int totalRecord = 0;
            var model = new BookDAO().Search(keyword, ref totalRecord, page, pageSize);
            ViewBag.Total = totalRecord;
            ViewBag.Page = page;
            ViewBag.Keyword = keyword;
            int maxPage = 5;
            int totalPage = 0;
            totalPage = (int)Math.Ceiling((double)(totalRecord / pageSize));
            ViewBag.TotalPage = totalPage;
            ViewBag.MaxPage = maxPage;
            ViewBag.First = 1;
            ViewBag.Last = totalPage;
            ViewBag.Next = page + 1;
            ViewBag.Prev = page - 1;
            return View(model);
        }
    }
}
