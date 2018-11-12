namespace MVC.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Student")]
    public partial class Student
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Student()
        {
            Book_Out_on_loan = new HashSet<Book_Out_on_loan>();
        }

        [Key]
        public int stuId { get; set; }

        [StringLength(50)]
        [Required]
        public string username { get; set; }
        [Required]
        [StringLength(50)]
        public string password { get; set; }
        
        [StringLength(50)]
        [Required]
        public string stuName { get; set; }
        [Required]
        [StringLength(50)]
        public string stuPhone { get; set; }
        [Required]
        [StringLength(50)]
        public string stuEmail { get; set; }
        [Required]
        [StringLength(50)]
        public string stuAddress { get; set; }

        public int? majorId { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Book_Out_on_loan> Book_Out_on_loan { get; set; }

        public virtual Major Major { get; set; }
    }
}
