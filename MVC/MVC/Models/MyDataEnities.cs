namespace MVC.Models
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class MyDataEnities : DbContext
    {
        public MyDataEnities()
            : base("name=MyDataEnities5")
        {
        }

        public virtual DbSet<Admin> Admins { get; set; }
        public virtual DbSet<Book> Books { get; set; }
        public virtual DbSet<Book_Out_on_loan> Book_Out_on_loan { get; set; }
        public virtual DbSet<BookCategory> BookCategories { get; set; }
        public virtual DbSet<Major> Majors { get; set; }
        public virtual DbSet<Student> Students { get; set; }
        public virtual DbSet<vBookDetail> vBookDetails { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Admin>()
                .Property(e => e.adminId)
                .IsUnicode(false);

            modelBuilder.Entity<Admin>()
                .Property(e => e.password)
                .IsUnicode(false);

            modelBuilder.Entity<Book>()
                .Property(e => e.Title)
                .IsUnicode(false);

            modelBuilder.Entity<Book>()
                .Property(e => e.Author)
                .IsUnicode(false);

            modelBuilder.Entity<Book>()
                .Property(e => e.Image)
                .IsUnicode(false);

            modelBuilder.Entity<Book>()
                .Property(e => e.Price)
                .IsUnicode(false);

            modelBuilder.Entity<Book>()
                .Property(e => e.Description)
                .IsUnicode(false);

            modelBuilder.Entity<Book>()
                .HasMany(e => e.Book_Out_on_loan)
                .WithRequired(e => e.Book)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Book_Out_on_loan>()
                .Property(e => e.status)
                .IsUnicode(false);

            modelBuilder.Entity<BookCategory>()
                .Property(e => e.catName)
                .IsUnicode(false);

            modelBuilder.Entity<BookCategory>()
                .HasMany(e => e.Books)
                .WithOptional(e => e.BookCategory)
                .HasForeignKey(e => e.Category)
                .WillCascadeOnDelete();

            modelBuilder.Entity<Major>()
                .Property(e => e.majorName)
                .IsUnicode(false);

            modelBuilder.Entity<Student>()
                .Property(e => e.username)
                .IsUnicode(false);

            modelBuilder.Entity<Student>()
                .Property(e => e.password)
                .IsUnicode(false);

            modelBuilder.Entity<Student>()
                .Property(e => e.stuName)
                .IsUnicode(false);

            modelBuilder.Entity<Student>()
                .Property(e => e.stuPhone)
                .IsUnicode(false);

            modelBuilder.Entity<Student>()
                .Property(e => e.stuEmail)
                .IsUnicode(false);

            modelBuilder.Entity<Student>()
                .Property(e => e.stuAddress)
                .IsUnicode(false);

            modelBuilder.Entity<Student>()
                .HasMany(e => e.Book_Out_on_loan)
                .WithRequired(e => e.Student)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<vBookDetail>()
                .Property(e => e.stuName)
                .IsUnicode(false);

            modelBuilder.Entity<vBookDetail>()
                .Property(e => e.Title)
                .IsUnicode(false);

            modelBuilder.Entity<vBookDetail>()
                .Property(e => e.date_issue)
                .IsUnicode(false);

            modelBuilder.Entity<vBookDetail>()
                .Property(e => e.date_due_for_return)
                .IsUnicode(false);

            modelBuilder.Entity<vBookDetail>()
                .Property(e => e.date_return)
                .IsUnicode(false);

            modelBuilder.Entity<vBookDetail>()
                .Property(e => e.date_late)
                .IsUnicode(false);

            modelBuilder.Entity<vBookDetail>()
                .Property(e => e.fine_paid)
                .IsUnicode(false);
        }
    }
}
