namespace MVC.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("vBookDetail")]
    public partial class vBookDetail
    {
        [Key]
        [Column(Order = 0)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int stuId { get; set; }

        [StringLength(50)]
        public string stuName { get; set; }

        [Key]
        [Column(Order = 1)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int ISBN { get; set; }

        [StringLength(50)]
        public string Title { get; set; }

        [StringLength(50)]
        public string date_issue { get; set; }

        [StringLength(50)]
        public string date_due_for_return { get; set; }

        [StringLength(50)]
        public string date_return { get; set; }

        [StringLength(50)]
        public string date_late { get; set; }

        [StringLength(50)]
        public string fine_paid { get; set; }
    }
}
