using MVC.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MVC.DAO
{

    public class BookDAO
    {
        MyDataEnities db = null;
        public BookDAO()
        {
            db = new MyDataEnities();
        }
        public List<string> ListName(string keyword)
        {
            return db.Books.Where(x => x.Title.Contains(keyword)).Select(x => x.Title).ToList();
        }
        public List<Book> Search(string keyword,ref int totalRecord, int pageIndex = 1,int pageSize = 2)
        {
            totalRecord = db.Books.Where(x => x.Title.Contains(keyword)).Count();
            var model = (from a in db.Books
                        join b in db.BookCategories
                        on a.Category equals b.catId
                        where a.Title.Contains(keyword)
                        select new 
                        {
                            Author = a.Author,
                            ISBN = a.ISBN,
                            Description = a.Description,
                            Image = a.Image,
                            Price = a.Price,
                            Title = a.Title,
                            Category = b.catId,
           
                            
                        }).AsEnumerable().Select(x=>new Book()
                        {
                            Author = x.Author,
                            ISBN = x.ISBN,
                            Description = x.Description,
                            Image = x.Image,
                            Price = x.Price,
                            Title = x.Title,
                            Category = x.Category,
                        });
            model.OrderByDescending(x => x.Price).Skip((pageIndex - 1) * pageSize).Take(pageSize);
            return model.ToList();
        }
    }
}