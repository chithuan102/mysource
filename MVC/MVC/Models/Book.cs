namespace MVC.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Book")]
    public partial class Book
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Book()
        {
            Book_Out_on_loan = new HashSet<Book_Out_on_loan>();
        }
        
        [Key]
        
        public int ISBN { get; set; }
      [Required]
        [StringLength(50)]
        public string Title { get; set; }
        
        [StringLength(50)]
        public string Author { get; set; }

        public string Image { get; set; }

        [StringLength(50)]
        public string Price { get; set; }

        public string Description { get; set; }

        public int? Category { get; set; }

        public virtual BookCategory BookCategory { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Book_Out_on_loan> Book_Out_on_loan { get; set; }
    }
}
